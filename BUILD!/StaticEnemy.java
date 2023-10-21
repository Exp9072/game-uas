public class StaticEnemy extends Enemy {
    public StaticEnemy(int x, int y) {
        super(x, y, 40, 40); // Memanggil konstruktor kelas induk dengan koordinat x, y, lebar 40, dan tinggi 40
    }

    @Override
    public void move() {
        // Musuh statis tidak bergerak, sehingga tidak ada implementasi di sini
    }
}
