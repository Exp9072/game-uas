import java.awt.*;

public class PlayerShip {
    private int x, y; // Current position of the player ship
    private int width, height; // Dimensions of the player ship
    private int speed; // Movement speed
    private int lives; // Number of lives the player has

    public PlayerShip(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 40; // Set your desired dimensions
        this.height = 40; // Set your desired dimensions
        this.speed = 5; // Set your desired speed
        this.lives = 3; // Set the initial number of lives
    }

    public void moveLeft() {
        x -= speed;
    }

    public void moveRight() {
        x += speed;
    }

    public void draw(Graphics g) {
        // Draw the player ship on the screen using graphics context (g)
        g.setColor(Color.blue); // Set the ship's color
        g.fillRect(x, y, width, height); // Draw a rectangle as the ship
    }
}
