import java.awt.*;
import java.awt.event.KeyEvent;

public class PlayerShip {
    private int x, y;
    private int width, height;
    private int speed;
    private int screenWidth;
    private boolean movingLeft;
    private boolean movingRight;

    public PlayerShip(int x, int y, int screenWidth) {
        this.x = x;
        this.y = y;
        this.width = 40;
        this.height = 40;
        this.speed = 3;
        this.screenWidth = screenWidth;
        this.movingLeft = false;
        this.movingRight = false;
    }

    public void moveLeft() {
        if (x - speed >= 0) {
            x -= speed;
            System.out.println("Left");
        }
    }

    public void moveRight() {
        if (x + speed + width <= screenWidth - 8) {
            x += speed;
            System.out.println("Right");
        }
    }

    public void handleInput(int keyCode) {
        if (keyCode == KeyEvent.VK_LEFT) {
            movingLeft = true;
            movingRight = false;
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            movingRight = true;
            movingLeft = false;
        }
    }

    public void handleKeyRelease(int keyCode) {
        if (keyCode == KeyEvent.VK_LEFT) {
            movingLeft = false;
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            movingRight = false;
        }
    }

    public void update() {
        if (movingLeft) {
            moveLeft();
        } else if (movingRight) {
            moveRight();
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.blue);
        g.fillRect(x, y, width, height);
    }

    public int getX() {
        return x;
    }

    public int getWidth() {
        return width;
    }

    public int getY() {
        return y;
    }
}
