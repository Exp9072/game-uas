import javax.swing.JFrame;
import java.awt.Dimension;

public class MainApplication {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Galaga Game"); // Membuat instance JFrame dengan judul "Galaga Game"
        GalagaGame game = new GalagaGame(); // Membuat instance GalagaGame
        frame.add(game); // Menambahkan instance GalagaGame ke dalam frame
        frame.setSize(700, 1035); // Mengatur ukuran frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Mengatur aksi penutupan frame
        frame.setVisible(true); // Menampilkan frame
        frame.getContentPane().setPreferredSize(new Dimension(700, 1035)); // Mengatur dimensi konten frame
        frame.pack(); // Paksa frame untuk mengikuti ukuran kontennya

        game.initializePlayerShip(frame.getWidth(), frame.getHeight()); // Memanggil metode untuk menginisialisasi kapal pemain
    }
}
 

// 700 1035 (16:10)
// 700 925 (16:9)

/* 
1. Pengantar Java OOP | Y
2. Kerangka Program OOP | Y                          
3. Dasar Pemrograman Java | Y
4. Struktur Kontrol | Y
5. Array | Y
6. Method | Y
7. Encapsulation | Y
8. Inheritance | Y
9. Polimorfisme | Y
! 10. Static & Final Variable | N (Total Yang Nembak)
! 11. Exception Handling | N
! 12. Abstract Class & Interface | N
13. Java Collection | Y
14. GUI | Y
*/

//864 lines