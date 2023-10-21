import java.awt.*;

public abstract class Enemy {
    protected int x, y;
    protected int width, height;

    public Enemy(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = 40;
        this.height = 40;
    }

    public int getX() {
        return x;
    }
    
    public abstract void move();

    public void draw(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(x, y, width, height);
    }
}
