import java.awt.*;
import java.awt.event.KeyEvent;

public class PlayerShip {
    private int x, y; // Koordinat x dan y
    private int width, height; // Lebar dan tinggi kapal pemain
    private int speed; // Kecepatan pemain
    private int screenWidth; // Lebar layar
    private boolean movingLeft; // Pemain bergerak ke kiri
    private boolean movingRight; // Pemain bergerak ke kanan

    public PlayerShip(int x, int y, int screenWidth) {
        this.x = x;
        this.y = y;
        this.width = 40; // Setel lebar kapal pemain
        this.height = 40; // Setel tinggi kapal pemain
        this.speed = 3; // Setel kecepatan pemain
        this.screenWidth = screenWidth;
        this.movingLeft = false; // Awalnya tidak bergerak ke kiri
        this.movingRight = false; // Awalnya tidak bergerak ke kanan
    }

    public void moveLeft() {
        if (x - speed >= 0) {
            x -= speed; // Pindahkan pemain ke kiri
            System.out.println("Kiri"); // Debug: Cetak pesan "Kiri"
        }
    }

    public void moveRight() {
        if (x + speed + width <= screenWidth - 8) {
            x += speed; // Pindahkan pemain ke kanan
            System.out.println("Kanan"); // Debug: Cetak pesan "Kanan"
        }
    }

    public void handleInput(int keyCode) {
        if (keyCode == KeyEvent.VK_LEFT) {
            movingLeft = true; // Tangani input pemain ke kiri
            movingRight = false; // Setel pemain tidak bergerak ke kanan
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            movingRight = true; // Tangani input pemain ke kanan
            movingLeft = false; // Setel pemain tidak bergerak ke kiri
        }
    }

    public void handleKeyRelease(int keyCode) {
        if (keyCode == KeyEvent.VK_LEFT) {
            movingLeft = false; // Tangani pemain melepaskan tombol kiri
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            movingRight = false; // Tangani pemain melepaskan tombol kanan
        }
    }

    public void update() {
        if (movingLeft) {
            moveLeft(); // Perbarui pergerakan pemain ke kiri
        } else if (movingRight) {
            moveRight(); // Perbarui pergerakan pemain ke kanan
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.blue); // Atur warna kapal pemain
        g.fillRect(x, y, width, height); // Gambar kapal pemain
    }

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
}
