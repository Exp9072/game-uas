import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Random;

public class MainApplication {
    public static void main(String[] args) {
        // Membuat instance JFrame
        JFrame frame = new JFrame("Star-Hawk Invasion");

        // Membuat panel utama dengan pengaturan tata letak GridBagLayout
        JPanel mainMenu = new JPanel(new GridBagLayout());

        // Membuat panel untuk tombol-tombol dengan pengaturan tata letak GridBagLayout
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());

        // Menambahkan label untuk nama permainan
        JLabel gameNameLabel = new JLabel("Star-Hawk Invasion");
        
        // Mengatur font label nama permainan
        Font gameNameFont = new Font("Arial", Font.BOLD, 24);
        gameNameLabel.setFont(gameNameFont);

        // Membuat tombol "Start" dengan menggunakan kelas StartButton
        StartButton startButton = new StartButton(frame); // Panggil StartButton.java

        // Membuat tombol "Scoreboard" dengan menggunakan kelas SCRButton
        SCRButton scoreboardButton = new SCRButton(); // Panggil SCRButton.java

        // Mengatur ukuran tombol
        Dimension buttonSize = new Dimension(200, 50);
        startButton.setPreferredSize(buttonSize);
        scoreboardButton.setPreferredSize(buttonSize);

        // Mengubah ukuran font dan menjadikan huruf menjadi huruf besar pada tombol
        Font buttonFont = new Font("Arial", Font.PLAIN, 20);
        startButton.setFont(buttonFont);
        startButton.setText(startButton.getText().toUpperCase());
        scoreboardButton.setFont(buttonFont);
        scoreboardButton.setText(scoreboardButton.getText().toUpperCase());

        // Mengatur batasan dan tata letak komponen menggunakan GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets.set(5, 5, 5, 5); // Menambahkan jarak antara komponen
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Menambahkan label nama permainan ke dalam panel tombol
        buttonPanel.add(gameNameLabel, gbc);

        // Menambahkan tombol "Start" dan "Scoreboard" ke dalam panel tombol
        buttonPanel.add(startButton, gbc);
        buttonPanel.add(scoreboardButton, gbc);

        // Mengatur panel tombol agar berada di tengah secara vertikal dalam panel utama
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.CENTER;
        gbc.weighty = 1.0;

        // Menambahkan panel tombol ke dalam panel utama
        mainMenu.add(buttonPanel, gbc);

        // Menambahkan panel utama ke dalam frame
        frame.add(mainMenu);

        // Mengatur operasi penutupan frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Mengatur dimensi frame sesuai dengan layar
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;
        frame.setSize(width, height);

        // Mengatur posisi frame agar berada di tengah layar
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);

        // Menonaktifkan kemampuan merubah ukuran frame
        frame.setResizable(false);

        // Menampilkan frame
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
9. Polimorfisme | Y
10. Static & Final Variable | Y (Kayaknya)
11. Exception Handling | Y
12. Abstract Class & Interface | Y
13. Java Collection | Y
14. GUI | Y
*/

// 1144 lines