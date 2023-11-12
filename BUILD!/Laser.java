import java.awt.Graphics;
import java.awt.Color;

public class Laser {
    private int x, y; // Koordinat x dan y dari laser
    private int width, height; // Lebar dan tinggi laser
    private int speed; // Kecepatan laser
    private boolean collided;

    // Konstruktor kelas Laser
    public Laser(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 5; // Mengatur lebar laser
        this.height = 15; // Mengatur tinggi laser
        this.speed = 5; // Mengatur kecepatan laser
    }

    // Metode untuk memindahkan posisi laser berdasarkan arah yang ditentukan
    public void move(int direction) {
        y += direction * speed; // Pindahkan laser berdasarkan arah yang ditentukan
    }

    // Metode untuk menggambar laser
    public void draw(Graphics g) {
        g.setColor(Color.PINK); // Mengatur warna laser
        g.fillRect(x, y, width, height); // Gambar laser
    }

    // Metode getter untuk mendapatkan nilai y laser
    public int getY() {
        return y; // Mengembalikan posisi y laser
    }

    // Metode getter untuk mendapatkan nilai x laser
    public int getX() {
        return x; // Mengembalikan posisi x laser
    }

    // Metode getter untuk mendapatkan lebar laser
    public int getWidth() {
        return width; // Mengembalikan lebar laser
    }

    // Metode getter untuk mendapatkan tinggi laser
    public int getHeight() {
        return height; // Mengembalikan tinggi laser
    }

    // Metode untuk memeriksa apakah laser telah bertabrakan dengan sesuatu
    public boolean hasCollided() {
        return collided;
    }

    // Metode untuk menandai laser sebagai bertabrakan atau tidak bertabrakan
    public void setCollided(boolean collided) {
        this.collided = collided;
    }
}

// jangan di ss
