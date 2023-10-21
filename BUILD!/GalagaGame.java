import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class GalagaGame extends JPanel implements ActionListener {
    private Timer timer; // Objek Timer untuk mengatur pembaruan game secara berkala
    private PlayerShip playerShip; // Objek kapal pemain
    private ArrayList<Enemy> enemies; // Gunakan daftar musuh generik
    private ArrayList<Laser> lasers; // Daftar proyektil laser yang ditembakkan oleh pemain
    private Random random; // Objek Random untuk menghasilkan nilai acak

    public GalagaGame() {
         // Membuat objek Timer yang akan mengatur pembaruan game setiap 10 milidetik
        timer = new Timer(10, this);
        timer.start();

        // Membuat objek PlayerShip dan menentukan posisinya awal
        playerShip = new PlayerShip(380, 500, getWidth());
        lasers = new ArrayList<>();

        // Membuat daftar objek musuh (enemies) dan menambahkan dua kapal musuh ke dalamnya
        enemies = new ArrayList<>();
        enemies.add(new StaticEnemy(100, 100)); // Contoh musuh statis
        enemies.add(new StaticEnemy(250, 100)); // Contoh musuh statis
        enemies.add(new RandomMovingEnemy(200, 200, 1, 0, 800, new Random())); // Contoh musuh yang bergerak acak

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                playerShip.handleInput(e.getKeyCode());
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    lasers.add(new Laser(playerShip.getX() + playerShip.getWidth() / 2, playerShip.getY()));
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                playerShip.handleKeyRelease(e.getKeyCode()); // Menangani pelepasan tombol kunci pemain
            }
        });
        setFocusable(true); // Mengatur fokus panel game
        requestFocusInWindow(); // Meminta fokus ke jendela game
        random = new Random(); // Inisialisasi objek Random untuk digunakan dalam permainan

    }

   public void initializePlayerShip(int screenWidth) {
    playerShip = new PlayerShip(380, 500, screenWidth); // Menginisialisasi kapal pemain dengan posisi awal dan lebar layar
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        playerShip.update(); // Memperbarui kapal pemain
        moveRandomMovingEnemies(); // Memindahkan musuh yang bergerak acak
        updateLasers(); // Memperbarui posisi laser dan menghapus laser yang telah melewati layar
        repaint(); // Melakukan penggambaran ulang tampilan game
    }

    private void updateLasers() {
        ArrayList<Laser> lasersToRemove = new ArrayList<>();
        
        for (Laser laser : lasers) {
            laser.move(); // Memindahkan laser ke atas
            if (laser.getY() < 0) {
                lasersToRemove.add(laser); // Menandai laser yang telah keluar dari layar
            }
        }
        
        lasers.removeAll(lasersToRemove); // Menghapus laser yang telah keluar dari layar
    }

    private void moveRandomMovingEnemies() {
        for (Enemy enemy : enemies) {
            if (enemy instanceof RandomMovingEnemy) {
                ((RandomMovingEnemy) enemy).moveRandomly(random); // Memindahkan musuh yang bergerak acak
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        playerShip.draw(g); // Menggambar kapal pemain

        for (Enemy enemy : enemies) {
            enemy.draw(g); // Menggambar musuh
        }

        for (Laser laser : lasers) {
            laser.draw(g); // Menggambar laser
        }

        for (Enemy enemy : enemies) {
            if (enemy instanceof RandomMovingEnemy) {
                drawLocationIndicator(g, enemy.getX(), 570); // Menggambar indikator posisi musuh bergerak acak
            }
        }
    }

    private void drawLocationIndicator(Graphics g, int x, int y) {
        g.setColor(Color.black);
        g.drawString("Enemy X-Position: " + x, x, y); // Menggambar indikator posisi musuh bergerak acak
    }
}