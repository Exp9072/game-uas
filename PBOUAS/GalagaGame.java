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
    private ArrayList<EnemyShip> enemyShips; // Daftar kapal musuh
    private ArrayList<Laser> lasers; // Daftar proyektil laser yang ditembakkan oleh pemain
    private ArrayList<RandomMovingEnemy> randomMovingEnemies; // Daftar musuh yang bergerak secara acak
    private Random random; // Objek Random untuk menghasilkan nilai acak

    public GalagaGame() {
        // Membuat objek Timer yang akan mengatur pembaruan game setiap 10 milidetik
        timer = new Timer(10, this);
        timer.start();

        // Membuat objek PlayerShip dan menentukan posisinya awal
        playerShip = new PlayerShip(380, 500, getWidth());
        lasers = new ArrayList<>();

        // Membuat daftar objek musuh (enemyShips) dan menambahkan dua kapal musuh ke dalamnya
        enemyShips = new ArrayList<>();
        enemyShips.add(new EnemyShip(100, 100));
        enemyShips.add(new EnemyShip(300, 100));

        // Membuat daftar objek musuh yang bergerak acak (randomMovingEnemies) dan menambahkan satu musuh ke dalamnya
        randomMovingEnemies = new ArrayList<>();
        randomMovingEnemies.add(new RandomMovingEnemy(200, 200, 1, 0, 800, new Random()));

        // Menambahkan pemantik (KeyListener) untuk mengendalikan input pemain
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                playerShip.handleInput(e.getKeyCode());
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    // Membuat laser ketika tombol spasi ditekan
                    lasers.add(new Laser(playerShip.getX() + playerShip.getWidth() / 2, playerShip.getY()));
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // Mengatasi pembebasan tombol yang memengaruhi pengendalian pemain
                playerShip.handleKeyRelease(e.getKeyCode());
            }
        });
        setFocusable(true); // Mengatur panel permainan agar dapat menerima fokus.
        requestFocusInWindow(); // Meminta fokus pada panel permainan agar pemain dapat berinteraksi dengan game tanpa harus mengklik panel terlebih dahulu.
        random = new Random(); // Membuat objek Random yang akan digunakan untuk menghasilkan nilai acak dalam permainan.

    }

    public void initializePlayerShip(int screenWidth) {
    // Inisialisasi kapal pemain dengan posisi awal pada koordinat x 380, koordinat y 500, dan lebar layar yang diberikan.
    playerShip = new PlayerShip(380, 500, screenWidth);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        playerShip.update(); // Memperbarui posisi dan status kapal pemain.
        moveRandomMovingEnemies(); // Memindahkan musuh bergerak acak.
        updateLasers(); // Memperbarui posisi tembakan laser dan menggambar ulang layar.
        repaint(); // Meminta penggambaran ulang layar untuk menampilkan perubahan yang terjadi.
    }

    private void updateLasers() {
    // Membuat ArrayList baru untuk menampung tembakan laser yang akan dihapus.
        ArrayList<Laser> lasersToRemove = new ArrayList<>();
        for (Laser laser : lasers) {
            laser.move(); // Memindahkan tembakan laser.
            if (laser.getY() < 0) {
                lasersToRemove.add(laser); // Tandai tembakan laser yang telah keluar dari layar untuk dihapus nanti.
            }
        }
        lasers.removeAll(lasersToRemove); // Menghapus tembakan laser yang telah keluar dari layar.
    }

    private void moveRandomMovingEnemies() {
        for (RandomMovingEnemy enemy : randomMovingEnemies) {
            enemy.moveRandomly(random); // Memindahkan musuh bergerak acak dengan menghasilkan pergerakan acak menggunakan objek Random.
        }
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Memanggil metode paintComponent dari kelas induk.

        playerShip.draw(g); // Menggambar kapal pemain.

        for (EnemyShip enemy : enemyShips) {
            enemy.draw(g); // Menggambar setiap kapal musuh.
        }

        for (Laser laser : lasers) {
            laser.draw(g); // Menggambar setiap tembakan laser.
        }

        for (RandomMovingEnemy enemy : randomMovingEnemies) {
            enemy.draw(g); // Menggambar musuh bergerak acak.
            drawLocationIndicator(g, enemy.getX(), 570); // Menggambar indikator posisi musuh di bawahnya.
        }
    }

    private void drawLocationIndicator(Graphics g, int x, int y) {
        g.setColor(Color.black); // Mengatur warna teks menjadi hitam.
        g.drawString("Enemy X-Position: " + x, x, y); // Menggambar teks yang menunjukkan posisi X musuh pada posisi tertentu di layar.
    }

    public static void main(String[] args) {
        // Membuat objek JFrame untuk menampung permainan.
        JFrame frame = new JFrame("Galaga Game");
        // Membuat objek GalagaGame yang merupakan panel permainan.
        GalagaGame game = new GalagaGame();
        // Menambahkan panel permainan ke frame.
        frame.add(game);
        // Mengatur ukuran jendela frame menjadi 800x600 piksel.
        frame.setSize(800, 600);
        // Mengatur operasi yang dilakukan saat menutup jendela.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Menjadikan panel content memiliki ukuran preferensi sebesar 850x600 piksel.
        frame.getContentPane().setPreferredSize(new Dimension(850, 600));
        // Menyesuaikan ukuran frame dengan ukuran content.
        frame.pack();
        // Menginisialisasi kapal pemain dengan lebar layar sebagai parameter.
        game.initializePlayerShip(frame.getWidth());
        // Menampilkan frame ke layar.
        frame.setVisible(true);
    }

    static class PlayerShip {
        private int x, y; // Koordinat posisi kapal pemain pada layar.
        private int width, height; // Ukuran lebar dan tinggi kapal pemain.
        private int speed; // Kecepatan pergerakan kapal pemain.
        private int screenWidth; // Lebar layar game.
        private boolean movingLeft; // Status pergerakan ke kiri.
        private boolean movingRight; // Status pergerakan ke kanan.
    
        public PlayerShip(int x, int y, int screenWidth) {
            this.x = x; // Menginisialisasi koordinat X kapal pemain.
            this.y = y; // Menginisialisasi koordinat Y kapal pemain.
            this.width = 40; // Menginisialisasi lebar kapal pemain.
            this.height = 40; // Menginisialisasi tinggi kapal pemain.
            this.speed = 3; // Menginisialisasi kecepatan pergerakan kapal pemain.
            this.screenWidth = screenWidth; // Menginisialisasi lebar layar game.
            this.movingLeft = false; // Awalnya tidak bergerak ke kiri.
            this.movingRight = false; // Awalnya tidak bergerak ke kanan.
        }
    
        public void moveLeft() {
            if (x - speed >= 0) {
                x -= speed; // Memindahkan kapal pemain ke kiri jika memungkinkan.
                System.out.println("Left"); // Mencetak pesan "Left" ke konsol.
            }
        }
    
        public void moveRight() {
            if (x + speed + width <= screenWidth - 10) {
                x += speed; // Memindahkan kapal pemain ke kanan jika memungkinkan.
                System.out.println("Right"); // Mencetak pesan "Right" ke konsol.
            }
        }
    
        public void handleInput(int keyCode) {
            if (keyCode == KeyEvent.VK_LEFT) {
                movingLeft = true; // Mengatur status pergerakan ke kiri.
                movingRight = false; // Mengatur status pergerakan ke kanan menjadi false.
            } else if (keyCode == KeyEvent.VK_RIGHT) {
                movingRight = true; // Mengatur status pergerakan ke kanan.
                movingLeft = false; // Mengatur status pergerakan ke kiri menjadi false.
            }
        }
    
        public void handleKeyRelease(int keyCode) {
            if (keyCode == KeyEvent.VK_LEFT) {
                movingLeft = false; // Mengatur status pergerakan ke kiri menjadi false.
            } else if (keyCode == KeyEvent.VK_RIGHT) {
                movingRight = false; // Mengatur status pergerakan ke kanan menjadi false.
            }
        }
    
        public void update() {
            if (movingLeft) {
                moveLeft(); // Memindahkan ke kiri jika status pergerakan ke kiri aktif.
            } else if (movingRight) {
                moveRight(); // Memindahkan ke kanan jika status pergerakan ke kanan aktif.
            }
        }
    
        public void draw(Graphics g) {
            g.setColor(Color.blue); // Mengatur warna gambar menjadi biru.
            g.fillRect(x, y, width, height); // Menggambar kapal pemain pada layar.
        }
    
        public int getX() {
            return x; // Mengambil koordinat X kapal pemain.
        }
    
        public int getWidth() {
            return width; // Mengambil lebar kapal pemain.
        }
    
        public int getY() {
            return y; // Mengambil koordinat Y kapal pemain.
        }
    }

    static class EnemyShip {
        private int x, y; // Koordinat posisi kapal musuh pada layar.
        private int width, height; // Ukuran lebar dan tinggi kapal musuh.
    
        public EnemyShip(int x, int y) {
            this.x = x; // Menginisialisasi koordinat X kapal musuh.
            this.y = y; // Menginisialisasi koordinat Y kapal musuh.
            this.width = 40; // Menginisialisasi lebar kapal musuh.
            this.height = 40; // Menginisialisasi tinggi kapal musuh.
        }
    
        public void draw(Graphics g) {
            g.setColor(Color.red); // Mengatur warna gambar menjadi merah.
            g.fillRect(x, y, width, height); // Menggambar kapal musuh pada layar.
        }
    }
    
    static class Laser {
        private int x, y; // Koordinat posisi tembakan laser pada layar.
        private int width, height; // Ukuran lebar dan tinggi tembakan laser.
        private int speed; // Kecepatan pergerakan tembakan laser.
    
        public Laser(int x, int y) {
            this.x = x; // Menginisialisasi koordinat X tembakan laser.
            this.y = y; // Menginisialisasi koordinat Y tembakan laser.
            this.width = 5; // Menginisialisasi lebar tembakan laser.
            this.height = 15; // Menginisialisasi tinggi
            this.height = 15; // Menginisialisasi tinggi tembakan laser.
            this.speed = 5; // Menginisialisasi kecepatan pergerakan tembakan laser.
        }
    
        public void move() {
            y -= speed; // Menggerakkan tembakan laser ke atas (berlawanan dengan arah Y positif).
        }
    
        public void draw(Graphics g) {
            g.setColor(Color.pink); // Mengatur warna gambar tembakan laser menjadi merah muda (pink).
            g.fillRect(x, y, width, height); // Menggambar tembakan laser pada layar.
        }
    
        public int getY() {
            return y; // Mengambil koordinat Y dari tembakan laser.
        }
    }
    
    

    static class RandomMovingEnemy {
        private int x, y; // Koordinat posisi musuh pada layar.
        private int width, height; // Ukuran lebar dan tinggi musuh.
        private int speed; // Kecepatan pergerakan musuh.
        private int direction; // Arah pergerakan musuh (-1 untuk kiri, 1 untuk kanan).
        private int minRange; // Batas minimum pergerakan musuh di sebelah kiri layar.
        private int maxRange; // Batas maksimum pergerakan musuh di sebelah kanan layar.
        private int lastDirectionChange; // Menghitung berapa lama sejak perubahan arah terakhir.
        private int minMove; // Jarak minimum pergerakan acak.
        private int maxMove; // Jarak maksimum pergerakan acak.
        private int currentMove; // Jarak pergerakan acak saat ini yang ditentukan secara acak.
        
        public RandomMovingEnemy(int x, int y, int speed, int minRange, int maxRange, Random random) {
            // Konstruktor untuk menginisialisasi atribut-atribut musuh yang bergerak acak.
            this.x = x; // Inisialisasi koordinat X musuh.
            this.y = y; // Inisialisasi koordinat Y musuh.
            this.width = 40; // Inisialisasi lebar musuh.
            this.height = 40; // Inisialisasi tinggi musuh.
            this.speed = speed; // Inisialisasi kecepatan pergerakan musuh.
            this.direction = 1; // Inisialisasi arah pergerakan musuh (default ke kanan).
            this.minRange = minRange; // Inisialisasi batas minimum pergerakan di sebelah kiri layar.
            this.maxRange = maxRange; // Inisialisasi batas maksimum pergerakan di sebelah kanan layar.
            this.lastDirectionChange = 0; // Inisialisasi waktu sejak perubahan arah terakhir.
            this.minMove = 50; // Jarak minimum pergerakan acak.
            this.maxMove = 250; // Jarak maksimum pergerakan acak.
            this.currentMove = getRandomMove(random); // Inisialisasi pergerakan acak saat ini.
        }

        public void moveRandomly(Random random) {
            // Metode untuk menggerakkan musuh secara acak berdasarkan parameter dan nilai acak.
            if (lastDirectionChange >= currentMove) {
                if (random.nextBoolean()) {
                    direction = -direction; // Mengubah arah pergerakan secara acak.
                    lastDirectionChange = 0; // Mereset waktu sejak perubahan arah.
                    currentMove = getRandomMove(random); // Mendapatkan pergerakan acak baru.
                }
            } else {
                lastDirectionChange++; // Menambah waktu sejak perubahan arah.
            }

            if (x <= minRange || x >= maxRange) {
                direction = -direction; // Memantul jika mencapai batas minimum atau maksimum.
                lastDirectionChange = 0; // Mereset waktu sejak perubahan arah.
                currentMove = getRandomMove(random); // Mendapatkan pergerakan acak baru.
            }

            x += speed * direction; // Menggerakkan musuh.
            x = Math.min(maxRange, Math.max(minRange, x)); // Memastikan posisi musuh berada dalam batas yang diizinkan.
        }

        private int getRandomMove(Random random) {
            // Metode untuk mendapatkan nilai pergerakan acak antara minMove dan maxMove.
            return random.nextInt(maxMove - minMove + 1) + minMove;
        }

        public void draw(Graphics g) {
            // Menggambar musuh yang bergerak acak pada layar dengan warna hijau.
            g.setColor(Color.green);
            g.fillRect(x, y, width, height);
        }

        public int getX() {
            // Mengambil koordinat X dari musuh yang bergerak acak.
            return x;
        }
    }
}

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
! 10. Static & Final Variable | N
! 11. Exception Handling | N
! 12. Abstract Class & Interface | N
13. Java Collection | Y
14. GUI | Y
*/