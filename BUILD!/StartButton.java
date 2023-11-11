import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class StartButton extends JButton {
    // Deklarasi variabel instance untuk JFrame, GalagaGame, dan Random
    private final JFrame frame;
    private final GalagaGame game;
    private Random random; // Deklarasi objek Random

    // Konstruktor untuk StartButton, menerima JFrame dan GalagaGame sebagai parameter
    public StartButton(JFrame frame, GalagaGame game) {
        super("Mulai"); // Atur teks tombol menjadi "Mulai"
        this.frame = frame; // Inisialisasi variabel instance JFrame
        this.game = game; // Inisialisasi variabel instance GalagaGame

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

        // Tambahkan komponen GalagaGame ke dalam frame
        frame.add(game);

        // Perbarui frame
        frame.revalidate();
        frame.repaint();

        // Tetapkan fokus pada komponen permainan
        game.requestFocusInWindow();
    }
}
