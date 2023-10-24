import java.awt.*;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StaticEnemy extends Enemy {
    private Timer shootingTimer; // Timer for shooting
    private int shootingDelay = 3000; // Shooting delay in milliseconds
    private ArrayList<Laser> enemyLasers; // ArrayList to manage enemy lasers
    private int destinationY;
    private Timer moveTimer;

    public StaticEnemy(int x, int y) {
        super(x, y, 40, 40); // Memanggil konstruktor kelas induk dengan koordinat x, y, lebar 40, dan tinggi 40
        enemyLasers = new ArrayList<>();
        // Initialize the shooting timer
        this.destinationY = 100;
        shootingTimer = new Timer(shootingDelay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shoot();
            }
        });

        shootingTimer.start(); // Start the shooting timer
        // Initialize the move timer to control enemy movement
        moveTimer = new Timer(35, new ActionListener() {
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

    // Implementasi metode abstrak untuk pergerakan (musuh statis tidak bergerak)
    @Override
    public void move() {
        if (y < destinationY) {
            System.out.println(destinationY);
            y++; // Move downward until reaching the destinationY
        } else {
            moveTimer.stop(); // Stop the move timer when the destination is reached
        }
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