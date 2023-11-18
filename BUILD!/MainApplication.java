import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.net.URL;

public class MainApplication {
    private static JPanel panel;
    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "true");
        // Membuat objek JFrame untuk menampung permainan
        JFrame frame = new JFrame("Star-Hawk Invasion");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Membuat objek StarHawk sebagai inti permainan
        StarHawk game = new StarHawk();
        // Membuat panel untuk menu utama
        JPanel mainMenuPanel = new JPanel(new GridBagLayout());
        
        // Membuat panel utama yang berisi menu utama dan panel scoreboard
        JPanel mainPanel = new JPanel(new CardLayout());
        
        // Membuat panel untuk scoreboard
        JPanel scoreboardPanel = new JPanel(new BorderLayout());
        
        //JLabel gameNameLabel = new JLabel("Star Hawk Invasion");
        //Font gameNameFont = new Font("8BIT WONDER", Font.BOLD, 46);
        //gameNameLabel.setFont(gameNameFont);
        //gameNameLabel.setForeground(Color.WHITE);
        // Create a panel for the title label
        JPanel titlePanel = new JPanel(new GridBagLayout());
        titlePanel.setOpaque(false);  // Make the panel transparent
        GridBagConstraints titleGbc = new GridBagConstraints();
        titleGbc.insets.set(0, 50, 0, 50);
        titleGbc.gridwidth = GridBagConstraints.REMAINDER;
        titleGbc.anchor = GridBagConstraints.CENTER;
        titleGbc.fill = GridBagConstraints.NONE;
        
        URL titleImageUrl = MainApplication.class.getResource("./BTitle.png"); 
        ImageIcon titleImageIcon = new ImageIcon(titleImageUrl);
        JLabel gameNameLabel = new JLabel(titleImageIcon);
        
        titlePanel.add(gameNameLabel, titleGbc);
        
        
        /*
        if (titleImageIcon.getIconWidth() > 0) {
            System.out.println("ImageIcon loaded successfully.");
        } else {
            System.out.println("Failed to load ImageIcon.");
        }
        */
        //./Bg_Main.png
        //./BgMainUpgrade.png
        
        ImageIcon ii = new ImageIcon("./BgMain.png");
        Image BgMain; 
        BgMain = ii.getImage(); 
        
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.drawImage(BgMain, 0, 0,1707, 1067, this); // Draw the background image
            }
        };
        
        //System.out.println("Title Image Width: " + titleImageIcon.getIconWidth());
        //System.out.println("Background Image Width: " + ii.getIconWidth());
        // Mendapatkan ukuran layar
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;
        //System.out.println(height+ " | " + width);
        
        frame.setLayout(new BorderLayout()); // Set layout to null
        panel.setBounds(0, 0, 1707, 1067); // Set the bounds of the panel
        frame.add(panel);
        // Mengatur ukuran frame dan posisinya di tengah layar
        frame.setSize(width, height);
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);

        CardLayout cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);
        mainPanel.add(scoreboardPanel, "scoreboard");

        

        
        SCRButton scoreboardButton = new SCRButton(cardLayout, mainPanel, scoreboardPanel, mainMenuPanel, frame, panel, gameNameLabel);
        //panel.add(gameNameLabel, BorderLayout.CENTER);
        // Membuat objek RTOMainMenu dan SCRButton untuk mengatur menu utama dan scoreboard
        RTOMainMenu mainMenu = new RTOMainMenu(frame, mainPanel);
        mainPanel.add(mainMenuPanel, "mainMenu");

        game.setReturnMenu(mainMenu);
        
        // Menambahkan komponen-komponen menu utama ke mainMenuPanel 
        
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        
        // Membuat tombol StartButton, SCRButton, dan ExitButton
        StartButton startButton = new StartButton(frame, game);
        ExitButton exitButton = new ExitButton();
        
        Dimension buttonSize = new Dimension(200, 50);
        
        // Mengatur ukuran tombol
        startButton.setPreferredSize(buttonSize);
        scoreboardButton.setPreferredSize(buttonSize);
        exitButton.setPreferredSize(buttonSize);
        
        GridBagConstraints buttonGbc = new GridBagConstraints();
        buttonGbc.insets.set(0, 5, 0, 5);
        buttonGbc.anchor = GridBagConstraints.CENTER;
        buttonGbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Menambahkan tombol-tombol ke panel tombol
        buttonPanel.add(startButton, buttonGbc);
        buttonPanel.add(scoreboardButton, buttonGbc);
        buttonPanel.add(exitButton, buttonGbc);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.CENTER;
        
        // Membuat JTextArea untuk menampilkan skor
        JTextArea scoreboardTextArea = new JTextArea(10, 40);
        scoreboardTextArea.setEditable(false);

        //startButton.setVisible(true);
        //scoreboardButton.setVisible(true);
        //exitButton.setVisible(true);
        
        // Memuat skor dari file dan menampilkannya
        try {
            String filename = "SCORESAVE.txt";
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                scoreboardTextArea.append(line + "\n");
            }
            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        mainMenuPanel.add(gameNameLabel, titleGbc);
        mainMenuPanel.add(titlePanel);
        mainPanel.add(mainMenuPanel, "mainMenu");
        
        // Adjust the position and size of components to avoid overlap
        int buttonPanelY = frame.getHeight() - 200; // Adjust this value as needed
        
        gameNameLabel.setBounds(0, 0, frame.getWidth(), 100);
        buttonPanel.setBounds(0, buttonPanelY, frame.getWidth(), 200);
        
        JScrollPane scrollPane = new JScrollPane(scoreboardTextArea);
        scoreboardPanel.add(scrollPane, BorderLayout.SOUTH);
        
        //mainPanel.add(scoreboardPanel, "scoreboard");
        // Menambahkan panel menu utama dan panel scoreboard ke mainPanel
        //mainMenuPanel.add(buttonPanel);
        
        
        // Secara awal, menampilkan panel menu utama
        //CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
        
        //panel.add(buttonPanel, BorderLayout.SOUTH);

        System.out.println("\n mainpanel = "+mainPanel);
        cardLayout.show(mainPanel, "mainMenu");
        panel.add(gameNameLabel, BorderLayout.CENTER);
        panel.add(startButton);
        panel.add(scoreboardButton);
        panel.add(exitButton);
        frame.add(panel);
        frame.getContentPane().add(mainPanel);
        frame.revalidate();
        frame.setResizable(false);
        frame.setVisible(true);
        frame.repaint();
        panel.revalidate();
        panel.setVisible(true);
        panel.repaint();
        buttonPanel.revalidate();
        buttonPanel.setVisible(true);
        buttonPanel.repaint();
        //startButton.repaint();
        //scoreboardButton.repaint();
        //exitButton.repaint();
    }
}

// jangan di ss


/* 
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Random;

public class MainApplication {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Star-Hawk Invasion"); // Membuat instance JFrame dengan judul "Galaga Game"
        GalagaGame game = new GalagaGame(); // Membuat instance GalagaGame
        frame.add(game); // Menambahkan instance GalagaGame ke dalam frame
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Mengatur aksi penutupan frame
        //frame.getContentPane().setPreferredSize(new Dimension(700, 1035)); // Mengatur dimensi konten frame
        //frame.pack(); // Paksa frame untuk mengikuti ukuran kontennya

        // Membuat lokasi frame berada di tengah-tengah layar
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;
        frame.setSize(width, height);
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y); // Menetapkan lokasi GU I

        frame.setResizable(false); // Matikan tombol fullscreen
        frame.setVisible(true); // Menampilkan frame
        
        game.initializePlayerShip(frame.getWidth(), frame.getHeight()); // Memanggil metode untuk menginisialisasi kapal pemain
        Random random = new Random(); // Initialize a valid Random object
        game.initializeRandomMovingEnemy(frame.getWidth(), frame.getHeight(), random);
        ArrayList<Enemy> enemies = game.getEnemies();
        game.initializeRespawn(enemies, random, frame.getWidth());

    }
}
*/

// 700 1035 (16:10)
// 700 925 (16:9)

/* 
1. Pengantar Java OOP | Y
2. Kerangka Program OOP | Y                          
3. Dasar Pemrograman Java | Y
4. Struktur Kontrol | Y
5. Array | Y
6. Method | Y
7. Encapsulation | Y
8. Inheritance | Y
9. Polimorfisme | Y (blm tapi nanti di akhir, kayaknya)
10. Static & Final Variable | Y (Kayaknya)
11. Exception Handling | Y
12. Abstract Class & Interface | Y
13. Java Collection | Y
14. GUI | Y
*/

// 1669 lines