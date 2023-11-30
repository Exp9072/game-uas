import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.awt.Dimension;

// Kelas ExitButton merupakan JButton khusus untuk keluar dari aplikasi.
public class ExitButton extends JButton {
    // Konstruktor ExitButton, mengatur teks tombol dan menambahkan ActionListener.
    public ExitButton() {
        super(""); // Mengatur teks tombol menjadi "Exit"
        //2023-11-17_21.41.02.png 2023-11-17_21.44.15.png
        URL imageUrl = ExitButton.class.getClassLoader().getResource("BExitButton.png");
        ImageIcon ii = new ImageIcon(imageUrl);
        Image scaledImage = ii.getImage().getScaledInstance(205, 55, Image.SCALE_SMOOTH);
        setIcon(new ImageIcon(scaledImage));

        setPreferredSize(new Dimension(50, 50));
        setHorizontalTextPosition(JButton.CENTER);
        setVerticalTextPosition(JButton.CENTER);
        
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
