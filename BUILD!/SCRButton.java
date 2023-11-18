import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.awt.Image;
import java.awt.Dimension;

// Kelas SCRButton merepresentasikan tombol "Scoreboard" pada permainan Galaga.
public class SCRButton extends JButton {
    // Referensi ke panel utama, panel scoreboard, dan panel menu utama
    private JPanel mainMenuPanel;
    private JPanel scoreboardPanel;
    private JPanel mainPanel;

    // Konstruktor untuk SCRButton, menerima referensi ke panel utama, panel scoreboard, dan panel menu utama sebagai parameter
    public SCRButton(CardLayout cardLayout, JPanel mainPanel, JPanel scoreboardPanel, JPanel mainMenuPanel, JFrame frame, JPanel panel, JLabel gameNameLabel) {
        super("");
        this.mainMenuPanel = mainMenuPanel;
        this.scoreboardPanel = scoreboardPanel;
        this.mainPanel = mainPanel;
        URL imageUrl = getClass().getResource("./BScoreboardButton.png");
        ImageIcon ii = new ImageIcon(imageUrl);
        Image scaledImage = ii.getImage().getScaledInstance(205, 55, Image.SCALE_SMOOTH);
        setIcon(new ImageIcon(scaledImage));

        setPreferredSize(new Dimension(50, 50));
        setHorizontalTextPosition(JButton.CENTER);
        setVerticalTextPosition(JButton.CENTER);

        // Menambahkan ActionListener untuk menanggapi klik tombol "Scoreboard"
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Menggunakan CardLayout untuk beralih ke panel scoreboard di dalam panel utama
                CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
                cardLayout.show(mainPanel, "scoreboard");
                frame.remove(mainMenuPanel);
                frame.remove(panel);
                //System.out.println("\n SB mainmenupanel = "+ mainMenuPanel);
                //System.out.println("\n SB mainpanel = "+mainPanel);

                // Menampilkan skor pada panel scoreboard
                displayScores();

                // Menambahkan tombol "RETURN" untuk kembali ke panel menu utama
                JButton returnButton = new JButton("");
 
                URL imageUrl = getClass().getResource("./BMainMenuButtonBlue.png");
                ImageIcon ii = new ImageIcon(imageUrl);
                Image scaledImage = ii.getImage().getScaledInstance(205, 55, Image.SCALE_SMOOTH);
                
                returnButton.setIcon(new ImageIcon(scaledImage));
                returnButton.setPreferredSize(new Dimension(200, 50));
                returnButton.setHorizontalTextPosition(JButton.CENTER);
                returnButton.setVerticalTextPosition(JButton.CENTER);

                returnButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Switch back to the original main panel
                        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
                        cardLayout.show(mainPanel, "mainMenu");
                        //System.out.println("\n SCR mainpanel = "+mainPanel);

                        // Clear the scoreboard panel
                        scoreboardPanel.removeAll();
                        scoreboardPanel.revalidate();
                        scoreboardPanel.repaint();
                        //frame.add(panel, BorderLayout.CENTER);
                        
                        panel.add(mainPanel, BorderLayout.CENTER);
                        
                        frame.add(panel);
                        frame.getContentPane().add(mainPanel);
                        frame.setResizable(false);
                        frame.setVisible(true);
                        frame.repaint();

                        panel.revalidate();
                        panel.setVisible(true);
                        panel.repaint();
                        
                        
                    }
                });

                // Membuat panel untuk menampung tombol "RETURN"
                JPanel returnButtonPanel = new JPanel();
                returnButtonPanel.add(returnButton);

                // Mengatur tata letak returnButtonPanel untuk menempatkan tombol di tengah
                returnButtonPanel.setLayout(new BoxLayout(returnButtonPanel, BoxLayout.PAGE_AXIS));
                returnButtonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

                // Menambahkan panel tombol "RETURN" ke bagian selatan panel scoreboard
                scoreboardPanel.add(returnButtonPanel, BorderLayout.CENTER);
                scoreboardPanel.revalidate();
                scoreboardPanel.repaint();
            }
        });
    }

    // Metode untuk menampilkan skor dari file SCORESAVE.txt ke dalam scoreboardPanel
    private void displayScores() {
        JLabel scoreLabel = new JLabel();
        Font font = new Font("8BIT WONDER", Font.BOLD, 64); // Menyesuaikan ukuran font di sini
        scoreLabel.setFont(font);

        try {
            // Membaca skor dari file SCORESAVE.txt
            String filename = "SCORESAVE.txt";
            StringBuilder scores = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                scores.append(line).append("<br>"); // Menambahkan pemisah baris untuk setiap skor
            }
            reader.close();

            // Mengatur teks label dengan format HTML untuk memungkinkan pemisah baris
            scoreLabel.setText("<html>" + scores.toString() + "</html>");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Membersihkan panel scoreboard dan menambahkan label skor di tengah
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


// jangan di ss