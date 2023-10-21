import java.awt.*;
import java.util.Random;

public class RandomMovingEnemy extends Enemy {
    private int speed;
    private int direction;
    private int minRange;
    private int maxRange;
    private int lastDirectionChange;
    private int minMove;
    private int maxMove;
    private int currentMove;

    public RandomMovingEnemy(int x, int y, int speed, int minRange, int maxRange, Random random) {
        super(x, y, 40, 40); // Call the constructor of the base class (Enemy)
        this.speed = speed;
        this.direction = 1;
        this.minRange = minRange;
        this.maxRange = maxRange;
        this.lastDirectionChange = 0;
        this.minMove = 50;
        this.maxMove = 250;
        this.currentMove = getRandomMove(random);
    }

    public void move() {
        // Implement the movement logic for random moving enemies here
        // For example, you can use the same logic as in moveRandomly
        moveRandomly(new Random());
    }

    public void moveRandomly(Random random) {
        if (lastDirectionChange >= currentMove) {
            if (random.nextBoolean()) {
                direction = -direction;
                lastDirectionChange = 0;
                currentMove = getRandomMove(random);
            }
        } else {
            lastDirectionChange++;
        }

        if (x <= minRange || x >= maxRange) {
            direction = -direction;
            lastDirectionChange = 0;
            currentMove = getRandomMove(random);
        }

        x += speed * direction;
        x = Math.min(maxRange, Math.max(minRange, x));
    }

    private int getRandomMove(Random random) {
        return random.nextInt(maxMove - minMove + 1) + minMove;
    }

    public void draw(Graphics g) {
        g.setColor(Color.green);
        g.fillRect(x, y, width, height);
    }

    public int getX() {
        return x;
    }
}
