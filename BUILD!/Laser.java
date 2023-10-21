import java.awt.*;

public class Laser {
    private int x, y;
    private int width, height;
    private int speed;

    public Laser(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 5; // Set the laser width
        this.height = 15; // Set the laser height
        this.speed = 5; // Set the laser speed
    }

    public void move() {
        y -= speed; // Move the laser upwards
    }

    public void draw(Graphics g) {
        g.setColor(Color.PINK); // Set the laser color
        g.fillRect(x, y, width, height); // Draw the laser
    }

    public int getY() {
        return y;
    }
}
