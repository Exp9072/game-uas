 
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class Respawn {
    private ArrayList<Enemy> enemies;
    private Random random;
    private Timer respawnTimer;
    private int screenWidth;

    public Respawn(ArrayList<Enemy> enemies, Random random, int screenWidth) {
        this.enemies = enemies;
        this.random = new Random();
        

        respawnTimer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                respawnEnemies();
            }
        });
        respawnTimer.setRepeats(false);
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public void startRespawnTimer() {
        respawnTimer.start();
    }

    public void respawnEnemies() {
        int numStaticEnemies = random.nextInt(2) + 2;
        int numRandomMovingEnemies = random.nextInt(2) + 1;
        int sectionWidth = screenWidth / 3 - 40; // Width of the middle (white) section
        int minSpawnX = sectionWidth + 8;
        int maxSpawnX = 2 * sectionWidth;
        //System.out.println("RESPAWN DEBUG");
        //System.out.println("minSpawnX = " + minSpawnX);
        //System.out.println("maxSpawnX = " + maxSpawnX);
        //System.out.println("sectionWith = " + sectionWidth);
        //System.out.println("screenWidth = " + screenWidth);

        for (int i = 0; i < numStaticEnemies; i++) {
            int xPosition = minSpawnX + random.nextInt(maxSpawnX - minSpawnX);; // Spawn within the middle section
            int yPosition = -5;
            enemies.add(new StaticEnemy(xPosition, yPosition, 40));
        }

        for (int i = 0; i < numRandomMovingEnemies; i++) {
            int xPosition = minSpawnX + random.nextInt(maxSpawnX - minSpawnX); // Spawn within the middle section
            int yPosition = -random.nextInt(100) - 50;
            enemies.add(new RandomMovingEnemy(xPosition, yPosition, 1, 0, screenWidth - 20, 2000, random));
        }
    }     
}






