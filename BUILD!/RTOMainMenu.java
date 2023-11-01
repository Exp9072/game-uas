import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RTOMainMenu extends JPanel {
    private final JFrame frame;
    private JPanel mainMenuPanel;

    public RTOMainMenu(JFrame frame, JPanel mainMenuPanel) {
        this.frame = frame;
        this.mainMenuPanel = mainMenuPanel;

        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);

        JButton returnButton = new JButton("Return to Main Menu");
        returnButton.setFont(new Font("Arial", Font.PLAIN, 24));
        returnButton.setForeground(Color.WHITE);
        returnButton.setBackground(Color.RED);

        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                returnToMainMenu();
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 300, 0);

        add(returnButton, gbc);
    }

    public void returnToMainMenu() {
        frame.getContentPane().removeAll(); // Remove all components from the content pane
        frame.getContentPane().add(mainMenuPanel); // Add the original main menu panel
        frame.getContentPane().revalidate(); // Revalidate the components hierarchy
        frame.getContentPane().repaint(); // Repaint the frame
    }

    public void showGameOverScreen() {
        // Create the game over panel
        JPanel gameOverPanel = new JPanel();
        gameOverPanel.setLayout(new GridBagLayout());
        gameOverPanel.setBackground(Color.BLACK);
    
        // Create and customize the game over label
        JLabel gameOverLabel = new JLabel("Game Over");
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 36));
        gameOverLabel.setForeground(Color.RED);
    
        // Create a button to return to the main menu
        JButton returnButton = new JButton("Return to Main Menu");
        returnButton.setFont(new Font("Arial", Font.PLAIN, 24));
        returnButton.setForeground(Color.WHITE);
        returnButton.setBackground(Color.RED);
    
        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                returnToMainMenu();
            }
        });
    
        // Add the game over label and return button to the game over panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        gameOverPanel.add(gameOverLabel, gbc);
    
        gbc.gridy = 1;
        gbc.insets = new Insets(00, 0, 0, 0);
        gameOverPanel.add(returnButton, gbc);
    
        // Clear the frame's content and add the game over panel
        frame.getContentPane().removeAll();
        frame.getContentPane().add(gameOverPanel);
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
    }
}
