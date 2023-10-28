import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel; // Add JLabel
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Random;

public class MainApplication {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Star-Hawk Invasion");
        JPanel mainMenu = new JPanel(new GridBagLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());

        // Add a JLabel for the game name
        JLabel gameNameLabel = new JLabel("Star-Hawk Invasion");
        Font gameNameFont = new Font("Arial", Font.BOLD, 24); // Set a larger font
        gameNameLabel.setFont(gameNameFont);

        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(mainMenu);
                startGame(frame);
            }
        });

        JButton scoreboardButton = new JButton("Scoreboard");

        // Set the button size
        Dimension buttonSize = new Dimension(200, 50);
        startButton.setPreferredSize(buttonSize);
        scoreboardButton.setPreferredSize(buttonSize);

        // Change the font size and make letters uppercase
        Font buttonFont = new Font("Arial", Font.PLAIN, 20);
        startButton.setFont(buttonFont);
        startButton.setText(startButton.getText().toUpperCase());
        scoreboardButton.setFont(buttonFont);
        scoreboardButton.setText(scoreboardButton.getText().toUpperCase());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets.set(5, 5, 5, 5);  // Add some padding
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add the game name label
        buttonPanel.add(gameNameLabel, gbc);
        buttonPanel.add(startButton, gbc);
        buttonPanel.add(scoreboardButton, gbc);

        // Center the buttonPanel vertically in the middle of the main panel
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.CENTER;
        gbc.weighty = 1.0;

        mainMenu.add(buttonPanel, gbc);

        frame.add(mainMenu);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

    public static void startGame(JFrame frame) {
        frame.getContentPane().removeAll();
        frame.repaint();
        GalagaGame game = new GalagaGame();

        game.initializePlayerShip(frame.getWidth(), frame.getHeight());
        Random random = new Random();
        game.initializeRandomMovingEnemy(frame.getWidth(), frame.getHeight(), random);
        ArrayList<Enemy> enemies = game.getEnemies();
        game.initializeRespawn(enemies, random, frame.getWidth());

        frame.add(game);
        frame.revalidate();
        frame.repaint();
        game.requestFocusInWindow();
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
9. Polimorfisme | Y
! 10. Static & Final Variable | N (Total Yang Nembak)
! 11. Exception Handling | N
! 12. Abstract Class & Interface | N
13. Java Collection | Y
14. GUI | Y
*/

//1060 lines