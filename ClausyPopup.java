import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javafoundations.LinkedBinaryTree;
import java.awt.*;

/**
 * Popup window for Clausy, the outfit recommendation assistant
 */
public class ClausyPopup extends JDialog {
    private OutfitQuiz quiz;
    private LinkedBinaryTree<String> currentNode;
    private JTextArea questionArea;
    private JButton yesButton;
    private JButton noButton;
    private JButton startOverButton;
    private JButton closeButton;
    private JPanel combinedButtonPanel;
    private CardLayout buttonCardLayout;
    
    public ClausyPopup(JFrame parent) {
        super(parent, "Clausy - Your Outfit Assistant", true);
        quiz = new OutfitQuiz();
        
        setSize(600, 500);
        setLocationRelativeTo(parent);
        setResizable(false);
        
        initComponents();
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(new Color(255, 240, 245));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Header with Clausy title and robot
        JPanel headerPanel = new JPanel(new BorderLayout(10, 5));
        headerPanel.setBackground(new Color(255, 240, 245));
        
        // Robot drawing panel
        JPanel robotPanel = new ClausyRobotPanel();
        robotPanel.setPreferredSize(new Dimension(120, 120));
        
        // Title panel
        JPanel titleTextPanel = new JPanel(new GridLayout(2, 1));
        titleTextPanel.setBackground(new Color(255, 240, 245));
        
        JLabel titleLabel = new JLabel("✨ Meet Clausy! ✨", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setForeground(new Color(150, 50, 150));
        
        JLabel subtitleLabel = new JLabel("Your AI Outfit Assistant", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.ITALIC, 15));
        subtitleLabel.setForeground(new Color(100, 100, 100));
        
        titleTextPanel.add(titleLabel);
        titleTextPanel.add(subtitleLabel);
        
        headerPanel.add(robotPanel, BorderLayout.WEST);
        headerPanel.add(titleTextPanel, BorderLayout.CENTER);
        
        // Question/Result display area
        questionArea = new JTextArea();
        questionArea.setEditable(false);
        questionArea.setLineWrap(true);
        questionArea.setWrapStyleWord(true);
        questionArea.setFont(new Font("Arial", Font.PLAIN, 16));
        questionArea.setBackground(Color.WHITE);
        questionArea.setForeground(Color.BLACK);
        questionArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 150, 200), 2),
            new EmptyBorder(15, 15, 15, 15)));
        
        JScrollPane scrollPane = new JScrollPane(questionArea);
        scrollPane.setBorder(null);
        
        // Button panel for Yes/No buttons (will be shown/hidden)
        JPanel yesNoButtonPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        yesNoButtonPanel.setBackground(new Color(255, 240, 245));
        
        yesButton = new JButton("✓ Yes");
        yesButton.setFont(new Font("Arial", Font.BOLD, 18));
        yesButton.setBackground(new Color(100, 180, 100));
        yesButton.setForeground(Color.WHITE);
        yesButton.setFocusPainted(false);
        yesButton.setOpaque(true);
        yesButton.setBorderPainted(false);
        yesButton.setPreferredSize(new Dimension(120, 50));
        yesButton.addActionListener(e -> handleAnswer(true));
        
        noButton = new JButton("✗ No");
        noButton.setFont(new Font("Arial", Font.BOLD, 18));
        noButton.setBackground(new Color(200, 100, 100));
        noButton.setForeground(Color.WHITE);
        noButton.setFocusPainted(false);
        noButton.setOpaque(true);
        noButton.setBorderPainted(false);
        noButton.setPreferredSize(new Dimension(120, 50));
        noButton.addActionListener(e -> handleAnswer(false));
        
        yesNoButtonPanel.add(yesButton);
        yesNoButtonPanel.add(noButton);
        
        // Button panel for Start Over button (will be shown when quiz is done)
        JPanel startOverButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        startOverButtonPanel.setBackground(new Color(255, 240, 245));
        
        startOverButton = new JButton("🔄 Start Over");
        startOverButton.setFont(new Font("Arial", Font.BOLD, 16));
        startOverButton.setBackground(new Color(150, 150, 200));
        startOverButton.setForeground(Color.WHITE);
        startOverButton.setFocusPainted(false);
        startOverButton.setOpaque(true);
        startOverButton.setBorderPainted(false);
        startOverButton.setPreferredSize(new Dimension(200, 50));
        startOverButton.addActionListener(e -> startQuiz());
        startOverButton.setVisible(false); // Hidden initially
        
        startOverButtonPanel.add(startOverButton);
        
        // Combined button panel using CardLayout to switch between Yes/No and Start Over
        buttonCardLayout = new CardLayout();
        combinedButtonPanel = new JPanel(buttonCardLayout);
        combinedButtonPanel.setBackground(new Color(255, 240, 245));
        combinedButtonPanel.add(yesNoButtonPanel, "YESNO");
        combinedButtonPanel.add(startOverButtonPanel, "STARTOVER");
        
        // Bottom panel with Close button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(new Color(255, 240, 245));
        
        closeButton = new JButton("✕ Close");
        closeButton.setFont(new Font("Arial", Font.BOLD, 13));
        closeButton.setBackground(new Color(150, 150, 150));
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.setOpaque(true);
        closeButton.setBorderPainted(false);
        closeButton.addActionListener(e -> dispose());
        
        bottomPanel.add(closeButton);
        
        // Add all panels
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(combinedButtonPanel, BorderLayout.SOUTH);
        
        JPanel outerPanel = new JPanel(new BorderLayout());
        outerPanel.setBackground(new Color(255, 240, 245));
        outerPanel.add(mainPanel, BorderLayout.CENTER);
        outerPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(outerPanel);
        
        // Start the quiz
        startQuiz();
    }
    
    private void startQuiz() {
        currentNode = quiz.dTree;
        displayCurrentNode();
    }
    
    private void displayCurrentNode() {
        if (currentNode == null) {
            questionArea.setText("Error: Quiz not initialized properly.");
            return;
        }
        
        String text = currentNode.getRootElement();
        
        // Check if this is a leaf node (recommendation)
        if (currentNode.getLeft().isEmpty() && currentNode.getRight().isEmpty()) {
            // This is a recommendation - show Start Over button
            questionArea.setText(text);
            buttonCardLayout.show(combinedButtonPanel, "STARTOVER");
        } else {
            // This is a question - show Yes/No buttons
            questionArea.setText(text);
            buttonCardLayout.show(combinedButtonPanel, "YESNO");
        }
    }
    
    private void handleAnswer(boolean isYes) {
        if (isYes) {
            currentNode = currentNode.getLeft();
        } else {
            currentNode = currentNode.getRight();
        }
        
        displayCurrentNode();
    }
    
    /**
     * Static method to show the popup
     */
    public static void show(JFrame parent) {
        ClausyPopup popup = new ClausyPopup(parent);
        popup.setVisible(true);
    }
    
    /**
     * Custom panel that draws a cute robot
     */
    private class ClausyRobotPanel extends JPanel {
        public ClausyRobotPanel() {
            setBackground(new Color(255, 240, 245));
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;
            
            // Robot body (rounded rectangle)
            g2d.setColor(new Color(180, 120, 220));
            g2d.fillRoundRect(centerX - 25, centerY - 10, 50, 40, 10, 10);
            
            // Robot head (circle)
            g2d.setColor(new Color(200, 150, 240));
            g2d.fillOval(centerX - 20, centerY - 35, 40, 35);
            
            // Antenna
            g2d.setStroke(new BasicStroke(2));
            g2d.setColor(new Color(150, 100, 200));
            g2d.drawLine(centerX, centerY - 35, centerX, centerY - 45);
            g2d.fillOval(centerX - 3, centerY - 50, 6, 6);
            
            // Eyes
            g2d.setColor(Color.WHITE);
            g2d.fillOval(centerX - 12, centerY - 25, 8, 8);
            g2d.fillOval(centerX + 4, centerY - 25, 8, 8);
            
            // Pupils
            g2d.setColor(new Color(100, 50, 150));
            g2d.fillOval(centerX - 10, centerY - 23, 4, 4);
            g2d.fillOval(centerX + 6, centerY - 23, 4, 4);
            
            // Smile
            g2d.setColor(new Color(150, 100, 200));
            g2d.setStroke(new BasicStroke(2));
            g2d.drawArc(centerX - 10, centerY - 15, 20, 12, 0, -180);
            
            // Arms
            g2d.setColor(new Color(180, 120, 220));
            g2d.fillRoundRect(centerX - 35, centerY - 5, 10, 25, 5, 5);
            g2d.fillRoundRect(centerX + 25, centerY - 5, 10, 25, 5, 5);
            
            // Legs
            g2d.fillRoundRect(centerX - 18, centerY + 30, 12, 20, 5, 5);
            g2d.fillRoundRect(centerX + 6, centerY + 30, 12, 20, 5, 5);
            
            // Body details (buttons)
            g2d.setColor(new Color(150, 100, 200));
            g2d.fillOval(centerX - 3, centerY, 6, 6);
            g2d.fillOval(centerX - 3, centerY + 10, 6, 6);
        }
    }
}