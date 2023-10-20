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

        playerShip = new PlayerShip(380, 500, getWidth());

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
        
            @Override
            public void keyReleased(KeyEvent e) {
                playerShip.handleKeyRelease(e.getKeyCode());
            }
        });
        setFocusable(true);
        requestFocusInWindow();
        random = new Random(); // Initialize the random object
    }

    public void initializePlayerShip(int screenWidth) {
        playerShip = new PlayerShip(380, 500, screenWidth);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Update game logic
        playerShip.update();
        moveRandomMovingEnemies();
        repaint();
    }

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
        g.setColor(Color.black);
        g.drawString("Enemy X-Position: " + x, x, y);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Galaga Game");
        GalagaGame game = new GalagaGame();
        frame.add(game);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.getContentPane().setPreferredSize(new Dimension(850, 600)); // Set the size of the content pane
        frame.pack(); // Pack the frame to adjust to the preferred size
        
        game.initializePlayerShip(frame.getWidth());
    }

    static class PlayerShip {
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
            if (x + speed + width <= screenWidth - 10) {
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
        private int lastDirectionChange;
        private int minMove;
        private int maxMove;
        private int currentMove;

        public RandomMovingEnemy(int x, int y, int speed, int minRange, int maxRange, Random random) {
            this.x = x;
            this.y = y;
            this.width = 40;
            this.height = 40;
            this.speed = speed;
            this.direction = 1;
            this.minRange = minRange;
            this.maxRange = maxRange;
            this.lastDirectionChange = 0;
            this.minMove = 50; // Minimum step
            this.maxMove = 250; // Maximum step
            this.currentMove = getRandomMove(random);
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
            
            // Check if the enemy is near the screen boundaries and reverse direction if necessary
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
}




/* 
Menurut gpt POINT 10,11,12 BLM 
1. Pengantar Java OOP  | Y
2. Kerangka Program OOP | Y
3. Dasar Pemrograman Java | Y
4. Struktur Kontrol  | Y
5. Array | Y
6. Method | Y
7. Encapsulation | Y
8. Inheritance | Y
9. Polimorfisme | Y
10. Static & Final Variable | N ?
11. Exception Handling | N ?
12. Abstract Class & Interface | N ?
13. Java Collection | Y
14. GUI | Y
*/







