import java.awt.*;
import java.awt.event.KeyEvent;

public class PlayerShip {
    private int x, y; // Koordinat x dan y
    private int width, height; // Lebar dan tinggi kapal pemain
    private int speed; // Kecepatan pemain
    private int screenWidth; // Lebar layar
    private int health; // Nyawa Pemain
    private boolean destroyed; // Status apakah kapal pemain telah hancur
    private boolean movingLeft; // Pemain bergerak ke kiri
    private boolean movingRight; // Pemain bergerak ke kanan

    // Konstruktor kelas PlayerShip
    public PlayerShip(int x, int y, int screenWidth) {
        this.x = x;
        this.y = y;
        this.width = 40; // Setel lebar kapal pemain
        this.height = 40; // Setel tinggi kapal pemain
        this.speed = 3; // Setel kecepatan pemain
        this.health = 3; // Setel nyawa pemain
        this.screenWidth = screenWidth;
        this.destroyed = false; // Awalnya tidak mati
        this.movingLeft = false; // Awalnya tidak bergerak ke kiri
        this.movingRight = false; // Awalnya tidak bergerak ke kanan
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
        if (x - speed >= 0) {
            x -= speed; // Pindahkan pemain ke kiri
            System.out.println("Kiri"); // Debug: Cetak pesan "Kiri"
        }
    }

    // Metode untuk memindahkan pemain ke kanan
    public void moveRight() {
        if (x + speed + width <= screenWidth - 8) {
            x += speed; // Pindahkan pemain ke kanan
            System.out.println("Kanan"); // Debug: Cetak pesan "Kanan"
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
        }
    }

    // Metode untuk menangani pemain melepaskan tombol keyboard
    public void handleKeyRelease(int keyCode) {
        if (keyCode == KeyEvent.VK_LEFT) {
            movingLeft = false; // Tangani pemain melepaskan tombol kiri
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            movingRight = false; // Tangani pemain melepaskan tombol kanan
        }
    }

    // Metode untuk memperbarui posisi pemain
    public void update() {
        if (movingLeft) {
            moveLeft(); // Perbarui pergerakan pemain ke kiri
        } else if (movingRight) {
            moveRight(); // Perbarui pergerakan pemain ke kanan
        }
    }

    // Metode untuk menggambar kapal pemain
    public void draw(Graphics g) {
        g.setColor(Color.blue); // Atur warna kapal pemain
        g.fillRect(x, y, width, height); // Gambar kapal pemain
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
}
