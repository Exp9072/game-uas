import java.awt.Graphics;
import javax.swing.Timer;
import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;

public abstract class Enemy {
    // Deklarasi variabel instance yang akan digunakan untuk mengelola posisi dan ukuran musuh
    protected int x, y; // Koordinat x dan y musuh
    protected int width, height; // Lebar dan tinggi musuh
    protected int health; // Kesehatan musuh
    long currentTime = System.currentTimeMillis(); // Waktu saat ini
    protected Timer shootingTimer; // Timer untuk menembakkan laser
    protected int shootingInterval; // Interval penembakan
    protected ArrayList<Laser> enemyLasers; // Daftar laser musuh
    private Image SEImage;

    // Konstruktor kelas Enemy
    public Enemy(int x, int y, int width, int height) {
        // Menginisialisasi variabel x dan y dengan nilai yang diberikan sebagai parameter
        this.x = x;
        this.y = y;
        // Menginisialisasi lebar dengan nilai 40
        this.width = 40;
        // Menginisialisasi tinggi dengan nilai 40
        this.height = 40;
        this.health = 1; // Menginisialisasi kesehatan musuh dengan 1
        this.enemyLasers = new ArrayList<>(); // Inisialisasi daftar laser musuh
        this.shootingInterval = 3000; // Mengatur interval penembakan
        
        ImageIcon ii = new ImageIcon(Enemy.class.getClassLoader().getResource("AEnemy_Static.gif"));
        SEImage = ii.getImage();
    }

    // Metode getter untuk mendapatkan nilai x
    public int getX() {
        return x;
    }

    // Metode getter untuk mendapatkan nilai y
    public int getY() {
        return y;
    }

    // Metode getter untuk mendapatkan lebar musuh
    public int getWidth() {
        return width;
    }

    // Metode getter untuk mendapatkan tinggi musuh
    public int getHeight() {
        return height;
    }

    // Metode abstrak yang akan diimplementasikan oleh subclass untuk menentukan perilaku penembakan
    public abstract void shoot();

    // Metode yang digunakan untuk memeriksa apakah musuh bertabrakan dengan kapal pemain
/* 
    public boolean collidesWith(PlayerShip playerShip) {
        // Check if this enemy collides with the player's ship
        return x < playerShip.getX() + playerShip.getWidth() &&
               x + width > playerShip.getX() &&
               y < playerShip.getY() + playerShip.getHeight() &&
               y + height > playerShip.getY();
    }
*/
    // Metode abstrak yang akan diimplementasikan oleh subclass untuk menentukan perilaku pergerakan musuh
    public abstract void move();

    // Metode untuk menggambar musuh
    public void draw(Graphics g) {
        // Mengatur warna gambar menjadi merah
        // Menggambar musuh sebagai persegi dengan posisi dan ukuran yang sesuai
        //Place holder
        //g.setColor(Color.red);
        //g.fillRect(x, y, width, height);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(SEImage, x, y, width, height, null);
    }
}

// jangan di ss
