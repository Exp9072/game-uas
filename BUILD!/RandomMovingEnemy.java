// batas screen rusak

import java.awt.*;
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

    public RandomMovingEnemy(int x, int y, int speed, int minRange, int maxRange, int shootingInterval, Random random) {
        super(x, y, 40, 40); // Memanggil konstruktor kelas dasar (Enemy) dengan posisi awal dan ukuran musuh
        this.speed = speed;
        this.direction = 1; // Arah awal pergerakan
        this.minRange = minRange; // Batas minimum horizontal
        this.maxRange = maxRange; // Batas maksimum horizontal
        this.lastDirectionChange = 0; // Awalnya belum ada perubahan arah
        this.minMove = 50; // Jarak minimum perubahan arah
        this.maxMove = 250; // Jarak maksimum perubahan arah
        this.shootingInterval = shootingInterval;
        this.currentMove = getRandomMove(random); // Mendapatkan jarak perubahan arah secara acak
        enemyLasers = new ArrayList<>();
        shootingTimer = new Timer(shootingInterval, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shoot();
            }
        });
        
        shootingTimer.start();

    }
    
    // Metode untuk mendapatkan daftar laser musuh
    public ArrayList<Laser> getEnemyLasers() {
        return enemyLasers;
    }

    // Metode untuk menggerakkan musuh
    public void move() {
        // Implementasikan logika pergerakan untuk musuh yang bergerak secara acak di sini
        // Misalnya, Anda dapat menggunakan logika yang sama dengan moveRandomly
        moveRandomly(new Random());
    }

    // Metode untuk menggerakkan musuh secara acak
    public void moveRandomly(Random random) {
        if (lastDirectionChange >= currentMove) {
            if (random.nextBoolean()) {
                direction = -direction; // Mengubah arah pergerakan secara acak
                lastDirectionChange = 0; // Mereset waktu sejak perubahan arah
                currentMove = getRandomMove(random); // Mendapatkan jarak perubahan arah secara acak
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
        System.out.println("RandomMovingEnemy ID " + this.hashCode() + " is shooting");

        // Implementasi logika penembakan untuk musuh yang bergerak secara acak
        Laser laser = new Laser(x + width / 2, y + height);
        enemyLasers.add(laser);
    }

    // Memulai timer penembakan
    public void startShootingTimer() {
        shootingTimer.start();
    }
}
