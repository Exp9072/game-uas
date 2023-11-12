import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.CardLayout;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MainApplication {
    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "true");
        // Membuat objek JFrame untuk menampung permainan
        JFrame frame = new JFrame("Star-Hawk Invasion");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Membuat objek StarHawk sebagai inti permainan
        StarHawk game = new StarHawk();

        // Membuat panel utama yang berisi menu utama dan panel scoreboard
        JPanel mainPanel = new JPanel(new CardLayout());

        // Membuat panel untuk menu utama
        JPanel mainMenuPanel = new JPanel(new GridBagLayout());

        // Membuat panel untuk scoreboard
        JPanel scoreboardPanel = new JPanel(new BorderLayout());

        // Membuat objek RTOMainMenu dan SCRButton untuk mengatur menu utama dan scoreboard
        RTOMainMenu mainMenu = new RTOMainMenu(frame, mainPanel);
        SCRButton scoreboardButton = new SCRButton(mainPanel, scoreboardPanel, mainMenuPanel);
        game.setReturnMenu(mainMenu);

        // Menambahkan komponen-komponen menu utama ke mainMenuPanel
        JLabel gameNameLabel = new JLabel("Star-Hawk Invasion");
        Font gameNameFont = new Font("Arial", Font.BOLD, 96);
        gameNameLabel.setFont(gameNameFont);

        GridBagConstraints titleGbc = new GridBagConstraints();
        titleGbc.insets.set(0, 5, 0, 5);
        titleGbc.gridwidth = GridBagConstraints.REMAINDER;
        titleGbc.anchor = GridBagConstraints.CENTER;
        titleGbc.fill = GridBagConstraints.NONE;

        mainMenuPanel.add(gameNameLabel, titleGbc);

        JPanel emptyPanel = new JPanel();
        GridBagConstraints emptyGbc = new GridBagConstraints();
        emptyGbc.insets.set(0, 5, 0, 5);
        emptyGbc.fill = GridBagConstraints.BOTH;

        mainMenuPanel.add(emptyPanel, emptyGbc);

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
        buttonGbc.insets.set(0, 5, 150, 5);
        buttonGbc.anchor = GridBagConstraints.CENTER;
        buttonGbc.fill = GridBagConstraints.HORIZONTAL;

        // Menambahkan tombol-tombol ke panel tombol
        buttonPanel.add(startButton, buttonGbc);
        buttonPanel.add(scoreboardButton, buttonGbc);
        buttonPanel.add(exitButton, buttonGbc);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.CENTER;

        mainMenuPanel.add(buttonPanel, gbc);

        // Membuat JTextArea untuk menampilkan skor
        JTextArea scoreboardTextArea = new JTextArea(10, 40);
        scoreboardTextArea.setEditable(false);

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

        JScrollPane scrollPane = new JScrollPane(scoreboardTextArea);
        scoreboardPanel.add(scrollPane, BorderLayout.SOUTH);

        // Menambahkan panel menu utama dan panel scoreboard ke mainPanel
        mainPanel.add(mainMenuPanel, "mainMenu");
        mainPanel.add(scoreboardPanel, "scoreboard");

        // Secara awal, menampilkan panel menu utama
        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
        cardLayout.show(mainPanel, "mainMenu");

        frame.add(mainPanel);

        // Kode lainnya...

        // Mendapatkan ukuran layar
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        // Mengatur ukuran frame dan posisinya di tengah layar
        frame.setSize(width, height);
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);

        frame.setResizable(false);
        frame.setVisible(true);
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