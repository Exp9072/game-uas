import java.awt.*;

public class PlayerShip {
    private int x, y; // Posisi saat ini dari kapal pemain
    private int width, height; // Dimensi kapal pemain
    private int speed; // Kecepatan pergerakan
    private int lives; // Jumlah nyawa pemain

    public PlayerShip(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 40; // Atur dimensi yang diinginkan
        this.height = 40; // Atur dimensi yang diinginkan
        this.speed = 5; // Atur kecepatan yang diinginkan
        this.lives = 3; // Atur jumlah nyawa awal
    }

    public void moveLeft() {
        x -= speed;
    }

    public void moveRight() {
        x += speed;
    }

    public void draw(Graphics g) {
        // Menggambar kapal pemain pada layar menggunakan konteks grafik (g)
        g.setColor(Color.blue); // Atur warna kapal
        g.fillRect(x, y, width, height); // Menggambar persegi panjang sebagai kapal
    }
}
