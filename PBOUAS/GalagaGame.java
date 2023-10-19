import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class GalagaGame extends JPanel implements ActionListener {
    private Timer timer;
    private PlayerShip playerShip;
    private ArrayList<EnemyShip> enemyShips;
    private ArrayList<RandomMovingEnemy> randomMovingEnemies;
    private Random random;

    public GalagaGame() {
        timer = new Timer(10, this);
        timer.start();

        playerShip = new PlayerShip(380, 500);

        enemyShips = new ArrayList<>();
        enemyShips.add(new EnemyShip(100, 100)); // Static enemy
        enemyShips.add(new EnemyShip(300, 100)); // Static enemy

        randomMovingEnemies = new ArrayList<>();
        randomMovingEnemies.add(new RandomMovingEnemy(200, 200, 1, 0, 800, new Random())); // Randomly moving enemy with larger movements

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                playerShip.handleInput(e.getKeyCode());
            }
        });
        setFocusable(true);

        random = new Random(); // Initialize the random object
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Update game logic
        moveRandomMovingEnemies();
        repaint();
    }
//akjalkjad;kljda;djasamnadm,nasdm,n
    private void moveRandomMovingEnemies() {
        for (RandomMovingEnemy enemy : randomMovingEnemies) {
            enemy.moveRandomly(random);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        playerShip.draw(g);

        for (EnemyShip enemy : enemyShips) {
            enemy.draw(g);
        }

        for (RandomMovingEnemy enemy : randomMovingEnemies) {
            enemy.draw(g);
            drawLocationIndicator(g, enemy.getX(), 570); // Display enemy's x-position at the bottom
        }
    }

    private void drawLocationIndicator(Graphics g, int x, int y) {
        g.setColor(Color.white);
        g.drawString("Enemy X-Position: " + x, x, y);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Galaga Game");
        GalagaGame game = new GalagaGame();
        frame.add(game);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    static class PlayerShip {
        private int x, y;
        private int width, height;
        private int speed;

        public PlayerShip(int x, int y) {
            this.x = x;
            this.y = y;
            this.width = 40;
            this.height = 40;
            this.speed = 5;
        }

        public void moveLeft() {
            x -= speed;
        }

        public void moveRight() {
            x += speed;
        }

        public void handleInput(int keyCode) {
            if (keyCode == KeyEvent.VK_LEFT) {
                moveLeft();
            } else if (keyCode == KeyEvent.VK_RIGHT) {
                moveRight();
            }
        }

        public void draw(Graphics g) {
            g.setColor(Color.blue);
            g.fillRect(x, y, width, height);
        }
    }

    static class EnemyShip {
        private int x, y;
        private int width, height;

        public EnemyShip(int x, int y) {
            this.x = x;
            this.y = y;
            this.width = 40;
            this.height = 40;
        }

        public void draw(Graphics g) {
            g.setColor(Color.red);
            g.fillRect(x, y, width, height);
        }
    }

    
    static class RandomMovingEnemy {
        private int x, y;
        private int width, height;
        private int speed;
        private int direction;
        private int minRange;
        private int maxRange;

        public RandomMovingEnemy(int x, int y, int speed, int minRange, int maxRange, Random random) {
            this.x = x;
            this.y = y;
            this.width = 40;
            this.height = 40;
            this.speed = speed;
            this.direction = 1;
            this.minRange = minRange;
            this.maxRange = maxRange;
        }

        public void moveRandomly(Random random) {
            if (random.nextBoolean()) {
                direction = -direction;
            }
            x += speed * direction;
            x = Math.min(maxRange, Math.max(minRange, x));
        }

        public void draw(Graphics g) {
            g.setColor(Color.green);
            g.fillRect(x, y, width, height);
        }
        public int getX() {
            return x;
        }
    }
}











