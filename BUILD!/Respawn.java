 
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
        
        //System.out.println("Screen Widht Respawn class before this.screenwidth = " + screenWidth);

        respawnTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                respawnEnemies();
            }
        });
        respawnTimer.setRepeats(false);
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
        //System.out.println("Screen Widht Respawn class after this.screenwidt = " + screenWidth);
    }

    public void startRespawnTimer() {
        respawnTimer.start();
    }



    
    public void respawnEnemies() {
        int numStaticEnemies = random.nextInt(2) + 2;
        int numRandomMovingEnemies = random.nextInt(2) + 1;
        int sectionWidth = screenWidth / 3; // Width of the middle (white) section
        int minSpawnX = sectionWidth + 8;
        int maxSpawnX = 2 * sectionWidth - 8; // Adjust as needed
/* 
        System.out.println("RESPAWN DEBUG");
        System.out.println("minSpawnX = " + minSpawnX);
        System.out.println("maxSpawnX = " + maxSpawnX);
        System.out.println("sectionWith = " + sectionWidth);
        System.out.println("screenWidth = " + screenWidth);
*/
        for (int i = 0; i < numStaticEnemies; i++) {

            int xPosition = minSpawnX + random.nextInt(maxSpawnX - minSpawnX);; // Spawn within the middle section
            //System.out.println("xPosisiton = " + xPosition);
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