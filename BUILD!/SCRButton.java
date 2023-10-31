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

    public SCRButton(JPanel mainMenuPanel, JPanel scoreboardPanel) {
        super("Scoreboard");
        this.mainMenuPanel = mainMenuPanel;
        this.scoreboardPanel = scoreboardPanel;

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) mainMenuPanel.getLayout();
                cardLayout.show(mainMenuPanel, "scoreboard");

                displayScores();
            }
        });
    }

    private void displayScores() {
        JTextArea textArea = new JTextArea(10, 40);
        textArea.setEditable(false);

        try {
            String filename = "SCORESAVE.txt";
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                textArea.append(line + "\n");
            }
            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(textArea);

        scoreboardPanel.add(scrollPane, BorderLayout.CENTER);
        scoreboardPanel.revalidate();
        scoreboardPanel.repaint();
    }
}
