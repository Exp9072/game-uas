import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Toolkit;

public class MainApplication {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Star-Hawk Invasion");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create an instance of GalagaGame
        GalagaGame game = new GalagaGame();

        JPanel mainPanel = new JPanel(new GridBagLayout());

        JLabel gameNameLabel = new JLabel("Star-Hawk Invasion");
        Font gameNameFont = new Font("Arial", Font.BOLD, 96);
        gameNameLabel.setFont(gameNameFont);

        GridBagConstraints titleGbc = new GridBagConstraints();
        titleGbc.insets.set(0, 5, 0, 5);  // Add space only to the left and right
        titleGbc.gridwidth = GridBagConstraints.REMAINDER;
        titleGbc.anchor = GridBagConstraints.CENTER; // Align to the center of the cell
        titleGbc.fill = GridBagConstraints.NONE; // Don't fill the cell

        mainPanel.add(gameNameLabel, titleGbc);

        JPanel emptyPanel = new JPanel(); // Create an empty panel for spacing
        GridBagConstraints emptyGbc = new GridBagConstraints();
        emptyGbc.insets.set(0, 5, 0, 5); // Add 10 pixels of space at the top and bottom
        emptyGbc.fill = GridBagConstraints.BOTH; // Fill both horizontally and vertically

        mainPanel.add(emptyPanel, emptyGbc);

        JPanel buttonPanel = new JPanel(new GridBagLayout());

        StartButton startButton = new StartButton(frame, game);
        SCRButton scoreboardButton = new SCRButton();
        ExitButton exitButton = new ExitButton();

        Dimension buttonSize = new Dimension(200, 50);

        startButton.setPreferredSize(buttonSize);
        scoreboardButton.setPreferredSize(buttonSize);
        exitButton.setPreferredSize(buttonSize);

        GridBagConstraints buttonGbc = new GridBagConstraints();
        buttonGbc.insets.set(0, 5, 150, 5);  // Add space only at the top
        buttonGbc.anchor = GridBagConstraints.CENTER; // Align to the center of the cell
        buttonGbc.fill = GridBagConstraints.HORIZONTAL;

        buttonPanel.add(startButton, buttonGbc);
        buttonPanel.add(scoreboardButton, buttonGbc);
        buttonPanel.add(exitButton, buttonGbc);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.CENTER;

        mainPanel.add(buttonPanel, gbc);

        frame.add(mainPanel);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;
        frame.setSize(width, height);

        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);

        frame.setResizable(false);
        frame.setVisible(true);
    }
}










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

// 1164 lines