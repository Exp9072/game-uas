// batas screen rusak
// batas height bawah kurang panjang

import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RandomMovingEnemy extends Enemy {
    private int speed; // Kecepatan pergerakan musuh
    private int direction; // Arah pergerakan (1 atau -1)
    private int minRange; // Batas minimum pergerakan horizontal
    private int maxRange; // Batas maksimum pergerakan horizontal
    private int lastDirectionChange; // Waktu sejak perubahan arah terakhir kali
    private int minMove; // Jarak minimum perubahan arah
    private int maxMove; // Jarak maksimum perubahan arah
    private int currentMove; // Jarak perubahan arah saat ini
    private ArrayList<Laser> enemyLasers; // ArrayList untuk mengelola laser musuh
    private Timer shootingTimer; // Timer untuk menembak
    private int destinationY;
    private Timer moveTimer;
    private int UpDirection;


    public RandomMovingEnemy(int x, int y, int speed, int minRange, int maxRange, int shootingInterval, Random random) {
        super(x, y, 40, 40); // Memanggil konstruktor kelas dasar (Enemy) dengan posisi awal dan ukuran musuh
        this.speed = speed;
        this.UpDirection = 1;
        this.direction = 1; // Arah awal pergerakan
        this.minRange = minRange; // Batas minimum horizontal
        this.maxRange = maxRange; // Batas maksimum horizontal
        this.lastDirectionChange = 0; // Awalnya belum ada perubahan arah
        this.minMove = 50; // Jarak minimum perubahan arah
        this.maxMove = 250; // Jarak maksimum perubahan arah
        this.shootingInterval = shootingInterval;
        this.currentMove = getRandomMove(random); // Mendapatkan jarak perubahan arah secara acak
        this.destinationY = 200;
        enemyLasers = new ArrayList<>();
        shootingTimer = new Timer(shootingInterval, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shoot();
            }
        });
        
        shootingTimer.start();

        // Initialize the move timer to control enemy movement
        moveTimer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                move();
            }
        });
            moveTimer.start(); // Start the move timer
    

    }
    
    // Metode untuk mendapatkan daftar laser musuh
    public ArrayList<Laser> getEnemyLasers() {
        return enemyLasers;
    }

    // Metode untuk menggerakkan musuh
    public void move() {
        if (y < destinationY) {
            //System.out.println(destinationY);
            y++; // Move downward until reaching the destinationY
        } else {
            moveTimer.stop(); // Stop the move timer when the destination is reached
            moveRandomlyLeftRight(new Random());
            moveRandomlyUpDown(new Random());
        }
    }
    
    // Metode untuk menggerakkan musuh secara acak
    public void moveRandomlyLeftRight(Random random) {
        if (lastDirectionChange >= currentMove) {
            if (random.nextBoolean()) {
                direction = -direction; // Mengubah arah pergerakan secara acak
                lastDirectionChange = 0; // Mereset waktu sejak perubahan arah
                currentMove = getRandomMove(random); // Mendapatkan jarak perubahan arah secara acak
            }
            if (random.nextBoolean()){
                UpDirection = -UpDirection;
            }
        } else {
            lastDirectionChange++;
        }

        if (x <= minRange || x >= maxRange) {
            direction = -direction; // Mengubah arah pergerakan jika mencapai batas horizontal
            lastDirectionChange = 0; // Mereset waktu sejak perubahan arah
            currentMove = getRandomMove(random); // Mendapatkan jarak perubahan arah secara acak
        }

        x += speed * direction; // Memindahkan musuh
        x = Math.min(maxRange, Math.max(minRange, x)); // Memastikan posisi tetap dalam batasan horizontal
    }

    public void moveRandomlyUpDown(Random random) {
        if (lastDirectionChange >= currentMove) {
            if (random.nextBoolean()) {
                direction = -direction; // Change the horizontal direction randomly
                lastDirectionChange = 0; // Reset the time since the last direction change
                currentMove = getRandomMove(random); // Get a random change distance
            }
            if (random.nextBoolean()) {
                UpDirection = -UpDirection; // Change the vertical direction randomly
            }
        } else {
            lastDirectionChange++;
        }
    
        if (x <= minRange || x >= maxRange) {
            direction = -direction; // Change the horizontal direction if it reaches horizontal boundaries
            lastDirectionChange = 0; // Reset the time since the last direction change
            currentMove = getRandomMove(random); // Get a random change distance
        }
    
        if (y <= 0 || y >= destinationY) {
            UpDirection = -UpDirection; // Change the vertical direction if it reaches vertical boundaries
        }
    
        x += speed * direction; // Move horizontally
        y += speed * UpDirection; // Move vertically
        x = Math.min(maxRange, Math.max(minRange, x)); // Ensure that the position stays within horizontal boundaries
        //System.out.println("GERAK");
    }

    // Metode untuk mendapatkan jarak perubahan arah secara acak
    private int getRandomMove(Random random) {
        return random.nextInt(maxMove - minMove + 1) + minMove; // Mendapatkan jarak perubahan arah secara acak
    }

    // Metode untuk menggambar musuh
    public void draw(Graphics g) {
        g.setColor(Color.green); // Mengatur warna musuh
        g.fillRect(x, y, width, height); // Menggambar musuh
    }

    // Metode untuk mendapatkan posisi horizontal musuh
    public int getX() {
        return x;
    }

    // Metode untuk mengeksekusi penembakan musuh
    @Override
    public void shoot() {
        //System.out.println("RandomMovingEnemy ID " + this.hashCode() + " is shooting");

        // Implementasi logika penembakan untuk musuh yang bergerak secara acak
        Laser laser = new Laser(x + width / 2, y + height);
        enemyLasers.add(laser);
    }

    // Memulai timer penembakan
    public void startShootingTimer() {
        shootingTimer.start();
    }
}
