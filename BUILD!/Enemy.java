import java.awt.*;

// Import kelas-kelas yang diperlukan dari paket java.awt
public abstract class Enemy {
    // Deklarasi variabel instance yang akan digunakan untuk mengelola posisi dan ukuran musuh
    protected int x, y;
    protected int width, height;
    protected int health;

    // Konstruktor kelas Enemy
    public Enemy(int x, int y, int width, int height) {
        // Menginisialisasi variabel x dan y dengan nilai yang diberikan sebagai parameter
        this.x = x;
        this.y = y;
        // Menginisialisasi lebar dengan nilai 40
        this.width = 40;
        // Menginisialisasi tinggi dengan nilai 40
        this.height = 40;
        this.health = 1;
    }

    // Metode getter untuk mendapatkan nilai x
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean collidesWith(PlayerShip playerShip) {
        // Check if this enemy collides with the player's ship
        return x < playerShip.getX() + playerShip.getWidth() &&
               x + width > playerShip.getX() &&
               y < playerShip.getY() + playerShip.getHeight() &&
               y + height > playerShip.getY();
    }

    // Metode abstrak yang akan diimplementasikan oleh subclass
    public abstract void move();

    // Metode untuk menggambar musuh
    public void draw(Graphics g) {
        // Mengatur warna gambar menjadi merah
        g.setColor(Color.red);
        // Menggambar musuh sebagai persegi dengan posisi dan ukuran yang sesuai
        g.fillRect(x, y, width, height);
    }
}
