import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SCRButton extends JButton {
    private JPanel mainMenuPanel;
    private JPanel scoreboardPanel;
    private JPanel mainPanel; // Add a reference to the main panel

    public SCRButton(JPanel mainPanel, JPanel scoreboardPanel, JPanel mainMenuPanel) {
        super("Scoreboard");
        this.mainMenuPanel = mainMenuPanel;
        this.scoreboardPanel = scoreboardPanel;
        this.mainPanel = mainPanel;

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
                cardLayout.show(mainPanel, "scoreboard");

                displayScores();

                // Add "RETURN" button to the scoreboardPanel
                JButton returnButton = new JButton("RETURN");
                returnButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        cardLayout.show(mainPanel, "mainMenu");
                    }
                });

                // Create a panel to hold the "RETURN" button
                JPanel returnButtonPanel = new JPanel();
                returnButtonPanel.add(returnButton);

                // Add the returnButtonPanel to the bottom-left corner of scoreboardPanel
                scoreboardPanel.add(returnButtonPanel, BorderLayout.SOUTH);
                scoreboardPanel.revalidate();
                scoreboardPanel.repaint();
            }
        });
    }

    private void displayScores() {
        JLabel scoreLabel = new JLabel();
        Font font = new Font("8BIT WONDER", Font.BOLD, 64); // You can adjust the font size here
        scoreLabel.setFont(font);

        try {
            String filename = "SCORESAVE.txt";
            StringBuilder scores = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                scores.append(line).append("<br>"); // Add line breaks for separate scores
            }
            reader.close();
            scoreLabel.setText("<html>" + scores.toString() + "</html>"); // Use HTML for line breaks
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Center the scoreLabel within scoreboardPanel
        scoreboardPanel.removeAll();
        scoreboardPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.CENTER;
        scoreboardPanel.add(scoreLabel, gbc);

        scoreboardPanel.revalidate();
        scoreboardPanel.repaint();
    }
}
