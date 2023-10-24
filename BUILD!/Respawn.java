import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class Respawn {
    private ArrayList<Enemy> enemies;
    private Random random;
    private Timer respawnTimer;

    public Respawn(ArrayList<Enemy> enemies, Random random) {
        this.enemies = enemies;
        this.random = random;

        // Initialize the respawn timer
        respawnTimer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                respawnEnemies();
            }
        });
        respawnTimer.setRepeats(false);
    }

    public void startRespawnTimer() {
        respawnTimer.start();
    }

        public void respawnEnemies() {
        int numStaticEnemies = random.nextInt(4) + 2; // Randomly generate between 2 and 5 StaticEnemies
        int numRandomMovingEnemies = random.nextInt(3) + 1; // Randomly generate between 1 and 3 RandomMovingEnemies

        for (int i = 0; i < numStaticEnemies; i++) {
            int xPosition = random.nextInt(700); // Random X position
            int yPosition = -random.nextInt(100) - 50; // Random Y position above the screen
            enemies.add(new StaticEnemy(xPosition, yPosition));
        }

        for (int i = 0; i < numRandomMovingEnemies; i++) {
            int xPosition = random.nextInt(700); // Random X position
            int yPosition = -random.nextInt(100) - 50; // Random Y position above the screen
            enemies.add(new RandomMovingEnemy(xPosition, yPosition, 1, 0, 800, 1000, random));
        }
    }
}

