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
    private ArrayList<Enemy> enemies; // Use a list of generic enemies
    private ArrayList<Laser> lasers;
    private Random random;

    public GalagaGame() {
        timer = new Timer(10, this);
        timer.start();

        playerShip = new PlayerShip(380, 500, getWidth());
        lasers = new ArrayList<>();
        enemies = new ArrayList<>();

        // Initialize player ship and add enemies
        enemies.add(new StaticEnemy(100, 100)); // Example static enemy
        enemies.add(new StaticEnemy(250, 100));
        enemies.add(new RandomMovingEnemy(200, 200, 1, 0, 800, new Random())); // Example random moving enemy

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                playerShip.handleInput(e.getKeyCode());
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    lasers.add(new Laser(playerShip.getX() + playerShip.getWidth() / 2, playerShip.getY()));
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                playerShip.handleKeyRelease(e.getKeyCode());
            }
        });
        setFocusable(true);
        requestFocusInWindow();
        random = new Random();
    }
    
    public void initializePlayerShip(int screenWidth) {
        playerShip = new PlayerShip(380, 500, screenWidth);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        playerShip.update();
        moveRandomMovingEnemies();
        updateLasers();
        repaint();

    }

    private void updateLasers() {
        ArrayList<Laser> lasersToRemove = new ArrayList<>();
        
        for (Laser laser : lasers) {
            laser.move(); // Move the laser
            if (laser.getY() < 0) {
                lasersToRemove.add(laser); // Mark lasers that have gone off the screen
            }
        }
        
        lasers.removeAll(lasersToRemove); // Remove off-screen lasers
    }

    private void moveRandomMovingEnemies() {
        for (Enemy enemy : enemies) {
            if (enemy instanceof RandomMovingEnemy) {
                ((RandomMovingEnemy) enemy).moveRandomly(random);
            }
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        playerShip.draw(g);

        for (Enemy enemy : enemies) {
            enemy.draw(g);
        }

        for (Laser laser : lasers) {
            laser.draw(g);
        }

        for (Enemy enemy : enemies) {
            if (enemy instanceof RandomMovingEnemy) {
                drawLocationIndicator(g, enemy.getX(), 570);
            }
        }
    }

    private void drawLocationIndicator(Graphics g, int x, int y) {
        g.setColor(Color.black);
        g.drawString("Enemy X-Position: " + x, x, y);
    }

}