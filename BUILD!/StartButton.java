import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.net.URL;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Dimension;


public class StartButton extends JButton {
    // Deklarasi variabel instance untuk JFrame, GalagaGame, dan Random
    private final JFrame frame;
    private final StarHawk game;
    private Random random; // Deklarasi objek Random

    // Konstruktor untuk StartButton, menerima JFrame dan GalagaGame sebagai parameter
    public StartButton(JFrame frame, StarHawk game) {
        super(""); // Atur teks tombol menjadi "Mulai"
        this.frame = frame; // Inisialisasi variabel instance JFrame
        this.game = game; // Inisialisasi variabel instance GalagaGame

        URL imageUrl = getClass().getResource("./BStartButton.png");
        ImageIcon ii = new ImageIcon(imageUrl);
        Image scaledImage = ii.getImage().getScaledInstance(205, 55, Image.SCALE_SMOOTH);
        setIcon(new ImageIcon(scaledImage));

        setPreferredSize(new Dimension(50, 50));
        setHorizontalTextPosition(JButton.CENTER);
        setVerticalTextPosition(JButton.CENTER);

        // Tambahkan ActionListener ke tombol yang memanggil metode startGame() saat diklik
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
    }

    // Metode untuk memulai permainan
    private void startGame() {
        // Reset status permainan
        game.resetGame();

        // Hapus konten dari frame
        frame.getContentPane().removeAll();
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();

        // Inisialisasi kapal pemain, musuh bergerak acak, dan mekanisme respawn
        game.initializePlayerShip(frame.getWidth(), frame.getHeight());
        random = new Random(); // Inisialisasi objek Random
        game.initializeRandomMovingEnemy(frame.getWidth(), frame.getHeight(), random);
        ArrayList<Enemy> enemies = game.getEnemies();
        game.initializeRespawn(enemies, random, frame.getWidth());

        
        frame.add(game);

        // Perbarui frame
        frame.revalidate();
        frame.repaint();

        // Tetapkan fokus pada komponen permainan
        game.requestFocusInWindow();
    }
}

// jangan di ss
