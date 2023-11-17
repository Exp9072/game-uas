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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;

public class StarHawk extends JPanel implements ActionListener {
    private Timer timer; // Objek Timer untuk mengatur pembaruan game secara berkala
    private PlayerShip playerShip; // Objek kapal pemain
    private RandomMovingEnemy randomMovingEnemy;
    private ArrayList<Enemy> enemies; // Gunakan daftar musuh generik
    private ArrayList<Laser> lasers; // Daftar proyektil laser yang ditembakkan oleh pemain
    private ArrayList<Laser> enemyLasers; // Daftar proyektil laser yang ditembakkan oleh musuh
    private Collision collisionDetector; // Create an instance of CollisionDetector
    private Random random; // Objek Random untuk menghasilkan nilai acak
    private boolean keybool; // Deklarasi variabel keybool sebagai boolean.
    private Timer spacebarDelayTimer; // Deklarasi variabel spacebarDelayTimer sebagai objek Timer.
    private Respawn respawn;
    private int screenWidth; // Declare screenWidth
    private int screenHeight; // Declare screenHeight
    private int score; // Added variable to store the player's score
    private RTOMainMenu returnMenu; // Add this field
    private boolean isGameOver = false;
    private long lastLoopTime;
    private static final int TARGET_FPS = 240;
    private static final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
    private Image BgImage, BgImageL,BgImageR;
   
    public StarHawk() {
        // Menginisialisasi kapal pemain dengan ukuran layar 
        initializePlayerShip(getWidth(), getHeight());
        // Membuat objek Timer dengan delay 10 milidetik yang akan memicu "this" (biasanya objek saat ini) setiap 10 milidetik.
        timer = new Timer(10, this); //Set timer 10
        timer.start(); // Memulai timer.
        
        this.setReturnMenu(returnMenu);
        
        random = new Random();
        lasers = new ArrayList<>(); // Daftar proyektil laser yang ditembakkan oleh pemain
        
        // Membuat daftar objek musuh (enemies) dan menambahkan dua kapal musuh ke dalamnya
        enemies = new ArrayList<>();
        // Inisialisasi keybool sebagai false, ini menandakan bahwa awalnya tidak ada kunci yang sedang ditekan.
        keybool = false;

        score = 0;

        // Inisialisasi spacebarDelayTimer ke null. Timer ini akan digunakan untuk mengatur penundaan setelah penggunaan tombol "spacebar."
        spacebarDelayTimer = null;
        // Membuat objek PlayerShip dan menentukan posisinya awal
        int sectionWidth = getWidth() / 3;
        int playerShipX = sectionWidth; // Set player ship's initial position in the left white section
        playerShip = new PlayerShip(playerShipX, 500, sectionWidth, getHeight());
        // Membuat objek PlayerShip dan menentukan posisinya awal
        randomMovingEnemy = new RandomMovingEnemy(800, -50, 1, 0, screenWidth, 1500, random); // Contoh musuh yang bergerak acak
        //playerShip = new PlayerShip(380, 500, getWidth(), getHeight());

        ImageIcon ii = new ImageIcon("./Bg_Game.png");
        ImageIcon uu = new ImageIcon("./Bg_Left.png");
        ImageIcon jj = new ImageIcon("./Bg_Right.png");

        BgImage = ii.getImage();
        BgImageL = uu.getImage();
        BgImageR = jj.getImage(); 

        //enemies.add(new StaticEnemy(enemyX1, sectionEnemy, enemyWidth)); // Create the first enemy
        //enemies.add(new StaticEnemy(enemyX2, sectionEnemy, enemyWidth)); // Create the second enemy


        // Membuat objek Random untuk menghasilkan nilai acak

        enemyLasers = new ArrayList<>(); // Daftar proyektil laser musuh
        //System.out.println("section width = " + sectionWidth);

        respawn = new Respawn(enemies, random, sectionWidth);

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

    public void setReturnMenu(RTOMainMenu returnMenu) {
        this.returnMenu = returnMenu;
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        long currentTime = System.nanoTime();
        double delta = (currentTime - lastLoopTime) / ((double) OPTIMAL_TIME);
        lastLoopTime = currentTime;

        screenWidth = getWidth(); // Set screenWidth in the actionPerformed method
        screenHeight = getHeight(); // Set screenHeight in the actionPerformed method
        //System.out.println("Playership = "+playerShip);
        playerShip.update(); // Memperbarui kapal pemain

        moveRandomMovingEnemies(); // Memindahkan musuh yang bergerak acak
        updateLasers();
        
        // Update the positions of player ship lasers
        for (Laser laser : lasers) {
            laser.move(-1); // Update player ship laser position
        }

        if (isGameOver) {
            resetGame();
            isGameOver = false;  // Reset the game over flag
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
        Collision.checkCollisions(lasers, enemies, playerShip, this); // MemulaiCollision class

        
        if (playerShip.getHealth() <= 0 && !isGameOver) {
            playerShip.destroy();
            updateScore();
            
            isGameOver = true; // Set the game over flag
            timer.stop();
            returnMenu.showGameOverScreen();
            returnMenu.setVisible(true);
        }


        ArrayList<Enemy> enemiesToRemove = new ArrayList<>();

        for (Enemy enemy : enemies) {
            if (enemy.getY() > getHeight()) {
                enemiesToRemove.add(enemy); // Mark the enemy for removal when they go off-screen
            }
        }

        enemies.removeAll(enemiesToRemove); // Remove defeated enemies

        if (enemies.isEmpty() && screenWidth != 0) {
            // Respawn enemies when all enemies are destroyed and screenWidth is not 0
            respawn.respawnEnemies();
        }
        paintComponents(getGraphics());
        repaint(); // Melakukan penggambaran ulang tampilan game
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Define the section widths
        int screenWidth = getWidth();
        //System.out.println(screenWidth);
        int sectionWidth = screenWidth / 3;

        // Draw the left red section
        g.setColor(Color.MAGENTA);
        g.fillRect(0, 0, sectionWidth, getHeight());
        Graphics2D bg1 = (Graphics2D) g;
        bg1.drawImage(BgImageL, 0, 0 , null);

        // Draw the middle white section
        g.setColor(Color.WHITE);
        g.fillRect(sectionWidth, 0, sectionWidth, getHeight());
        Graphics2D bg = (Graphics2D) g;
        bg.drawImage(BgImage, sectionWidth, 0 , null);

        // Draw the right red section
        g.setColor(Color.MAGENTA);
        g.fillRect(2 * sectionWidth, 0, sectionWidth, getHeight());
        Graphics2D bg2 = (Graphics2D) g;
        bg2.drawImage(BgImageR, 2 * sectionWidth,0  , null);
        // Now you can draw your game elements in these sections
        playerShip.draw(g);

        g.setColor(Color.WHITE);
        g.setFont(new Font("8BIT WONDER", Font.PLAIN, 12));
        g.drawString("Health " + playerShip.getHealth(), sectionWidth, 30);
        g.drawString("Score " + playerShip.getScore(), sectionWidth, 60);

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


    // Metode untuk menginisialisasi kapal pemain
    public void initializePlayerShip(int screenWidth, int screenHeight) {
        // Membuat objek PlayerShip baru dengan posisi awal yang ditentukan
        playerShip = new PlayerShip(380, 500, screenWidth, screenHeight);

        // Memeriksa apakah objek PlayerShip berhasil dibuat
        if (playerShip == null) {
            // Jika gagal, membuat objek PlayerShip baru dengan posisi awal yang sama
            playerShip = new PlayerShip(380, 500, screenWidth, screenHeight);
        } 
    }


    public void initializeRandomMovingEnemy(int screenWidth, int screenHeight, Random random) {
        int sectionWidth = screenWidth / 3;
        int enemyX1 = sectionWidth ; // Adjust the values as needed
        
        int sectionEnemy = 100; // Adjust the Y position as needed
    
        //enemies.add(new RandomMovingEnemy(enemyX1, sectionEnemy, 1, sectionWidth, screenWidth, 1500, random));
        
    }

    public void initializeRespawn(ArrayList<Enemy> enemies, Random random, int screenWidth) {
        //System.out.println("Screen WIdth GalagaGame class = " + screenWidth);
        int sectionWidth = screenWidth / 3;
        respawn = new Respawn(enemies, random, sectionWidth);
        respawn.setScreenWidth(screenWidth);
        //respawn.startRespawnTimer(); // Start the respawn timer
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
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
                //((RandomMovingEnemy) enemy).limitHorizontalMovement(screenWidth); // Limit horizontal movement
                //((RandomMovingEnemy) enemy).limitVerticalMovement(screenHeight); // Limit vertical movement
            }
        }
    }

    // Metode untuk mereset permainan
    public void resetGame(){
        // Menghapus semua musuh dan laser dari layar
        enemies.clear();
        lasers.clear();

        // Menginisialisasi kembali kapal pemain di tengah layar
        initializePlayerShip(screenWidth, screenHeight);

        // Menginisialisasi musuh yang bergerak secara acak
        initializeRandomMovingEnemy(screenWidth, screenHeight, random);

        // Menginisialisasi respawn musuh
        initializeRespawn(enemies, random, screenWidth);

        // Memulai timer untuk permainan
        timer.start();

        // Mengatur skor kembali ke 0
        score = 0;
    }

    // Metode untuk meningkatkan skor permainan
    public void increaseScore(int points) {
        // Menambahkan jumlah poin ke skor total
        score += points;
    }


    public void updateScore() {
        String filename = "SCORESAVE.txt";
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.addPoints(playerShip.getScore()); // Add the current score to the total score
        scoreboard.loadScoresFromFile(filename);
        
        // Sort the loaded scores
        scoreboard.sortScores();
        
        int newScore = playerShip.getScore();
        if (scoreboard.scores.size() < 5 || newScore > scoreboard.scores.get(4)) {
            // If the scores list has fewer than 5 scores or the new score is higher
            // than the lowest of the top 5 scores, add the new score and sort again.
            scoreboard.scores.add(newScore);
            scoreboard.sortScores();
        }
        
        scoreboard.saveScoreToFile(filename); // Save the sorted scores back to the file
    }  
}

    
// jangan di ss
    
/* 
    private void initializeEnemies(int x, int y) {
        enemies = new ArrayList<>();
        enemies.add(new StaticEnemy(x, y));
        enemies.add(new StaticEnemy(x + 150, y));
    }
    
    private void drawLocationIndicator(Graphics g, int x, int y) {
        g.setColor(Color.black);
        g.drawString("Enemy X-Position: " + x, x, y); // Menggambar indikator posisi musuh bergerak acak
    }
*/
