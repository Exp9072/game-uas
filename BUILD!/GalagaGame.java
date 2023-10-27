import javax.swing.Timer;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Color;
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
    private boolean keybool; // Deklarasi variabel keybool sebagai boolean.
    private Timer spacebarDelayTimer; // Deklarasi variabel spacebarDelayTimer sebagai objek Timer.
    private Respawn respawn;
    
    public GalagaGame() {
        // Membuat objek Timer dengan delay 10 milidetik yang akan memicu "this" (biasanya objek saat ini) setiap 10 milidetik.
        timer = new Timer(10, this); //Set timer 10
        timer.start(); // Memulai timer.

        // Inisialisasi keybool sebagai false, ini menandakan bahwa awalnya tidak ada kunci yang sedang ditekan.
        keybool = false;

        // Inisialisasi spacebarDelayTimer ke null. Timer ini akan digunakan untuk mengatur penundaan setelah penggunaan tombol "spacebar."
        spacebarDelayTimer = null;
        // Membuat objek PlayerShip dan menentukan posisinya awal
        int sectionWidth = getWidth() / 3;
        int playerShipX = sectionWidth; // Set player ship's initial position in the left white section
        playerShip = new PlayerShip(playerShipX, 500, sectionWidth, getHeight());
        // Membuat objek PlayerShip dan menentukan posisinya awal
        //playerShip = new PlayerShip(380, 500, getWidth(), getHeight());
        lasers = new ArrayList<>(); // Daftar proyektil laser yang ditembakkan oleh pemain
        
        // Membuat daftar objek musuh (enemies) dan menambahkan dua kapal musuh ke dalamnya
        enemies = new ArrayList<>();
        enemies.add(new StaticEnemy(100, -10)); // Contoh musuh statis
        enemies.add(new StaticEnemy(250, -10)); // Contoh musuh statis

        // Membuat objek Random untuk menghasilkan nilai acak
        random = new Random();

        enemies.add(new RandomMovingEnemy(200, -50, 1, 0, 800, 1500, random)); // Contoh musuh yang bergerak acak
        enemyLasers = new ArrayList<>(); // Daftar proyektil laser musuh

        respawn = new Respawn(enemies, random);

        // Memulai timer penembakan untuk musuh yang bergerak secara acak
        for (Enemy enemy : enemies) {
            if (enemy instanceof RandomMovingEnemy) {
                ((RandomMovingEnemy) enemy).startShootingTimer();
            }
        }

        // Menangani input dari pemain (keyboard)
        // catch handeling null ubah
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                playerShip.handleInput(e.getKeyCode());
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    // Memeriksa apakah keybool saat ini bernilai false
                    if (keybool == false) {
                        keybool = true; // Mengatur keybool ke true untuk mencegah penggunaan spacebar lagi
                        lasers.add(new Laser(playerShip.getX() + playerShip.getWidth() / 2, playerShip.getY()));
                        int delay = 1000; // Penundaan dalam milidetik
                        Timer spacebarDelayTimer = new Timer(delay, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                //System.out.println("TEST 3");
                                keybool = false; // Mengatur keybool kembali ke false setelah timer berakhir
                            }
                        });
                        spacebarDelayTimer.setRepeats(false); // Memastikan timer hanya berjalan sekali
                        spacebarDelayTimer.start(); // Memulai Timer
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    if (spacebarDelayTimer != null) {
                        spacebarDelayTimer.stop(); // Stop the timer if spacebar is released
                        spacebarDelayTimer = null; // Reset the timer reference
                        keybool = false; // Mengatur keybool kembali ke false
                    }
                }
                playerShip.handleKeyRelease(e.getKeyCode()); // Menangani pelepasan tombol kunci pemain
            }
        } );

        setFocusable(true); // Mengatur fokus panel game
        requestFocusInWindow(); // Meminta fokus ke jendela game
        collisionDetector = new Collision(); // Memulai CollisionDetector
    }

    public void initializePlayerShip(int screenWidth, int screenHeight) {
        playerShip = new PlayerShip(380, 500, screenWidth, screenHeight); // Menginisialisasi kapal pemain dengan posisi awal dan lebar layar
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

        
        for (Enemy enemy : enemies) {
            if (enemy.getY() < 0) {
                enemy.move(); // Move the enemy down
            }
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

        ArrayList<Enemy> enemiesToRemove = new ArrayList<>();

        for (Enemy enemy : enemies) {
            if (enemy.getY() > getHeight()) {
                enemiesToRemove.add(enemy); // Mark the enemy for removal when they go off-screen
            }
        }

        enemies.removeAll(enemiesToRemove); // Remove defeated enemies

        if (enemies.isEmpty()) {
            // Respawn enemies when all enemies are destroyed
            respawn.respawnEnemies();
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
                ((RandomMovingEnemy) enemy).moveRandomlyLeftRight(random); // Memindahkan musuh yang bergerak acak
                ((RandomMovingEnemy) enemy).moveRandomlyUpDown(random); // Move up and down
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

    // Define the section widths
    int screenWidth = getWidth();
    int sectionWidth = screenWidth / 3;

    // Draw the left red section
    g.setColor(Color.MAGENTA);
    g.fillRect(0, 0, sectionWidth, getHeight());

    // Draw the middle white section
    g.setColor(Color.WHITE);
    g.fillRect(sectionWidth, 0, sectionWidth, getHeight());

    // Draw the right red section
    g.setColor(Color.MAGENTA);
    g.fillRect(2 * sectionWidth, 0, sectionWidth, getHeight());

    // Now you can draw your game elements in these sections
    playerShip.draw(g);

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Health: " + playerShip.getHealth(), 10, 30);

        for (Enemy enemy : enemies) {
            enemy.draw(g); // Menggambar musuh
            int xPosistion = enemy.getX();
            //drawLocationIndicator(g, enemy.getX(), 570);

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
                //drawLocationIndicator(g, enemy.getX(), 570);
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
/* 
    private void initializeEnemies(int x, int y) {
        enemies = new ArrayList<>();
        enemies.add(new StaticEnemy(x, y));
        enemies.add(new StaticEnemy(x + 150, y));
    }
*/
/* 
    private void drawLocationIndicator(Graphics g, int x, int y) {
        g.setColor(Color.black);
        g.drawString("Enemy X-Position: " + x, x, y); // Menggambar indikator posisi musuh bergerak acak
    }
*/
}