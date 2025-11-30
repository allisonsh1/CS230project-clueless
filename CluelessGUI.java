import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import javafoundations.CircularArrayQueue;

public class CluelessGUI extends JFrame {
    private Closet closet;
    private CircularArrayQueue<Top> topQueue;
    private CircularArrayQueue<Bottom> bottomQueue;
    private Top currentTop;
    private Bottom currentBottom;
    private JLabel topImageLabel;
    private JLabel bottomImageLabel;
    private JLabel topInfoLabel;
    private JLabel bottomInfoLabel;
    private JLabel outfitLabel = new JLabel("Select a top and bottom to create an outfit");

    public CluelessGUI() {
        super("Cher's Clueless Closet");
        closet = new Closet(8);
        topQueue = new CircularArrayQueue<>();
        bottomQueue = new CircularArrayQueue<>();
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

        add(mainPanel, BorderLayout.CENTER);
        
        // Set initial display
        if (!topQueue.isEmpty()) currentTop = topQueue.first();
        if (!bottomQueue.isEmpty()) currentBottom = bottomQueue.first();
        updateCarousels();
    }

    private void loadQueues() {
        // Load all tops from closet into queue
        for (int c = 0; c < closet.getArray()[0].length; c++) {
            if (closet.getArray()[0][c] instanceof Top) {
                topQueue.enqueue((Top) closet.getArray()[0][c]);
            }
        }
        
        // Load all bottoms from closet into queue
        for (int c = 0; c < closet.getArray()[1].length; c++) {
            if (closet.getArray()[1][c] instanceof Bottom) {
                bottomQueue.enqueue((Bottom) closet.getArray()[1][c]);
            }
        }
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
        if (topQueue.isEmpty()) return;
        // Dequeue current, enqueue it at back to rotate
        Top item = topQueue.dequeue();
        topQueue.enqueue(item);
        currentTop = topQueue.first();
        updateCarousels();
    }

    private void navigateBottom() {
        if (bottomQueue.isEmpty()) return;
        // Dequeue current, enqueue it at back to rotate
        Bottom item = bottomQueue.dequeue();
        bottomQueue.enqueue(item);
        currentBottom = bottomQueue.first();
        updateCarousels();
    }

    private void updateCarousels() {
        // Update top
        if (currentTop != null) {
            if (currentTop.getImagePath() != null && !currentTop.getImagePath().isEmpty()) {
                ImageIcon icon = loadScaledImage(currentTop.getImagePath(), 280, 230);
                topImageLabel.setIcon(icon);
                topImageLabel.setText("");
            } else {
                topImageLabel.setIcon(null);
                topImageLabel.setText("<html><center>No image<br/>" + currentTop.getName() + "</center></html>");
            }
            topInfoLabel.setText(currentTop.getName() + " - " + currentTop.getColor());
        } else {
            topImageLabel.setIcon(null);
            topImageLabel.setText("No tops");
            topInfoLabel.setText("");
        }
        
        // Update bottom
        if (currentBottom != null) {
            if (currentBottom.getImagePath() != null && !currentBottom.getImagePath().isEmpty()) {
                ImageIcon icon = loadScaledImage(currentBottom.getImagePath(), 280, 230);
                bottomImageLabel.setIcon(icon);
                bottomImageLabel.setText("");
            } else {
                bottomImageLabel.setIcon(null);
                bottomImageLabel.setText("<html><center>No image<br/>" + currentBottom.getName() + "</center></html>");
            }
            bottomInfoLabel.setText(currentBottom.getName() + " - " + currentBottom.getColor());
        } else {
            bottomImageLabel.setIcon(null);
            bottomImageLabel.setText("No bottoms");
            bottomInfoLabel.setText("");
        }
        
        // Update outfit display
        if (currentTop != null && currentBottom != null) {
            Outfit outfit = new Outfit(currentTop, currentBottom);
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
        JPanel p = new JPanel(new GridLayout(2,1,4,4));
        p.setBackground(new Color(255, 240, 245));
        p.add(outfitLabel);
        return p;
    }

    private void seedCloset() {
        closet.addTop(new Top("Black Shirt", "black", "solid", "warm", 
            "images/black_shirt.png", "short"));
        closet.addTop(new Top("Green Shirt", "green", "solid", "warm", 
            "images/green_shirt.png", "short"));
        closet.addBottom(new Bottom("Blue Jeans", "blue", "denim", "warm", 
            "images/blue_jeans.png", "full"));
        closet.addBottom(new Bottom("Khaki Pants", "khaki", "solid", "warm", 
            "images/khaki_pants.png", "full"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CluelessGUI gui = new CluelessGUI();
            gui.setVisible(true);
        });
    }
}