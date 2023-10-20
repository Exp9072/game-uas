import java.awt.*;

public class PlayerShip {
    private int x, y; // Posisi saat ini dari kapal pemain pada layar.
    private int width, height; // Lebar dan tinggi kapal pemain.
    private int speed; // Kecepatan pergerakan kapal pemain.
    private int lives; // Jumlah nyawa pemain.

    public PlayerShip(int x, int y) {
        this.x = x; // Menginisialisasi posisi X kapal pemain.
        this.y = y; // Menginisialisasi posisi Y kapal pemain.
        this.width = 40; // Mengatur lebar kapal sesuai yang diinginkan.
        this.height = 40; // Mengatur tinggi kapal sesuai yang diinginkan.
        this.speed = 5; // Mengatur kecepatan pergerakan kapal pemain.
        this.lives = 3; // Mengatur jumlah nyawa awal pemain.
    }

    public int getX() {
        return x; // Mengambil koordinat X kapal pemain.
    }

    public int getY() {
        return y; // Mengambil koordinat Y kapal pemain.
    }

    public int getWidth() {
        return width; // Mengambil lebar kapal pemain.
    }

    public void moveLeft() {
        x -= speed; // Memindahkan kapal pemain ke kiri.
    }

    public void moveRight() {
        x += speed; // Memindahkan kapal pemain ke kanan.
    }

    public void draw(Graphics g) {
        // Menggambar kapal pemain pada layar menggunakan konteks grafik (g).
        g.setColor(Color.blue); // Mengatur warna kapal pemain menjadi biru.
        g.fillRect(x, y, width, height); // Menggambar persegi panjang sebagai kapal pemain.
    }
}
