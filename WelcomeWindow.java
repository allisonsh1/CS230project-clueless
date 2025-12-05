import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;

/**
 * Welcome screen that allows users to either start fresh or load saved history
 */
public class WelcomeWindow extends JFrame {
    
    public WelcomeWindow() {
        super("Welcome to Cher's Closet");
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null); // Center on screen
        setResizable(false);
        
        // Main panel with gradient-like background
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(new Color(255, 240, 245));
        mainPanel.setBorder(new EmptyBorder(30, 40, 30, 40));
        
        // Title section
        JPanel titlePanel = new JPanel(new GridLayout(3, 1, 5, 5));
        titlePanel.setBackground(new Color(255, 240, 245));
        
       /* JLabel titleLabel = new JLabel("✨ Cher's Clueless Closet ✨", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        //titleLabel.setForeground(Color.BLACK);
        titleLabel.setForeground(new Color(30, 30, 30)); // dark charcoal
        */
        
        JLabel titleLabel = new JLabel("Cher's Clueless Closet", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(115, 56, 133));
        

        
        JLabel subtitleLabel = new JLabel("Your Digital Wardrobe Assistant", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        subtitleLabel.setForeground(new Color(100, 100, 100));
        
        JLabel welcomeLabel = new JLabel("How would you like to start?", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        welcomeLabel.setForeground(new Color(80, 80, 80));
        
        titlePanel.add(titleLabel);
        titlePanel.add(subtitleLabel);
        titlePanel.add(welcomeLabel);
        
        // Button section
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 15, 15));
        buttonPanel.setBackground(new Color(255, 240, 245));
        buttonPanel.setBorder(new EmptyBorder(30, 20, 30, 20));
        
        // Start Fresh button
        JButton startFreshBtn = createStyledButton(
            "🆕 Start Fresh",
            "Begin with an empty outfit history",
            new Color(200, 150, 250),
            Color.WHITE
        );
        startFreshBtn.addActionListener(e -> startApplication(false));
        
        // Load History button
        JButton loadHistoryBtn = createStyledButton(
            "📂 Load Previous History",
            "Continue from where you left off",
            new Color(150, 100, 200),
            Color.WHITE
        );
        loadHistoryBtn.addActionListener(e -> startApplication(true));
        
        // Check if history file exists and disable button if not
        File historyFile = new File("history.txt");
        if (!historyFile.exists()) {
            loadHistoryBtn.setEnabled(false);
            loadHistoryBtn.setText("📂 Load Previous History (No history found)");
            loadHistoryBtn.setBackground(new Color(200, 200, 200));
        }
        
        buttonPanel.add(startFreshBtn);
        buttonPanel.add(loadHistoryBtn);
        
        // Info section at bottom
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBackground(new Color(255, 240, 245));
        
        JLabel infoLabel = new JLabel(
            "<html><center><i>Tip: Save your outfit history to access it later!</i></center></html>",
            SwingConstants.CENTER
        );
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        infoLabel.setForeground(new Color(120, 120, 120));
        
        infoPanel.add(infoLabel, BorderLayout.CENTER);
        
        // Add all panels to main panel
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(infoPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    /**
     * Creates a styled button with hover effects
     */
    private JButton createStyledButton(String text, String tooltip, Color bgColor, Color fgColor) {
        JButton button = new JButton("<html><center>" + text + "</center></html>");
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setToolTipText(tooltip);
        button.setPreferredSize(new Dimension(400, 80));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (button.isEnabled()) {
                    button.setBackground(bgColor.brighter());
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (button.isEnabled()) {
                    button.setBackground(bgColor);
                }
            }
        });
        
        return button;
    }
    
    /**
     * Starts the main application with or without loading history
     */
    private void startApplication(boolean shouldLoadHistory) {
        // Close welcome window
        this.setVisible(false);
        this.dispose();
        
        // Launch main GUI
        SwingUtilities.invokeLater(() -> {
            CluelessGUI gui = new CluelessGUI(shouldLoadHistory);
            gui.setVisible(true);
        });
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WelcomeWindow welcome = new WelcomeWindow();
            welcome.setVisible(true);
        });
    }
}