import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

// Kelas ExitButton merupakan JButton khusus untuk keluar dari aplikasi.
public class ExitButton extends JButton {
    // Konstruktor ExitButton, mengatur teks tombol dan menambahkan ActionListener.
    public ExitButton() {
        super("Exit"); // Mengatur teks tombol menjadi "Exit"
        //2023-11-17_21.41.02.png 2023-11-17_21.44.15.png
        URL imageUrl = getClass().getResource("./2023-11-17_21.44.15.png");
        ImageIcon ii = new ImageIcon(imageUrl);
        Image scaledImage = ii.getImage().getScaledInstance(270, 115, Image.SCALE_SMOOTH);
        setIcon(new ImageIcon(scaledImage));
        
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
