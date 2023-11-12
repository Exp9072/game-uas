import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

// Kelas Respawn bertanggung jawab untuk mengatur respawn musuh dalam permainan Galaga.
public class Respawn {
    private ArrayList<Enemy> enemies; // Daftar musuh yang akan di-respawn
    private Random random; // Objek Random untuk menghasilkan nilai acak
    private Timer respawnTimer; // Timer untuk mengatur interval respawn
    private int screenWidth; // Lebar layar permainan

    // Konstruktor untuk Respawn, menerima daftar musuh, objek Random, dan lebar layar sebagai parameter
    public Respawn(ArrayList<Enemy> enemies, Random random, int screenWidth) {
        this.enemies = enemies;
        this.random = new Random();

        respawnTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                respawnEnemies();
            }
        });
        respawnTimer.setRepeats(false); // Hanya menjalankan respawn sekali setiap kali timer selesai
    }

    // Metode untuk mengatur lebar layar permainan
    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    // Metode untuk memulai timer respawn
    public void startRespawnTimer() {
        respawnTimer.start();
    }

    // Metode untuk melakukan respawn musuh
    public void respawnEnemies() {
        int numStaticEnemies = random.nextInt(2) + 2; // Jumlah musuh statis yang akan di-respawn
        int numRandomMovingEnemies = random.nextInt(1) + 1; // Jumlah musuh acak yang akan di-respawn
        int sectionWidth = screenWidth / 3; // Lebar bagian tengah (putih)
        int minSpawnX = sectionWidth + 8;
        int maxSpawnX = 2 * sectionWidth - 8; // Sesuaikan sesuai kebutuhan

        for (int i = 0; i < numStaticEnemies; i++) {
            int xPosition = minSpawnX + random.nextInt(maxSpawnX - minSpawnX); // Spawn di dalam bagian tengah
            int yPosition = -5;
            enemies.add(new StaticEnemy(xPosition, yPosition, 40)); // Tambahkan musuh statis ke daftar
        }

        for (int i = 0; i < numRandomMovingEnemies; i++) {
            int xPosition = minSpawnX + random.nextInt(maxSpawnX - minSpawnX); // Spawn di dalam bagian tengah
            int yPosition = -random.nextInt(100) - 50;
            // Tambahkan musuh acak ke daftar dengan parameter yang sesuai
            enemies.add(new RandomMovingEnemy(xPosition, yPosition, 1, 0, screenWidth - 20, 2000, random));
        }
    }     
}

// jangan di ss
