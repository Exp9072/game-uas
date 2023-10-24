import java.util.ArrayList; // Impor kelas ArrayList dari pustaka Java
 // Impor kelas-kelas AWT untuk digunakan di dalam kelas Collision

public class Collision {
    // Metode untuk memeriksa tabrakan antara laser dan musuh
    public static void checkCollisions(ArrayList<Laser> playerLasers, ArrayList<Enemy> enemies, PlayerShip playerShip) {
        // Daftar laser yang akan dihapus setelah tabrakan pemain
        ArrayList<Laser> playerLasersToRemove = new ArrayList<>();
        // Daftar laser musuh yang akan dihapus setelah tabrakan
        ArrayList<Laser> enemyLasersToRemove = new ArrayList<>();
        // Daftar musuh yang akan dihapus setelah tabrakan
        ArrayList<Enemy> enemiesToRemove = new ArrayList<>();

        for (Laser playerLaser : playerLasers) {
            for (Enemy enemy : enemies) {
                // Memeriksa jika terjadi tabrakan  antara laser pemain dan musuh
                if (playerLaser.getX() < enemy.getX() + enemy.getWidth() && // Cek tabrakan  pada sumbu X (lebar)
                    playerLaser.getX() + playerLaser.getWidth() > enemy.getX() && // Cek tabrakan  pada sumbu X (lebar)
                    playerLaser.getY() < enemy.getY() + enemy.getHeight() && // Cek tabrakan  pada sumbu Y (tinggi)
                    playerLaser.getY() + playerLaser.getHeight() > enemy.getY()) { // Cek tabrakan  pada sumbu Y (tinggi)
                    
                    playerLasersToRemove.add(playerLaser); // Menandai laser pemain untuk dihapus
                    enemiesToRemove.add(enemy); // Menandai musuh untuk dihapus
                }
            }
        }

        playerLasers.removeAll(playerLasersToRemove); // Menghapus laser pemain yang telah menabrak musuh
        enemies.removeAll(enemiesToRemove); // Menghapus musuh yang telah tertabrak oleh laser pemain

        // Memeriksa tabrakan antara laser musuh dan kapal pemain
        for (Enemy enemy : enemies) {
            if (enemy instanceof StaticEnemy) {
                ArrayList<Laser> enemyLasers = ((StaticEnemy) enemy).getEnemyLasers(); // Mendapatkan daftar laser musuh
                for (Laser enemyLaser : enemyLasers) {
                    // Memeriksa jika terjadi tabarakan  antara laser musuh dan kapal pemain
                    if (enemyLaser.getX() < playerShip.getX() + playerShip.getWidth() && // Cek tabrakan  pada sumbu X (lebar)
                        enemyLaser.getX() + enemyLaser.getWidth() > playerShip.getX() && // Cek tabrakan  pada sumbu X (lebar)
                        enemyLaser.getY() < playerShip.getY() + playerShip.getHeight() && // Cek tabrakan  pada sumbu Y (tinggi)
                        enemyLaser.getY() + enemyLaser.getHeight() > playerShip.getY()) { // Cek tabrakan  pada sumbu Y (tinggi)
                            if (!enemyLaser.hasCollided()) {
                                playerShip.decreaseHealth(1); // Mengurangi kesehatan pemain
                                enemyLaser.setCollided(true); // Menandai laser musuh sebagai bertabrakan
                                enemyLasersToRemove.add(enemyLaser); // Menandai laser musuh untuk dihapus
                                System.out.println("Test 1");
                            }
                        }
                }
            }
        }

        for (Enemy enemy : enemies) {
            if (enemy instanceof StaticEnemy) {
                ArrayList<Laser> enemyLasers = ((StaticEnemy) enemy).getEnemyLasers(); // Mendapatkan daftar laser musuh
                enemyLasers.removeAll(enemyLasersToRemove); // Menghapus laser musuh yang telah bertabrakan dengan kapal pemain
            }
        }

        // Memeriksa tabrakan antara laser musuh dan kapal pemain (untuk musuh yang bergerak acak)
        for (Enemy enemy : enemies) {
            if (enemy instanceof RandomMovingEnemy) {
                ArrayList<Laser> enemyLasers = ((RandomMovingEnemy) enemy).getEnemyLasers(); // Mendapatkan daftar laser musuh
                for (Laser enemyLaser : enemyLasers) {
                    // Memeriksa jika terjadi tabrakan  antara laser musuh dan kapal pemain
                    if (enemyLaser.getX() < playerShip.getX() + playerShip.getWidth() && // Cek tabrakan  pada sumbu X (lebar)
                        enemyLaser.getX() + enemyLaser.getWidth() > playerShip.getX() && // Cek tabrakan  pada sumbu X (lebar)
                        enemyLaser.getY() < playerShip.getY() + playerShip.getHeight() && // Cek tabrakan  pada sumbu Y (tinggi)
                        enemyLaser.getY() + enemyLaser.getHeight() > playerShip.getY()) { // Cek tabrakan  pada sumbu Y (tinggi)
                            if (!enemyLaser.hasCollided()) {
                                playerShip.decreaseHealth(1); // Mengurangi kesehatan pemain
                                enemyLaser.setCollided(true); // Menandai laser musuh sebagai bertabrakan
                                enemyLasersToRemove.add(enemyLaser); // Menandai laser musuh untuk dihapus
                                System.out.println("Test 2");
                            }
                        }
                }
            }
        }

        for (Enemy enemy : enemies) {
            if (enemy instanceof RandomMovingEnemy) {
                ArrayList<Laser> enemyLasers = ((RandomMovingEnemy) enemy).getEnemyLasers(); // Mendapatkan daftar laser musuh
                enemyLasers.removeAll(enemyLasersToRemove); // Menghapus laser musuh yang telah bertabrakan dengan kapal pemain
            }
        }
    }
}
