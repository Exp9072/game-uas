import java.awt.Graphics;
import java.awt.Color;

public class Laser {
    private int x, y; // Koordinat x dan y dari laser
    private int width, height; // Lebar dan tinggi laser
    private int speed; // Kecepatan laser
    private boolean collided;

    public Laser(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 5; // Mengatur lebar laser
        this.height = 15; // Mengatur tinggi laser
        this.speed = 5; // Mengatur kecepatan laser
    }

    public void move(int direction) {
        y += direction * speed; // Move the laser based on the specified direction
    }

    public void draw(Graphics g) {
        g.setColor(Color.PINK); // Mengatur warna laser
        g.fillRect(x, y, width, height); // Gambar laser
    }

    public int getY() {
        return y; // Mengembalikan posisi y laser
    }

    public int getX() {
        return x; // Return the x-coordinate of the laser
    }

    public int getWidth() {
        return width; // Return the width of the laser
    }

    public int getHeight() {
        return height; // Return the height of the laser
    }
    public boolean hasCollided() {
        return collided;
    }
    public void setCollided(boolean collided) {
        this.collided = collided;
    }
}
