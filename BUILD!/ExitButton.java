import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Kelas ExitButton merupakan JButton khusus untuk keluar dari aplikasi.
public class ExitButton extends JButton {
    // Konstruktor ExitButton, mengatur teks tombol dan menambahkan ActionListener.
    public ExitButton() {
        super("Exit"); // Mengatur teks tombol menjadi "Exit"
        
        // Menambahkan ActionListener untuk menanggapi klik tombol exit.
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Menutup aplikasi
            }
        });
    }
}

// jangan di ss
