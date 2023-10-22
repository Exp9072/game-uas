import java.awt.*;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StaticEnemy extends Enemy {
    private Timer shootingTimer; // Timer for shooting
    private int shootingDelay = 3000; // Shooting delay in milliseconds
    private ArrayList<Laser> enemyLasers; // ArrayList to manage enemy lasers

    public StaticEnemy(int x, int y) {
        super(x, y, 40, 40); // Memanggil konstruktor kelas induk dengan koordinat x, y, lebar 40, dan tinggi 40
        enemyLasers = new ArrayList<>();
        // Initialize the shooting timer
        shootingTimer = new Timer(shootingDelay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shoot();
            }
        });

        shootingTimer.start(); // Start the shooting timer
    }

    // Metode untuk mendapatkan daftar laser musuh
    public ArrayList<Laser> getEnemyLasers() {
        return enemyLasers;
    }

    // Implementasi metode abstrak untuk pergerakan (musuh statis tidak bergerak)
    @Override
    public void move() {
        // Musuh statis tidak bergerak, sehingga tidak ada implementasi di sini
    }

    // Implementasi metode abstrak untuk mengeksekusi penembakan
    @Override
    public void shoot() {
        System.out.println("StaticEnemy ID " + this.hashCode() + " is shooting");

        // Implementasi logika penembakan untuk musuh statis
        Laser laser = new Laser(x + width / 2, y + height);
        enemyLasers.add(laser);
    }
}