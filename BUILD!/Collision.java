import java.util.ArrayList;
import java.awt.*;

public class Collision {
    // Metode untuk memeriksa tabrakan antara laser dan musuh
    public static void checkCollisions(ArrayList<Laser> lasers, ArrayList<Enemy> enemies) {
        // Daftar laser yang akan dihapus setelah tabrakan
        ArrayList<Laser> lasersToRemove = new ArrayList<>();

        // Daftar musuh yang akan dihapus setelah tabrakan
        ArrayList<Enemy> enemiesToRemove = new ArrayList<>();

        for (Laser laser : lasers) {
            for (Enemy enemy : enemies) {
                // Memeriksa jika terjadi tumpang tindih antara laser dan musuh
                if (laser.getX() < enemy.getX() + enemy.getWidth() &&
                    laser.getX() + laser.getWidth() > enemy.getX() &&
                    laser.getY() < enemy.getY() + enemy.getHeight() &&
                    laser.getY() + laser.getHeight() > enemy.getY()) {
                    
                    lasersToRemove.add(laser); // Menandai laser untuk dihapus
                    enemiesToRemove.add(enemy); // Menandai musuh untuk dihapus
                }
            }
        }

        lasers.removeAll(lasersToRemove); // Menghapus laser yang telah menabrak musuh
        enemies.removeAll(enemiesToRemove); // Menghapus musuh yang telah tertabrak laser
    }
}
