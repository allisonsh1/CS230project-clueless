import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javafoundations.CircularArrayQueue;
import java.awt.*;

/**
 * Main GUI for Cher's Clueless Closet application.
 * @author Allison, Vivian & AI
 */
public class CluelessGUI extends JFrame {
    private Closet closet;
    private Carousel topCarousel;
    private Carousel bottomCarousel;
    private OutfitHistory outfitHistory;
    private Matcher matcher;
    private JLabel topImageLabel;
    private JLabel bottomImageLabel;
    private JLabel topInfoLabel;
    private JLabel bottomInfoLabel;
    private JLabel outfitLabel = new JLabel("Select a top and bottom to create an outfit");
    private JTextArea historyDisplay;
    
    // Chic color palette
    private final Color BLUSH_PINK = new Color(255, 228, 240);
    private final Color ROSE_GOLD = new Color(183, 110, 121);
    private final Color DEEP_PLUM = new Color(142, 68, 173);
    private final Color LAVENDER = new Color(230, 230, 250);
    private final Color CREAM = new Color(255, 253, 248);
    private final Color GOLD = new Color(212, 175, 55);

    /**
     * Constructor for CluelessGUI
     * @param shouldLoadHistory whether to load saved outfit history on startup
     */
    public CluelessGUI(boolean shouldLoadHistory) {
        super("Cher's Clueless Closet");
        closet = new Closet(8);
        outfitHistory = new OutfitHistory();
        matcher = new Matcher();
        seedCloset();
        loadQueues();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 1100);
        setLayout(new BorderLayout(10,10));
        
        // Create patterned background panel
        JPanel backgroundPanel = new PatternedPanel();
        backgroundPanel.setLayout(new BorderLayout(10,10));
        setContentPane(backgroundPanel);

        JPanel mainPanel = new JPanel(new BorderLayout(12,12));
        mainPanel.setBorder(new EmptyBorder(20,20,20,20));
        mainPanel.setOpaque(false);
        
        mainPanel.add(makeTitlePanel(), BorderLayout.NORTH);
        mainPanel.add(makeCarouselPanel(), BorderLayout.CENTER);
        mainPanel.add(makeControlPanel(), BorderLayout.SOUTH);
        mainPanel.add(makeHistoryPanel(), BorderLayout.EAST);

        backgroundPanel.add(mainPanel, BorderLayout.CENTER);
        
        if(shouldLoadHistory){
            loadSavedHistory();
        }

        updateCarousels();
    }
    
    /**
     * Custom panel with a chic pattern background
     */
    class PatternedPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Base background color - soft blush pink
            g2d.setColor(new Color(255, 240, 245));
            g2d.fillRect(0, 0, getWidth(), getHeight());
            
            // Large polka dots - pink
            g2d.setColor(new Color(255, 182, 193));
            for (int x = 0; x < getWidth(); x += 70) {
                for (int y = 0; y < getHeight(); y += 70) {
                    g2d.fillOval(x, y, 25, 25);
                }
            }
            
            // Small polka dots offset - lavender
            g2d.setColor(new Color(216, 191, 216));
            for (int x = 35; x < getWidth(); x += 70) {
                for (int y = 35; y < getHeight(); y += 70) {
                    g2d.fillOval(x, y, 12, 12);
                }
            }
            
            // Tiny accent dots - white
            g2d.setColor(new Color(255, 255, 255));
            for (int x = 50; x < getWidth(); x += 70) {
                for (int y = 20; y < getHeight(); y += 70) {
                    g2d.fillOval(x, y, 6, 6);
                }
            }
        }
    }

    /**
     * Creates the title panel with Clausy button.
     * @return
     */
    private JPanel makeTitlePanel() {
        JPanel titlePanel = new JPanel(new BorderLayout(10, 10));
        titlePanel.setOpaque(false);
        titlePanel.setBorder(new EmptyBorder(0, 0, 20, 0));
        
        JPanel titleTextPanel = new JPanel(new GridLayout(2, 1, 0, 8));
        titleTextPanel.setOpaque(false);
        
        JLabel mainTitle = new JLabel("✨ Clueless Closet ✨", SwingConstants.CENTER);
        mainTitle.setFont(new Font("Georgia", Font.BOLD, 42));
        mainTitle.setForeground(DEEP_PLUM);
        
        JLabel subtitle = new JLabel("Create Your Perfect Outfit", SwingConstants.CENTER);
        subtitle.setFont(new Font("Brush Script MT", Font.ITALIC, 24));
        subtitle.setForeground(ROSE_GOLD);
        
        titleTextPanel.add(mainTitle);
        titleTextPanel.add(subtitle);
        
        // Clausy button on the right
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        
        JButton clausyButton = new JButton("💜 Ask Clausy");
        clausyButton.setFont(new Font("Georgia", Font.BOLD, 15));
        clausyButton.setBackground(DEEP_PLUM);
        clausyButton.setForeground(Color.WHITE);
        clausyButton.setFocusPainted(false);
        clausyButton.setOpaque(true);
        clausyButton.setBorderPainted(false);
        clausyButton.setBorder(new EmptyBorder(10, 20, 10, 20));
        clausyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        clausyButton.setToolTipText("Get outfit recommendations from Clausy!");
        clausyButton.addActionListener(e -> openClausy());
        
        // Add hover effect
        clausyButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                clausyButton.setBackground(new Color(160, 90, 193));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                clausyButton.setBackground(DEEP_PLUM);
            }
        });
        
        buttonPanel.add(clausyButton);
        
        titlePanel.add(titleTextPanel, BorderLayout.CENTER);
        titlePanel.add(buttonPanel, BorderLayout.EAST);
        
        return titlePanel;
    }
    
    /**
     * Opens the Clausy popup dialog.
     */
    private void openClausy() {
        ClausyPopup.show(this);
    }

    /**
     *  Loads the carousels from the closet.
     */
    private void loadQueues() {
        topCarousel = new Carousel(closet, 0);  //row 0 = tops
        bottomCarousel = new Carousel(closet, 1);  //row 1 = bottoms
    }

    /**
     * Loads saved outfit history from file.
     */
    private void loadSavedHistory() {
        try {
            SavingHistory savedHistory = new SavingHistory();
            CircularArrayQueue<Outfit> savedOutfits = savedHistory.readHistory();
            
            System.out.println("Loaded " + savedOutfits.size() + " outfits from history");
            
            //Add each saved outfit to the outfit history
            while (!savedOutfits.isEmpty()) {
                Outfit outfit = savedOutfits.dequeue();
                outfitHistory.addOutfit(outfit);
                System.out.println("Added outfit: " + outfit.toString());
            }
            
            updateHistoryDisplay();
            System.out.println("History display updated");
        } catch (Exception e) {
            System.err.println("Error loading history: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Could not load previous history: " + e.getMessage(), 
                "Load Error", 
                JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Creates the carousel panel with top and bottom carousels.
     * @return panel containing the carousels
     */
    private JPanel makeCarouselPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 20, 15));
        panel.setOpaque(false);
        
        // Top carousel
        JPanel topCarouselPanel = new JPanel(new BorderLayout(8,8));
        topCarouselPanel.setBackground(CREAM);
        topCarouselPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ROSE_GOLD, 3, true),
            new EmptyBorder(15,15,15,15)));
        
        JLabel topTitle = new JLabel("♡ TOPS ♡", SwingConstants.CENTER);
        topTitle.setFont(new Font("Georgia", Font.BOLD, 22));
        topTitle.setForeground(ROSE_GOLD);
        topCarouselPanel.add(topTitle, BorderLayout.NORTH);
        
        topImageLabel = new JLabel("", SwingConstants.CENTER);
        topImageLabel.setPreferredSize(new Dimension(450, 500));
        topImageLabel.setMinimumSize(new Dimension(450, 500));
        topImageLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(LAVENDER, 2),
            new EmptyBorder(10,10,10,10)));
        topImageLabel.setBackground(Color.WHITE);
        topImageLabel.setOpaque(true);
        topImageLabel.setVerticalAlignment(SwingConstants.CENTER);
        topImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topCarouselPanel.add(topImageLabel, BorderLayout.CENTER);
        
        JPanel topNav = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        topNav.setBackground(CREAM);
        
        JButton topPrev = createStyledButton("← Previous");
        JButton topNext = createStyledButton("Next →");
        
        topPrev.addActionListener(e -> navigateTopPrev());
        topNext.addActionListener(e -> navigateTopNext());
        
        topInfoLabel = new JLabel("Item info");
        topInfoLabel.setFont(new Font("Georgia", Font.ITALIC, 14));
        topInfoLabel.setForeground(DEEP_PLUM);
        
        topNav.add(topPrev);
        topNav.add(topInfoLabel);
        topNav.add(topNext);
        topCarouselPanel.add(topNav, BorderLayout.SOUTH);
        
        // Bottom carousel
        JPanel bottomCarouselPanel = new JPanel(new BorderLayout(8,8));
        bottomCarouselPanel.setBackground(CREAM);
        bottomCarouselPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(DEEP_PLUM, 3, true),
            new EmptyBorder(15,15,15,15)));
        
        JLabel bottomTitle = new JLabel("♡ BOTTOMS ♡", SwingConstants.CENTER);
        bottomTitle.setFont(new Font("Georgia", Font.BOLD, 22));
        bottomTitle.setForeground(DEEP_PLUM);
        bottomCarouselPanel.add(bottomTitle, BorderLayout.NORTH);
        
        bottomImageLabel = new JLabel("", SwingConstants.CENTER);
        bottomImageLabel.setPreferredSize(new Dimension(450, 500));
        bottomImageLabel.setMinimumSize(new Dimension(450, 500));
        bottomImageLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(LAVENDER, 2),
            new EmptyBorder(10,10,10,10)));
        bottomImageLabel.setBackground(Color.WHITE);
        bottomImageLabel.setOpaque(true);
        bottomImageLabel.setVerticalAlignment(SwingConstants.CENTER);
        bottomImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bottomCarouselPanel.add(bottomImageLabel, BorderLayout.CENTER);
        
        JPanel bottomNav = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        bottomNav.setBackground(CREAM);
        
        JButton bottomPrev = createStyledButton("← Previous");
        JButton bottomNext = createStyledButton("Next →");
        
        bottomPrev.addActionListener(e -> navigateBottomPrev());
        bottomNext.addActionListener(e -> navigateBottomNext());
        
        bottomInfoLabel = new JLabel("Item info");
        bottomInfoLabel.setFont(new Font("Georgia", Font.ITALIC, 14));
        bottomInfoLabel.setForeground(DEEP_PLUM);
        
        bottomNav.add(bottomPrev);
        bottomNav.add(bottomInfoLabel);
        bottomNav.add(bottomNext);
        bottomCarouselPanel.add(bottomNav, BorderLayout.SOUTH);
        
        panel.add(topCarouselPanel);
        panel.add(bottomCarouselPanel);
        return panel;
    }
    
    /**
     * Creates a styled button with consistent design
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Georgia", Font.PLAIN, 13));
        button.setBackground(LAVENDER);
        button.setForeground(DEEP_PLUM);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(8, 16, 8, 16));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(210, 210, 240));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(LAVENDER);
            }
        });
        
        return button;
    }

    /**
     * Navigates to the next item in the top carousel.
     */
    private void navigateTopNext() {
        if (topCarousel.isEmpty()) return;
        topCarousel.next();
        updateCarousels();
    }

    /**
     * Navigates to the previous item in the top carousel.
     */
    private void navigateTopPrev() {
        if (topCarousel.isEmpty()) return;
        topCarousel.previous();
        updateCarousels();
    }

    /**
     * Navigates to the next item in the bottom carousel.
     */
    private void navigateBottomNext() {
        if (bottomCarousel.isEmpty()) return;
        bottomCarousel.next();
        updateCarousels();
    }

    /**
     * Navigates to the previous item in the bottom carousel.
     */
    private void navigateBottomPrev() {
        if (bottomCarousel.isEmpty()) return;
        bottomCarousel.previous();
        updateCarousels();
    }

    /**
     * Updates the displayed images and info for both carousels.
     */
    private void updateCarousels() {
        // Update top
        if (!topCarousel.isEmpty()) {
            Top top = (Top) topCarousel.current();
            if (top.getImagePath() != null && !top.getImagePath().isEmpty()) {
                ImageIcon icon = loadScaledImageProportional(top.getImagePath(), 430, 480);
                topImageLabel.setIcon(icon);
                topImageLabel.setText("");
            } else {
                topImageLabel.setIcon(null);
                topImageLabel.setText("<html><center>No image<br/>" + top.getName() + "</center></html>");
            }
            topInfoLabel.setText(top.getName() + " • " + top.getColor());
        } else {
            topImageLabel.setIcon(null);
            topImageLabel.setText("No tops");
            topInfoLabel.setText("");
        }
        
        // Update bottom
        if (!bottomCarousel.isEmpty()) {
            Bottom bottom = (Bottom) bottomCarousel.current();
            if (bottom.getImagePath() != null && !bottom.getImagePath().isEmpty()) {
                ImageIcon icon = loadScaledImageProportional(bottom.getImagePath(), 430, 480);
                bottomImageLabel.setIcon(icon);
                bottomImageLabel.setText("");
            } else {
                bottomImageLabel.setIcon(null);
                bottomImageLabel.setText("<html><center>No image<br/>" + bottom.getName() + "</center></html>");
            }
            bottomInfoLabel.setText(bottom.getName() + " • " + bottom.getColor());
        } else {
            bottomImageLabel.setIcon(null);
            bottomImageLabel.setText("No bottoms");
            bottomInfoLabel.setText("");
        }
        
        // Update outfit display
        if (!topCarousel.isEmpty() && !bottomCarousel.isEmpty()) {
            Outfit outfit = new Outfit((Top)topCarousel.current(), (Bottom) bottomCarousel.current());
            outfitLabel.setText("✨ Current Outfit: " + outfit.toString() + " ✨");
        }
    }

    /**
     * Loads and scales an image from the given path while maintaining aspect ratio.
     * @param path
     * @param maxWidth
     * @param maxHeight
     * @return
     */
    private ImageIcon loadScaledImageProportional(String path, int maxWidth, int maxHeight) {
        try {
            ImageIcon original = new ImageIcon(path);
            int originalWidth = original.getIconWidth();
            int originalHeight = original.getIconHeight();
            
            // Calculate scaling factor to fit within maxWidth and maxHeight while maintaining aspect ratio
            double widthRatio = (double) maxWidth / originalWidth;
            double heightRatio = (double) maxHeight / originalHeight;
            double scale = Math.min(widthRatio, heightRatio);
            
            // Calculate new dimensions
            int newWidth = (int) (originalWidth * scale);
            int newHeight = (int) (originalHeight * scale);
            
            Image scaled = original.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Loads and scales an image from the given path.
     * @param path
     * @param width
     * @param height
     * @return
     */
    private ImageIcon loadScaledImage(String path, int width, int height) {
        try {
            ImageIcon original = new ImageIcon(path);
            Image scaled = original.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     *  Creates the control panel with buttons and outfit label.
     * @return
     */
    private JPanel makeControlPanel() {
        JPanel p = new JPanel(new GridLayout(3,1,8,8));
        p.setOpaque(false);
        
        JButton saveOutfitBtn = new JButton("💖 Save Current Outfit");
        saveOutfitBtn.setFont(new Font("Georgia", Font.BOLD, 18));
        saveOutfitBtn.setBackground(new Color(255, 105, 180)); // Hot pink
        saveOutfitBtn.setForeground(Color.WHITE);
        saveOutfitBtn.setFocusPainted(false);
        saveOutfitBtn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 20, 147), 3),
            new EmptyBorder(15, 25, 15, 25)));
        saveOutfitBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        saveOutfitBtn.setOpaque(true);
        saveOutfitBtn.addActionListener(e -> saveCurrentOutfit());
        
        JButton saveAndExitBtn = new JButton("👗 Save & Exit");
        saveAndExitBtn.setFont(new Font("Georgia", Font.BOLD, 18));
        saveAndExitBtn.setBackground(new Color(138, 43, 226)); // Blue violet
        saveAndExitBtn.setForeground(Color.WHITE);
        saveAndExitBtn.setFocusPainted(false);
        saveAndExitBtn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(75, 0, 130), 3),
            new EmptyBorder(15, 25, 15, 25)));
        saveAndExitBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        saveAndExitBtn.setOpaque(true);
        saveAndExitBtn.addActionListener(e -> saveAndExit());
        
        // Hover effects
        saveOutfitBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                saveOutfitBtn.setBackground(new Color(255, 20, 147));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                saveOutfitBtn.setBackground(new Color(255, 105, 180));
            }
        });
        
        saveAndExitBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                saveAndExitBtn.setBackground(new Color(75, 0, 130));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                saveAndExitBtn.setBackground(new Color(138, 43, 226));
            }
        });
        
        outfitLabel.setFont(new Font("Georgia", Font.ITALIC, 16));
        outfitLabel.setForeground(DEEP_PLUM);
        outfitLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        p.add(saveOutfitBtn);
        p.add(saveAndExitBtn);
        p.add(outfitLabel);
        return p;
    }

    /**
     * Creates the outfit history panel.
     * @return
     */
    private JPanel makeHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout(8,8));
        panel.setBackground(CREAM);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(GOLD, 3, true),
            new EmptyBorder(15,15,15,15)));
        panel.setPreferredSize(new Dimension(270, 0));
        
        JLabel title = new JLabel("✦ Outfit History ✦", SwingConstants.CENTER);
        title.setFont(new Font("Georgia", Font.BOLD, 16));
        title.setForeground(ROSE_GOLD);
        title.setBorder(new EmptyBorder(0, 0, 10, 0));
        
        JLabel subtitle = new JLabel("(Last 7 outfits)", SwingConstants.CENTER);
        subtitle.setFont(new Font("Georgia", Font.ITALIC, 12));
        subtitle.setForeground(DEEP_PLUM);
        
        JPanel titlePanel = new JPanel(new GridLayout(2, 1));
        titlePanel.setBackground(CREAM);
        titlePanel.add(title);
        titlePanel.add(subtitle);
        
        historyDisplay = new JTextArea();
        historyDisplay.setEditable(false);
        historyDisplay.setFont(new Font("Georgia", Font.PLAIN, 12));
        historyDisplay.setText("No outfits saved yet!\nStart creating looks ♡");
        historyDisplay.setLineWrap(true);
        historyDisplay.setWrapStyleWord(true);
        historyDisplay.setBackground(new Color(255, 250, 250));
        historyDisplay.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JScrollPane scroll = new JScrollPane(historyDisplay);
        scroll.setBorder(BorderFactory.createLineBorder(LAVENDER, 2));
        
        panel.add(titlePanel, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        
        return panel;
    }

    /**
     *  Saves the current outfit to history.
     */
    private void saveCurrentOutfit() {
        if (topCarousel.isEmpty() || bottomCarousel.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please select both a top and bottom first! ♡", 
                "Oops!", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Outfit newOutfit = new Outfit((Top)topCarousel.current(), (Bottom)bottomCarousel.current());
        
        // Check if the outfit clashes
        if (matcher.clashes(newOutfit)) {
            JOptionPane.showMessageDialog(this, 
                "⚠️ Fashion Alert! This outfit has clashing elements.\n\n" +
                "Try mixing different colors, patterns, or seasons! ✨", 
                "Style Mismatch", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // If no clashes, save the outfit
        outfitHistory.addOutfit(newOutfit);
        updateHistoryDisplay();
        JOptionPane.showMessageDialog(this, 
            "Outfit saved! You look fabulous! 💖✨", 
            "Success!", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     *  Saves the outfit history to file and exits the application.
     */
    private void saveAndExit() {
        try {
            CircularArrayQueue<Outfit> historyQueue = getHistoryQueue();
            SavingHistory savingHistory = new SavingHistory();
            savingHistory.saveHistory(historyQueue);
            
            JOptionPane.showMessageDialog(this, 
                "All outfits saved successfully! 👗✨\n\nYou're totally ready to slay!\nSee you next time, fashionista! 💕", 
                "Bye Bye!", 
                JOptionPane.INFORMATION_MESSAGE);
            
            System.exit(0);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error saving history: " + e.getMessage(), 
                "Oops!", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * Gets the outfit history queue.
     * @return queue of outfits
     */
    private CircularArrayQueue<Outfit> getHistoryQueue() {
        return outfitHistory.getHistory();
    }

    /**
     * Updates the outfit history display.
     */
    private void updateHistoryDisplay() {
        historyDisplay.setText(outfitHistory.getHistoryDisplay());
    }

    /**
     * Seeds the closet with initial clothing items.
     */
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
        closet.addTop(new Top("Grey Lace Longsleeve", "grey", "lace", "cold", 
            "images/grey_lace_longsleeve.jpeg", "long"));
        closet.addTop(new Top("Grey Longsleeve", "grey", "solid", "cold", 
            "images/grey_longsleeve.jpeg", "long"));
        closet.addTop(new Top("Lace Longsleeve", "white", "lace", "cold", 
            "images/lace_longsleeve.jpeg", "long"));
        closet.addTop(new Top("Navy Boston Hoodie", "navy", "graphic", "cold", 
            "images/navy_boston_hoodie.jpeg", "long"));
        closet.addTop(new Top("Navy Button Up", "navy", "solid", "warm", 
            "images/navy_buttonup.jpeg", "short"));
        closet.addTop(new Top("Navy Malibu Hoodie", "navy", "graphic", "cold", 
            "images/navy_malibu_hoodie.jpeg", "long"));
        closet.addTop(new Top("Patches Tube Top", "multicolor", "graphic", "warm", 
            "images/patches_tube.jpeg", "sleeveless"));
        closet.addTop(new Top("Pink Stripe Tank", "pink", "stripe", "warm", 
            "images/pink_stripe_tank.jpeg", "sleeveless"));
        closet.addTop(new Top("Red Cardigan", "red", "solid", "cold", 
            "images/red_cardigan.jpeg", "long"));
        closet.addTop(new Top("Red Graphic Tee", "red", "graphic", "warm", 
            "images/red_graphic.jpeg", "short"));
        closet.addTop(new Top("Red Plaid Button Up", "red", "plaid", "warm", 
            "images/red_plaid_buttonup.jpeg", "short"));
        closet.addTop(new Top("Red Star Tank", "red", "graphic", "warm", 
            "images/red_star_tank.jpeg", "sleeveless"));
        closet.addTop(new Top("Stripe Sweater", "multicolor", "stripe", "cold", 
            "images/stripe_sweater.jpeg", "long"));
        closet.addTop(new Top("Striped Tube Top", "multicolor", "stripe", "warm", 
            "images/striped_tube.jpeg", "sleeveless"));
        closet.addTop(new Top("Twilight Tee", "grey", "graphic", "warm", 
            "images/twilight_tee.jpeg", "short"));
        closet.addTop(new Top("UCSC Crew", "grey", "graphic", "warm", 
            "images/ucsc_crew.jpeg", "short"));
        closet.addTop(new Top("White Button Up", "white", "solid", "warm", 
            "images/white_buttonup.jpeg", "short"));
        closet.addTop(new Top("White Heart Tank", "white", "graphic", "warm", 
            "images/white_heart_tank.jpeg", "sleeveless"));
        closet.addTop(new Top("White Linen Tank", "white", "solid", "warm", 
            "images/white_linen_tank.jpeg", "sleeveless"));
        closet.addTop(new Top("White Word Tank", "white", "graphic", "warm", 
            "images/white_word_tank.jpeg", "sleeveless"));
        
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
        closet.addBottom(new Bottom("Lace Up Shorts", "white", "solid", "warm", 
            "images/laceup_shorts.jpeg", "short"));
        closet.addBottom(new Bottom("Light Blue Jeans", "blue", "denim", "warm", 
            "images/light_blue_jeans.jpeg", "full"));
        closet.addBottom(new Bottom("Low Rise Jeans", "blue", "denim", "warm", 
            "images/low_rise_jeans.jpeg", "full"));
        closet.addBottom(new Bottom("Midi Jean Skirt", "blue", "denim", "warm", 
            "images/midi_jean_skirt.jpeg", "short"));
        closet.addBottom(new Bottom("Navy Anchor Shorts", "navy", "graphic", "warm", 
            "images/navy_anchor_shorts.jpeg", "short"));
        closet.addBottom(new Bottom("Navy Yoga Pants", "navy", "solid", "cold", 
            "images/navy_yogapants.jpeg", "full"));
        closet.addBottom(new Bottom("Pink Linen Pants", "pink", "solid", "warm", 
            "images/pink_linen_pants.jpeg", "full"));
        closet.addBottom(new Bottom("Studded Jeans", "blue", "denim", "warm", 
            "images/studded_jeans.jpeg", "full"));
        closet.addBottom(new Bottom("White Jeans", "white", "denim", "warm", 
            "images/white_jeans.jpeg", "full"));
        closet.addBottom(new Bottom("White Lace Maxi", "white", "lace", "warm", 
            "images/white_lace_maxi.jpeg", "full"));
    }
}