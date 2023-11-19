// border kanan blm benar
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StaticEnemy extends Enemy {
    private Timer shootingTimer; // Timer untuk menembak
    private int shootingDelay = 2000; // Keterlambatan penembakan dalam milidetik
    private ArrayList<Laser> enemyLasers; // ArrayList untuk mengelola laser musuh
    private int destinationY;
    private Timer moveTimer;

    // Konstruktor untuk StaticEnemy, menerima parameter koordinat x, tinggi layar, dan lebar musuh
    public StaticEnemy(int sectionX, int screenHeight, int enemyWidth) {
        // Memanggil konstruktor kelas induk dengan koordinat x, y, lebar 40, dan tinggi 40
        super(sectionX, screenHeight - 200 - enemyWidth, enemyWidth, enemyWidth);
        enemyLasers = new ArrayList<>();
        // Inisialisasi timer penembakan
        this.destinationY = 256;
        shootingTimer = new Timer(shootingDelay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shoot();
            }
        });

        shootingTimer.start(); // Mulai timer penembakan
        // Inisialisasi timer pergerakan untuk mengendalikan pergerakan musuh
        moveTimer = new Timer(30, new ActionListener() { // UBAH DELAY UNTUK MENGURANGI LAGG
            @Override
            public void actionPerformed(ActionEvent e) {
                move();
            }
        });
        moveTimer.start(); // Mulai timer pergerakan
    }

    // Metode untuk mendapatkan daftar laser musuh
    public ArrayList<Laser> getEnemyLasers() {
        return enemyLasers;
    }

    // Implementasi metode abstrak untuk pergerakan (musuh statis tidak bergerak)
    @Override
    public void move() {
        // Tentukan arah pergerakan menggunakan Math.signum
        int arahPergerakan = (int) Math.signum(destinationY - y);

        // Perbarui posisi berdasarkan arah pergerakan
        y += arahPergerakan + 2;
        //System.err.println(y);
        //System.out.println(destinationY);

        // Periksa apakah tujuan telah tercapai
        if (y == destinationY) {
            moveTimer.stop(); // Hentikan timer pergerakan ketika tujuan tercapai
        }
    }

    public void stopTimers() {
        shootingTimer.stop();
        moveTimer.stop();
    }


    // Implementasi metode abstrak untuk mengeksekusi penembakan
    @Override
    public void shoot() {
        // Implementasi logika penembakan untuk musuh statis
        Laser laser = new Laser(x + width / 2, y + height);
        enemyLasers.add(laser);
    }
}

// jangan di ss
