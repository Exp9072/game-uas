import java.awt.*;

public class Laser {
    private int x, y; // Koordinat x dan y dari laser
    private int width, height; // Lebar dan tinggi laser
    private int speed; // Kecepatan laser

    public Laser(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 5; // Mengatur lebar laser
        this.height = 15; // Mengatur tinggi laser
        this.speed = 5; // Mengatur kecepatan laser
    }

    public void move() {
        y -= speed; // Pindahkan laser ke atas
    }

    public void draw(Graphics g) {
        g.setColor(Color.PINK); // Mengatur warna laser
        g.fillRect(x, y, width, height); // Gambar laser
    }

    public int getY() {
        return y; // Mengembalikan posisi y laser
    }
}
