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
    private ArrayList<Laser> enemyLasers; // Daftar proyektil laser yang ditembakkan oleh musuh
    private Collision collisionDetector; // Create an instance of CollisionDetector
    private Random random; // Objek Random untuk menghasilkan nilai acak
    private boolean keybool;
    private Timer delayTimer;
    private boolean delayAktif;
    private Timer spacebarDelayTimer;
    
    public GalagaGame() {
        // Membuat objek Timer yang akan mengatur pembaruan game secara berkala
        timer = new Timer(10, this);
        timer.start();
        keybool = false;
        delayAktif = false;
        spacebarDelayTimer = null;

        // Membuat objek PlayerShip dan menentukan posisinya awal
        playerShip = new PlayerShip(380, 500, getWidth());
        lasers = new ArrayList<>(); // Daftar proyektil laser yang ditembakkan oleh pemain

        // Membuat daftar objek musuh (enemies) dan menambahkan dua kapal musuh ke dalamnya
        enemies = new ArrayList<>();
        enemies.add(new StaticEnemy(100, 100)); // Contoh musuh statis
        enemies.add(new StaticEnemy(250, 100)); // Contoh musuh statis

        // Membuat objek Random untuk menghasilkan nilai acak
        random = new Random();

        enemies.add(new RandomMovingEnemy(200, 200, 1, 0, 800, 1000, random)); // Contoh musuh yang bergerak acak
        enemyLasers = new ArrayList<>(); // Daftar proyektil laser musuh

        // Memulai timer penembakan untuk musuh yang bergerak secara acak
        for (Enemy enemy : enemies) {
            if (enemy instanceof RandomMovingEnemy) {
                ((RandomMovingEnemy) enemy).startShootingTimer();
            }
        }

        // Menangani input dari pemain (keyboard)
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                playerShip.handleInput(e.getKeyCode());
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    if (keybool == false) {
                        keybool = true;
                        lasers.add(new Laser(playerShip.getX() + playerShip.getWidth() / 2, playerShip.getY()));
                        int delay = 3000; // Delay in milliseconds
                        Timer spacebarDelayTimer = new Timer(delay, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                System.out.println("TEST 3");
                                keybool = false;
                            }
                        });
                        spacebarDelayTimer.setRepeats(false); // Ensure the timer fires only once
                        spacebarDelayTimer.start();
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    if (spacebarDelayTimer != null) {
                        spacebarDelayTimer.stop(); // Stop the timer if spacebar is released
                        spacebarDelayTimer = null; // Reset the timer reference
                        keybool = false;
                        playerShip.handleKeyRelease(e.getKeyCode()); // Menangani pelepasan tombol kunci pemain
                    }
                }
            }
        } );

        setFocusable(true); // Mengatur fokus panel game
        requestFocusInWindow(); // Meminta fokus ke jendela game
        collisionDetector = new Collision(); // Memulai CollisionDetector
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
                    laser.move(1); // Update posisi laser StaticEnemy
                }
            } else if (enemy instanceof RandomMovingEnemy) {
                ArrayList<Laser> enemyLasers = ((RandomMovingEnemy) enemy).getEnemyLasers();
                for (Laser laser : enemyLasers) {
                    laser.move(1); // Update posisi laser RandomMovingEnemy
                }
            }
        }
        Collision.checkCollisions(lasers, enemies, playerShip); // MemulaiCollision class

        if (playerShip.getHealth() <= 0) {
            playerShip.destroy();  // Hilangkan player ship
        }
        repaint(); // Melakukan penggambaran ulang tampilan game
    }

    private void updateLasers() {
        ArrayList<Laser> lasersToRemove = new ArrayList<>();
        for (Laser laser : lasers) {
            laser.move(-1); // Update posisi laser
            if (laser.getY() < 0) {
                lasersToRemove.add(laser); // Catat laser yang keluar dari screen
            }
        }

        lasers.removeAll(lasersToRemove); // Hapus laser yang keluar dari screen
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
            enemy.draw(g); // Gambar kapal enemies

            // Periksa apakah musuh tersebut adalah instansi dari RandomMovingEnemy dan gambar proyektil lasernya.
            if (enemy instanceof RandomMovingEnemy) {
                ArrayList<Laser> enemyLasers = ((RandomMovingEnemy) enemy).getEnemyLasers();
                for (Laser laser : enemyLasers) {
                    laser.draw(g);
                }
            }

            if (enemy instanceof StaticEnemy) {
                drawLocationIndicator(g, enemy.getX(), 570);
                ArrayList<Laser> enemyLasers = ((StaticEnemy) enemy).getEnemyLasers(); // Ambil enemy lasers
                for (Laser laser : ((StaticEnemy) enemy).getEnemyLasers()) {
                    laser.draw(g);
                }
            }
        }

        if (playerShip.getHealth() > 0) {
            playerShip.draw(g); // Gambar player ship ijika health > 0
        } else {
            playerShip.destroy(); //Hilangkan player ship saat health = 0
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
