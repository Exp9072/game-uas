import java.util.ArrayList;
import java.awt.*;

public class Collision {
    // Metode untuk memeriksa tabrakan antara laser dan musuh
    
    public static void checkCollisions(ArrayList<Laser> playerLasers, ArrayList<Enemy> enemies, PlayerShip playerShip) {
        // Daftar laser yang akan dihapus setelah tabrakan
        ArrayList<Laser> playerLasersToRemove = new ArrayList<>();
        ArrayList<Laser> enemyLasersToRemove = new ArrayList<>();

        // Daftar musuh yang akan dihapus setelah tabrakan
        ArrayList<Enemy> enemiesToRemove = new ArrayList<>();


         // Pass the PlayerShip instance as a parameter
    

        for (Laser playerLaser : playerLasers) {
            for (Enemy enemy : enemies) {
                // Memeriksa jika terjadi tumpang tindih antara laser dan musuh
                if (playerLaser.getX() < enemy.getX() + enemy.getWidth() &&
                    playerLaser.getX() + playerLaser.getWidth() > enemy.getX() &&
                    playerLaser.getY() < enemy.getY() + enemy.getHeight() &&
                    playerLaser.getY() + playerLaser.getHeight() > enemy.getY()) {
                    
                    playerLasersToRemove.add(playerLaser); // Menandai laser untuk dihapus
                    enemiesToRemove.add(enemy); // Menandai musuh untuk dihapus
                }
            }
        }

        playerLasers.removeAll(playerLasersToRemove); // Menghapus laser yang telah menabrak musuh
        enemies.removeAll(enemiesToRemove); // Menghapus musuh yang telah tertabrak laser

        // Check collisions between enemy lasers and the player's ship
        for (Enemy enemy : enemies) {
            if (enemy instanceof StaticEnemy) {
                ArrayList<Laser> enemyLasers = ((StaticEnemy) enemy).getEnemyLasers();
                for (Laser enemyLaser : enemyLasers) {
                    if (enemyLaser.getX() < playerShip.getX() + playerShip.getWidth() &&
                        enemyLaser.getX() + enemyLaser.getWidth() > playerShip.getX() &&
                        enemyLaser.getY() < playerShip.getY() + playerShip.getHeight() &&
                        enemyLaser.getY() + enemyLaser.getHeight() > playerShip.getY()) {
                            if (!enemyLaser.hasCollided()) {
                                playerShip.decreaseHealth(1);
                                enemyLaser.setCollided(true);
                                enemyLasersToRemove.add(enemyLaser);
                                System.out.println("Test");
                            }
                        }
                }
            }
        }
        for (Enemy enemy : enemies) {
            if (enemy instanceof StaticEnemy) {
                ArrayList<Laser> enemyLasers = ((StaticEnemy) enemy).getEnemyLasers();
                enemyLasers.removeAll(enemyLasersToRemove);
            }
        }
    }
}
