import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;

public class PlayerShip {
    private int x, y; // Koordinat x dan y
    private int width, height; // Lebar dan tinggi kapal pemain
    private int speed; // Kecepatan pemain
    private int screenWidth; // Lebar layar
    private int health; // Nyawa Pemain
    private boolean destroyed; // Status apakah kapal pemain telah hancur
    private boolean movingLeft; // Pemain bergerak ke kiri
    private boolean movingRight; // Pemain bergerak ke kanan
    private boolean movingUp;
    private boolean movingDown;
    private int screenHeight;
    private int score;
    private Image playerImage;

    // Konstruktor kelas PlayerShip
    public PlayerShip(int x, int y, int screenWidth, int screenHeight) {
        // Inisialisasi posisi awal kapal pemain di tengah bagian putih layar permainan
        this.x = screenWidth / 3 + (screenWidth / 3 - width) / 2; // Set it to the middle of the white section
        
        // Inisialisasi posisi vertikal kapal pemain, diatur agar muncul 200 piksel di atas bagian bawah layar
        this.y = screenHeight - 200 - height; // Spawn 200 pixels above the bottom

        // Inisialisasi lebar dan tinggi kapal pemain
        this.width = 32; // Setel lebar kapal pemain
        this.height = 32; // Setel tinggi kapal pemain

        // Inisialisasi kecepatan, nyawa, lebar, dan tinggi layar
        this.speed = 3; // Setel kecepatan pemain
        this.health = 3; // Setel nyawa pemain
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        // Inisialisasi status pergerakan kapal pemain
        this.movingDown = false;
        this.movingUp = false;
        this.movingLeft = false; // Awalnya tidak bergerak ke kiri
        this.movingRight = false; // Awalnya tidak bergerak ke kanan 

        // Inisialisasi status kehancuran kapal pemain
        this.destroyed = false; // Awalnya tidak mati
        
        try {
            // Membaca gambar kapal pemain dari lokasi file tertentu
            // No thruster
            //ImageIcon ii = new ImageIcon("./export202311140837368022.png");
            // With thruster
            ImageIcon ii = new ImageIcon("./APlayerWithBoost.png");
            
            // Mengambil objek Image dari ImageIcon
            playerImage = ii.getImage();
            
            // Print dimensi gambar untuk keperluan debug
            //System.out.println("Image dimensions: " + playerImage.getWidth(null) + " x " + playerImage.getHeight(null));
        } catch (Exception e) {
            // Menangani exception yang mungkin terjadi saat membaca gambar
            e.printStackTrace();
        }
    }

    // Metode untuk mengurangi kesehatan pemain
    public void decreaseHealth(int amount) {
        health -= amount;
        if (health <= 0) {
            destroyed = true;
        }
    }

    // Metode untuk memindahkan pemain ke kiri
    public void moveLeft() {
        if (x - speed >= screenWidth / 3 -8) {
            x -= speed; // Pindahkan pemain ke kiri
            //System.out.println("Kiri"); // Debug: Cetak pesan "Kiri"
        }
    }

    // Metode untuk memindahkan pemain ke kanan
    public void moveRight() {
        if (x + speed + width <= 2 * screenWidth/3 - 8) {
            x += speed; // Pindahkan pemain ke kanan
            //System.out.println("Kanan"); // Debug: Cetak pesan "Kanan"
        }
    }

    public void moveUp() {
        if (y - speed >= 0) {
            y -= speed; // Pindahkan pemain ke atas
            //System.out.println("Atas"); // Debug: Cetak pesan "Atas"
        }
    }

    public void moveDown() {
        if (y + speed + height <= screenHeight - 8) {
            y += speed; // Pindahkan pemain ke bawah
            //System.out.println("Bawah"); // Debug: Cetak pesan "Bawah"
        }
    }

    // Metode untuk menangani input pemain
    public void handleInput(int keyCode) {
        if (keyCode == KeyEvent.VK_LEFT) {
            movingLeft = true; // Tangani input pemain ke kiri
            movingRight = false; // Setel pemain tidak bergerak ke kanan
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            movingRight = true; // Tangani input pemain ke kanan
            movingLeft = false; // Setel pemain tidak bergerak ke kiri
        } else if (keyCode == KeyEvent.VK_UP) {
            movingUp = true; // Tangani input pemain ke atas
            movingDown = false; // Setel pemain tidak bergerak ke bawah
        } else if (keyCode == KeyEvent.VK_DOWN) {
            movingDown = true; // Tangani input pemain ke bawah
            movingUp = false; // Setel pemain tidak bergerak ke atas
            //System.out.println("Down");
        }
    }

    // Metode untuk menangani pemain melepaskan tombol keyboard
    public void handleKeyRelease(int keyCode) {
        if (keyCode == KeyEvent.VK_LEFT) {
            movingLeft = false; // Tangani pemain melepaskan tombol kiri
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            movingRight = false; // Tangani pemain melepaskan tombol kanan
        } else if (keyCode == KeyEvent.VK_UP) {
            movingUp = false;
        } else if (keyCode == KeyEvent.VK_DOWN) {
            movingDown = false;
        }
    }

    // Metode untuk memperbarui posisi pemain
    public void update() {
        if (movingLeft) {
            moveLeft(); // Perbarui pergerakan pemain ke kiri
        } else if (movingRight) {
            moveRight(); // Perbarui pergerakan pemain ke kanan
        } else if (movingUp) {
            moveUp(); // Perbarui pergerakan pemain ke atas
        } else if (movingDown) {
            moveDown(); // Perbarui pergerakan pemain ke bawah
        }
    }

    
    // Metode untuk menggambar kapal pemain
    public void draw(Graphics g) {
        // Menampilkan pesan di konsol untuk memastikan metode ini dipanggil dengan benar
        //System.out.println("Drawing player ship at: " + x + ", " + y); 

        // Membuat objek Graphics2D untuk memberikan fungsionalitas grafis tambahan
        Graphics2D g2d = (Graphics2D) g;

        // place holder
        //g.setColor(Color.blue); // Atur warna kapal pemain
        //g.fillRect(x, y, width, height); // Gambar kapal pemain

        // Menggunakan metode drawImage untuk menggambar gambar kapal pemain ke layar
        g2d.drawImage(playerImage, x, y, width , height , null);
    }


    // Metode getter untuk mendapatkan nilai x pemain
    public int getX() {
        return x;
    }

    // Metode getter untuk mendapatkan nilai y pemain
    public int getY() {
        return y;
    }

    // Metode getter untuk mendapatkan lebar pemain
    public int getWidth() {
        return width;
    }

    // Metode getter untuk mendapatkan tinggi pemain
    public int getHeight() {
        return height;
    }

    // Metode getter untuk mendapatkan kesehatan pemain
    public int getHealth() {
        return health;
    }

    // Metode untuk memeriksa apakah kapal pemain telah hancur
    public boolean isDestroyed() {
        return destroyed;
    }

    // Metode untuk menandai pemain sebagai hancur dan mengatur ulang kesehatan pemain
    public void destroy() {
        destroyed = true;
        health = 0;  // Reset health to 0
        
        // Set the player ship's position off-screen to hide it
        x = -getWidth();
        y = -getHeight();
    }
    
    public int getScore() {
        return score;
    }

    public void increaseScore(int points) {
        score += points;
    }
}

// jangan di ss
