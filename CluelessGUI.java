import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import javafoundations.CircularArrayQueue;

public class CluelessGUI extends JFrame {
private Closet closet;
private Carousel topCarousel;
private Carousel bottomCarousel;
private OutfitHistory outfitHistory;
private JLabel topImageLabel;
private JLabel bottomImageLabel;
private JLabel topInfoLabel;
private JLabel bottomInfoLabel;
private JLabel outfitLabel = new JLabel("Select a top and bottom to create an outfit");
private JTextArea historyDisplay;

public CluelessGUI() {
    super("Cher's Clueless Closet");
    closet = new Closet(8);
    outfitHistory = new OutfitHistory();
    seedCloset();
    loadQueues();

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(900, 700);
    setLayout(new BorderLayout(8,8));
    getContentPane().setBackground(new Color(255, 240, 245));

    JPanel mainPanel = new JPanel(new BorderLayout(10,10));
    mainPanel.setBorder(new EmptyBorder(15,15,15,15));
    mainPanel.setBackground(new Color(255, 240, 245));
    
    mainPanel.add(makeCarouselPanel(), BorderLayout.CENTER);
    mainPanel.add(makeControlPanel(), BorderLayout.SOUTH);
    mainPanel.add(makeHistoryPanel(), BorderLayout.EAST);

    add(mainPanel, BorderLayout.CENTER);
    
    updateCarousels();
}

private void loadQueues() {
    // Create carousels from closet rows
    topCarousel = new Carousel(closet, 0);  // row 0 = tops
    bottomCarousel = new Carousel(closet, 1);  // row 1 = bottoms
}



private JPanel makeCarouselPanel() {
    JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
    panel.setBackground(new Color(255, 240, 245));
    
    // Top carousel
    JPanel topCarousel = new JPanel(new BorderLayout(5,5));
    topCarousel.setBackground(Color.WHITE);
    topCarousel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(200, 150, 200), 2),
        new EmptyBorder(10,10,10,10)));
    
    JLabel topTitle = new JLabel("TOPS", SwingConstants.CENTER);
    topTitle.setFont(new Font("Arial", Font.BOLD, 18));
    topTitle.setForeground(new Color(150, 50, 150));
    topCarousel.add(topTitle, BorderLayout.NORTH);
    
    topImageLabel = new JLabel("", SwingConstants.CENTER);
    topImageLabel.setPreferredSize(new Dimension(300, 250));
    topImageLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
    topCarousel.add(topImageLabel, BorderLayout.CENTER);
    
    JPanel topNav = new JPanel(new FlowLayout());
    topNav.setBackground(Color.WHITE);
    JButton topPrev = new JButton("◀ Previous");
    JButton topNext = new JButton("Next ▶");
    topPrev.addActionListener(e -> navigateTop());
    topNext.addActionListener(e -> navigateTop());
    topInfoLabel = new JLabel("Item info");
    topInfoLabel.setFont(new Font("Arial", Font.PLAIN, 12));
    topNav.add(topPrev);
    topNav.add(topInfoLabel);
    topNav.add(topNext);
    topCarousel.add(topNav, BorderLayout.SOUTH);
    
    // Bottom carousel
    JPanel bottomCarousel = new JPanel(new BorderLayout(5,5));
    bottomCarousel.setBackground(Color.WHITE);
    bottomCarousel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(150, 150, 250), 2),
        new EmptyBorder(10,10,10,10)));
    
    JLabel bottomTitle = new JLabel("BOTTOMS", SwingConstants.CENTER);
    bottomTitle.setFont(new Font("Arial", Font.BOLD, 18));
    bottomTitle.setForeground(new Color(50, 50, 200));
    bottomCarousel.add(bottomTitle, BorderLayout.NORTH);
    
    bottomImageLabel = new JLabel("", SwingConstants.CENTER);
    bottomImageLabel.setPreferredSize(new Dimension(300, 250));
    bottomImageLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
    bottomCarousel.add(bottomImageLabel, BorderLayout.CENTER);
    
    JPanel bottomNav = new JPanel(new FlowLayout());
    bottomNav.setBackground(Color.WHITE);
    JButton bottomPrev = new JButton("◀ Previous");
    JButton bottomNext = new JButton("Next ▶");
    bottomPrev.addActionListener(e -> navigateBottom());
    bottomNext.addActionListener(e -> navigateBottom());
    bottomInfoLabel = new JLabel("Item info");
    bottomInfoLabel.setFont(new Font("Arial", Font.PLAIN, 12));
    bottomNav.add(bottomPrev);
    bottomNav.add(bottomInfoLabel);
    bottomNav.add(bottomNext);
    bottomCarousel.add(bottomNav, BorderLayout.SOUTH);
    
    panel.add(topCarousel);
    panel.add(bottomCarousel);
    return panel;
}

private void navigateTop() {
    if (topCarousel.isEmpty()) return;
    topCarousel.next();
    updateCarousels();
}

private void navigateBottom() {
    if (bottomCarousel.isEmpty()) return;
    bottomCarousel.next();
    updateCarousels();
}

private void updateCarousels() {
    // Update top
    if (!topCarousel.isEmpty()) {
        Top top = (Top) topCarousel.current();
        if (top.getImagePath() != null && !top.getImagePath().isEmpty()) {
            ImageIcon icon = loadScaledImage(top.getImagePath(), 280, 230);
            topImageLabel.setIcon(icon);
            topImageLabel.setText("");
        } else {
            topImageLabel.setIcon(null);
            topImageLabel.setText("<html><center>No image<br/>" + top.getName() + "</center></html>");
        }
        topInfoLabel.setText(top.getName() + " - " + top.getColor());
    } else {
        topImageLabel.setIcon(null);
        topImageLabel.setText("No tops");
        topInfoLabel.setText("");
    }
    
    // Update bottom
    if (!bottomCarousel.isEmpty()) {
        Bottom bottom = (Bottom) bottomCarousel.current();
        if (bottom.getImagePath() != null && !bottom.getImagePath().isEmpty()) {
            ImageIcon icon = loadScaledImage(bottom.getImagePath(), 280, 230);
            bottomImageLabel.setIcon(icon);
            bottomImageLabel.setText("");
        } else {
            bottomImageLabel.setIcon(null);
            bottomImageLabel.setText("<html><center>No image<br/>" + bottom.getName() + "</center></html>");
        }
        bottomInfoLabel.setText(bottom.getName() + " - " + bottom.getColor());
    } else {
        bottomImageLabel.setIcon(null);
        bottomImageLabel.setText("No bottoms");
        bottomInfoLabel.setText("");
    }
    
    // Update outfit display
    if (!topCarousel.isEmpty() && !bottomCarousel.isEmpty()) {
        Outfit outfit = new Outfit((Top)topCarousel.current(), (Bottom) bottomCarousel.current());
        outfitLabel.setText("Current outfit: " + outfit.toString());
    }
}

private ImageIcon loadScaledImage(String path, int width, int height) {
    try {
        ImageIcon original = new ImageIcon(path);
        Image scaled = original.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    } catch (Exception e) {
        return null;
    }
}

private JPanel makeControlPanel() {
    JPanel p = new JPanel(new GridLayout(3,1,4,4));
    p.setBackground(new Color(255, 240, 245));
    
    JButton saveOutfitBtn = new JButton("💾 Save Current Outfit");
    saveOutfitBtn.setFont(new Font("Arial", Font.BOLD, 14));
    saveOutfitBtn.addActionListener(e -> saveCurrentOutfit());
    
    p.add(saveOutfitBtn);
    p.add(outfitLabel);
    return p;
}

private JPanel makeHistoryPanel() {
    JPanel panel = new JPanel(new BorderLayout(5,5));
    panel.setBackground(Color.WHITE);
    panel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(200, 100, 200), 2),
        new EmptyBorder(10,10,10,10)));
    panel.setPreferredSize(new Dimension(250, 0));
    
    JLabel title = new JLabel("Outfit History (Last 7)", SwingConstants.CENTER);
    title.setFont(new Font("Arial", Font.BOLD, 14));
    title.setForeground(new Color(150, 50, 150));
    
    historyDisplay = new JTextArea();
    historyDisplay.setEditable(false);
    historyDisplay.setFont(new Font("Arial", Font.PLAIN, 11));
    historyDisplay.setText("No outfits saved yet!");
    historyDisplay.setLineWrap(true);
    historyDisplay.setWrapStyleWord(true);
    
    JScrollPane scroll = new JScrollPane(historyDisplay);
    
    panel.add(title, BorderLayout.NORTH);
    panel.add(scroll, BorderLayout.CENTER);
    
    return panel;
}

private void saveCurrentOutfit() {
    if (topCarousel.isEmpty() || bottomCarousel.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please select both a top and bottom first!");
        return;
    }
    
    Outfit newOutfit = new Outfit((Top)topCarousel.current(), (Bottom)bottomCarousel.current());
    outfitHistory.addOutfit(newOutfit);
    updateHistoryDisplay();
    JOptionPane.showMessageDialog(this, "Outfit saved! ✨");
}

private void updateHistoryDisplay() {
    historyDisplay.setText(outfitHistory.getHistoryDisplay());
}

private void seedCloset() {
    // Add tops
    closet.addTop(new Top("Black Floral Tank", "black", "floral", "warm", 
        "images/black_floral_tank.jpeg", "sleeveless"));
    closet.addTop(new Top("Black Gingham Tank", "black", "gingham", "warm", 
        "images/black_gingham_tank.jpeg", "sleeveless"));
    closet.addTop(new Top("Black Graphic Tee", "black", "graphic", "warm", 
        "images/black_graphic.jpeg", "short"));
    closet.addTop(new Top("Black Mesh Top", "black", "solid", "warm", 
        "images/black_mesh_top.jpeg", "short"));
    closet.addTop(new Top("Blue Linen Tank", "blue", "solid", "warm", 
        "images/blue_linen_tank.jpeg", "sleeveless"));
    closet.addTop(new Top("Blue Sweats Top", "blue", "solid", "cold", 
        "images/blue_sweats.jpeg", "long"));
    closet.addTop(new Top("Brown Graphic Tank", "brown", "graphic", "warm", 
        "images/brown_graphic_tank.jpeg", "sleeveless"));
    closet.addTop(new Top("Brown Henley", "brown", "solid", "cold", 
        "images/brown_henley.jpeg", "long"));
    closet.addTop(new Top("Brown Lace Tank", "brown", "lace", "warm", 
        "images/brown_lace_tank.jpeg", "sleeveless"));
    closet.addTop(new Top("Brown Longsleeve", "brown", "solid", "cold", 
        "images/brown_longsleeve.jpeg", "long"));
    closet.addTop(new Top("Brown Stripe Longsleeve", "brown", "stripe", "cold", 
        "images/brown_stripe_longsleeve.jpeg", "long"));
    closet.addTop(new Top("Cable Knit Zipup", "cream", "cable", "cold", 
        "images/cable_knit_zipup.jpeg", "long"));
    closet.addTop(new Top("Car Graphic Tee", "white", "graphic", "warm", 
        "images/car_graphic_tee.jpeg", "short"));
    closet.addTop(new Top("Chicago Tee", "grey", "graphic", "warm", 
        "images/chicago_tee.jpeg", "short"));
    closet.addTop(new Top("Cream Lace Tank", "cream", "lace", "warm", 
        "images/cream_lace_tank.jpeg", "sleeveless"));
    closet.addTop(new Top("Dark Grey Longsleeve", "darkgrey", "solid", "cold", 
        "images/darkgrey_longsleeve.jpeg", "long"));
    closet.addTop(new Top("Floral Tube Top", "multicolor", "floral", "warm", 
        "images/floral_tube.jpeg", "sleeveless"));
    closet.addTop(new Top("Flowy Tube Top", "white", "solid", "warm", 
        "images/flowy_tube.jpeg", "sleeveless"));
    closet.addTop(new Top("Gingham Tube Top", "blue", "gingham", "warm", 
        "images/gingham_tube.jpeg", "sleeveless"));
    closet.addTop(new Top("Green Babydoll Top", "green", "solid", "warm", 
        "images/green_babydoll.jpeg", "short"));
    closet.addTop(new Top("Green Tee", "green", "solid", "warm", 
        "images/green_tee.jpeg", "short"));
    closet.addTop(new Top("Grey Babydoll Longsleeve", "grey", "solid", "cold", 
        "images/grey_babydoll_longsleeve.jpeg", "long"));
    
    // Add bottoms
    closet.addBottom(new Bottom("Blue Jeans", "blue", "denim", "warm", 
        "images/blue_jeans.jpeg", "full"));
    closet.addBottom(new Bottom("Dark Jeans", "dark", "denim", "warm", 
        "images/dark_jeans.jpeg", "full"));
    closet.addBottom(new Bottom("Denim Shorts", "blue", "denim", "warm", 
        "images/denim_short.jpeg", "short"));
    closet.addBottom(new Bottom("Green Cargos", "green", "solid", "warm", 
        "images/green_cargos.jpeg", "full"));
    closet.addBottom(new Bottom("Green Denim Skirt", "green", "denim", "warm", 
        "images/green_denim_skirt.jpeg", "short"));
}

public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
        CluelessGUI gui = new CluelessGUI();
        gui.setVisible(true);
    });
}
}