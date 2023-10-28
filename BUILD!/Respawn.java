 
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
        int numStaticEnemies = random.nextInt(2) + 2;
        int numRandomMovingEnemies = random.nextInt(3) + 1;
        int sectionWidth = 1800 / 3; // Width of the middle (white) section
        int minSpawnX = sectionWidth;
        int maxSpawnX = 2 * sectionWidth;

        for (int i = 0; i < numStaticEnemies; i++) {
            int xPosition = minSpawnX + random.nextInt(maxSpawnX - minSpawnX);; // Spawn within the middle section
            int yPosition = -5;
            enemies.add(new StaticEnemy(xPosition, yPosition, 40));
        }

        for (int i = 0; i < numRandomMovingEnemies; i++) {
            int xPosition = minSpawnX + random.nextInt(maxSpawnX - minSpawnX); // Spawn within the middle section
            int yPosition = -random.nextInt(100) - 50;
           //enemies.add(new RandomMovingEnemy(xPosition, yPosition, 1, 0, 800, 1000, random));
        }
    }
    
        
}






