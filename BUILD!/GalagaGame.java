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
    private ArrayList<Laser> enemyLasers;
    private Collision collisionDetector; // Create an instance of CollisionDetector
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
        random = new Random();
        enemies.add(new RandomMovingEnemy(200, 200, 1, 0, 800, 1000, random)); // Contoh musuh yang bergerak acak
        enemyLasers = new ArrayList<>();

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
        collisionDetector = new Collision(); // Initialize CollisionDetector
        // Inisialisasi objek Random untuk digunakan dalam permainan

    }

   public void initializePlayerShip(int screenWidth) {
    playerShip = new PlayerShip(380, 500, screenWidth); // Menginisialisasi kapal pemain dengan posisi awal dan lebar layar
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        playerShip.update(); // Memperbarui kapal pemain
        moveRandomMovingEnemies(); // Memindahkan musuh yang bergerak acak
        updateLasers();
        // Update the positions of player ship lasers
        for (Laser laser : lasers) {
            laser.move(-1); // Update player ship laser position
        }

        // Update the positions of enemy lasers
        for (Enemy enemy : enemies) {
            if (enemy instanceof StaticEnemy) {
                ArrayList<Laser> enemyLasers = ((StaticEnemy) enemy).getEnemyLasers();
                for (Laser laser : enemyLasers) {
                    laser.move(1); // Update enemy laser position
                }
            }
        }
        Collision.checkCollisions(lasers, enemies, playerShip); // Initialize the Collision class
        if (playerShip.getHealth() <= 0) {
            playerShip.destroy();  // Destroy the player ship
            // You may also want to handle game over logic here
        }
        repaint(); // Melakukan penggambaran ulang tampilan game
    }

    private void updateLasers() {
        ArrayList<Laser> lasersToRemove = new ArrayList<>();
        for (Laser laser : lasers) {
            laser.move(-1); // Update player ship laser position
            if (laser.getY() < 0) {
                lasersToRemove.add(laser); // Mark lasers that have gone off the screen
            }
        }
        // Update enemy lasers
        for (Enemy enemy : enemies) {
            if (enemy instanceof StaticEnemy) {
                ArrayList<Laser> enemyLasers = ((StaticEnemy) enemy).getEnemyLasers();
                ArrayList<Laser> enemyLasersToRemove = new ArrayList<>();
                
                for (Laser enemyLaser : enemyLasers) {
                    enemyLaser.move(1); // Update enemy laser position
                    if (enemyLaser.getY() > getHeight()) {
                        enemyLasersToRemove.add(enemyLaser); // Mark enemy lasers that have gone off the screen
                    }
                }
                enemyLasers.removeAll(enemyLasersToRemove); // Remove enemy lasers that have gone off the screen
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

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Health: " + playerShip.getHealth(), 10, 30);

        for (Enemy enemy : enemies) {
            enemy.draw(g); // Menggambar musuh
            drawLocationIndicator(g, enemy.getX(), 570);
        }
        
        for (Enemy enemy : enemies) {
            enemy.draw(g); // Draw enemies
            if (enemy instanceof StaticEnemy) {
                drawLocationIndicator(g, enemy.getX(), 570);
                ArrayList<Laser> enemyLasers = ((StaticEnemy) enemy).getEnemyLasers(); // Get the enemy lasers
                for (Laser laser : ((StaticEnemy) enemy).getEnemyLasers()) {
                    laser.draw(g);
                }
            }
        }

        if (playerShip.getHealth() > 0) {
            playerShip.draw(g); // Draw the player ship if health is greater than 0
        } else {
            playerShip.destroy(); // Destroy the player ship when health reaches 0
        }

        for (Laser laser : lasers) {
            laser.draw(g); // Menggambar laser
        }
    }
        

    private void drawLocationIndicator(Graphics g, int x, int y) {
        g.setColor(Color.black);
        g.drawString("Enemy X-Position: " + x, x, y); // Menggambar indikator posisi musuh bergerak acak
    }
}