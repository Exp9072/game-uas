import java.awt.*;

public class Laser {
    private int x, y; // Koordinat posisi tembakan laser di layar.
    private int width, height; // Lebar dan tinggi tembakan laser.
    private int speed; // Kecepatan pergerakan tembakan laser.

    public Laser(int x, int y) {
        this.x = x; // Menginisialisasi koordinat X tembakan laser.
        this.y = y; // Menginisialisasi koordinat Y tembakan laser.
        this.width = 5; // Mengatur lebar tembakan laser.
        this.height = 15; // Mengatur tinggi tembakan laser.
        this.speed = 5; // Mengatur kecepatan pergerakan tembakan laser.
    }

    public void move(int i) {
        y -= speed; // Memindahkan tembakan laser ke atas (berlawanan dengan arah Y positif).
    }

    public void draw(Graphics g) {
        g.setColor(Color.PINK); // Mengatur warna tembakan laser menjadi merah muda (pink).
        g.fillRect(x, y, width, height); // Menggambar tembakan laser di layar.
    }

    public int getY() {
        return y; // Mengambil koordinat Y dari tembakan laser.
    }

    public static void add(Laser laser) {
    }
}
