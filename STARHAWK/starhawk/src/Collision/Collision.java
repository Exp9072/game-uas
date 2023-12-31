package Collision;

import Main.*;
import Objects.*;



import java.io.File;
import java.util.ArrayList; 
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Collision {
    private static Clip hurtSound;
    // Metode untuk memeriksa tabrakan antara laser dan musuh
    public static void checkCollisions(ArrayList<Laser> playerLasers, ArrayList<Enemy> enemies, PlayerShip playerShip, StarHawk game) {
        // Daftar laser yang akan dihapus setelah tabrakan pemain
        ArrayList<Laser> playerLasersToRemove = new ArrayList<>();
        // Daftar laser musuh yang akan dihapus setelah tabrakan
        ArrayList<Laser> enemyLasersToRemove = new ArrayList<>();
        // Daftar musuh yang akan dihapus setelah tabrakan
        ArrayList<Enemy> enemiesToRemove = new ArrayList<>();

        try {
            // Muat suara sakit dari file
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/res/audio/CPlayerHurt_1.wav"));
            hurtSound = AudioSystem.getClip();
            hurtSound.open(audioInputStream);
        } catch (Exception e) {
            // Cetak jejak tumpukan jika terjadi pengecualian saat inisialisasi suara
            e.printStackTrace();
        }

        for (Laser playerLaser : playerLasers) {
            for (Enemy enemy : enemies) {
                // Memeriksa jika terjadi tabrakan  antara laser pemain dan musuh
                if (playerLaser.getX() < enemy.getX() + enemy.getWidth() && // Cek tabrakan  pada sumbu X (lebar)
                    playerLaser.getX() + playerLaser.getWidth() > enemy.getX() && // Cek tabrakan  pada sumbu X (lebar)
                    playerLaser.getY() < enemy.getY() + enemy.getHeight() && // Cek tabrakan  pada sumbu Y (tinggi)
                    playerLaser.getY() + playerLaser.getHeight() > enemy.getY()) { // Cek tabrakan  pada sumbu Y (tinggi)
                    
                    playerLasersToRemove.add(playerLaser); // Menandai laser pemain untuk dihapus
                    enemiesToRemove.add(enemy); // Menandai musuh untuk dihapus

                    if (enemy instanceof StaticEnemy) {
                        playerShip.increaseScore(5); // Add 5 points for hitting a StaticEnemy
                    } else if (enemy instanceof RandomMovingEnemy) {
                        playerShip.increaseScore(10); // Add 10 points for hitting a RandomMovingEnemy
                    }
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
                                hurtSound.setFramePosition(0);
                                hurtSound.start();
                                playerShip.decreaseHealth(1); // Mengurangi kesehatan pemain
                                enemyLaser.setCollided(true); // Menandai laser musuh sebagai bertabrakan
                                enemyLasersToRemove.add(enemyLaser); // Menandai laser musuh untuk dihapus
                                //System.out.println("Test 1");
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
                                hurtSound.setFramePosition(0);
                                hurtSound.start();
                                enemyLaser.setCollided(true); // Menandai laser musuh sebagai bertabrakan
                                enemyLasersToRemove.add(enemyLaser); // Menandai laser musuh untuk dihapus
                                //System.out.println("Test 2");
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

// jangan di ss
