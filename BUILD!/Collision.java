import java.util.ArrayList;
import java.awt.*;

public class Collision {
    public static void checkCollisions(ArrayList<Laser> lasers, ArrayList<Enemy> enemies) {
        ArrayList<Laser> lasersToRemove = new ArrayList<>();
        ArrayList<Enemy> enemiesToRemove = new ArrayList<>();

        for (Laser laser : lasers) {
            for (Enemy enemy : enemies) {
                if (laser.getX() < enemy.getX() + enemy.getWidth() &&
                    laser.getX() + laser.getWidth() > enemy.getX() &&
                    laser.getY() < enemy.getY() + enemy.getHeight() &&
                    laser.getY() + laser.getHeight() > enemy.getY()) {
                    
                    lasersToRemove.add(laser);
                    enemiesToRemove.add(enemy);
                }
            }
        }

        lasers.removeAll(lasersToRemove);
        enemies.removeAll(enemiesToRemove);
    }
}
