import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Kelas RTOMainMenu merepresentasikan panel menu utama dalam permainan Galaga.
public class RTOMainMenu extends JPanel {
    private final JFrame frame;
    private JPanel mainPanel;

    // Konstruktor untuk RTOMainMenu, menerima JFrame dan panel menu utama sebagai parameter
    public RTOMainMenu(JFrame frame, JPanel mainPanel) {
        this.frame = frame;
        this.mainPanel = mainPanel;

        setLayout(new GridBagLayout()); // Mengatur tata letak panel menggunakan GridBagLayout
        setBackground(Color.BLACK); // Mengatur warna latar belakang panel menjadi hitam

        // Tombol untuk kembali ke menu utama
        JButton returnButton = new JButton("Kembali ke Menu Utama");
        returnButton.setFont(new Font("Arial", Font.PLAIN, 24)); // Mengatur jenis font, gaya, dan ukuran
        returnButton.setForeground(Color.WHITE); // Mengatur warna teks tombol menjadi putih
        returnButton.setBackground(Color.RED); // Mengatur warna latar tombol menjadi merah

        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                returnToMainMenu();
            }
        });

        GridBagConstraints gbc = new GridBagConstraints(); // Menggunakan GridBagConstraints untuk mengatur posisi komponen
        gbc.gridx = 0; // Menentukan kolom ke-0
        gbc.gridy = 1; // Menentukan baris ke-1
        gbc.insets = new Insets(0, 0, 300, 0); // Mengatur jarak antara komponen (top, left, bottom, right)

        add(returnButton, gbc); // Menambahkan tombol kembali ke panel dengan menggunakan GridBagConstraints
    }

    // Metode untuk kembali ke panel menu utama
    public void returnToMainMenu() {
        frame.getContentPane().removeAll(); // Menghapus semua komponen dari content pane
        frame.getContentPane().add(mainPanel); // Menambahkan panel menu utama asli
        frame.getContentPane().revalidate(); // Memvalidasi kembali hierarki komponen
        frame.getContentPane().repaint(); // Menggambar ulang frame
    }

    // Metode untuk menampilkan layar permainan berakhir
    public void showGameOverScreen() {
        // Membuat panel permainan berakhir
        JPanel gameOverPanel = new JPanel();
        gameOverPanel.setLayout(new GridBagLayout()); // Mengatur tata letak panel menggunakan GridBagLayout
        gameOverPanel.setBackground(Color.BLACK); // Mengatur warna latar belakang panel menjadi hitam
    
        // Membuat dan menyesuaikan label permainan berakhir
        JLabel gameOverLabel = new JLabel("Game Over");
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 36)); // Mengatur jenis font, gaya, dan ukuran
        gameOverLabel.setForeground(Color.RED); // Mengatur warna teks label menjadi merah
    
        // Membuat tombol untuk kembali ke menu utama
        JButton returnButton = new JButton("Kembali ke Menu Utama");
        returnButton.setFont(new Font("Arial", Font.PLAIN, 24)); // Mengatur jenis font, gaya, dan ukuran
        returnButton.setForeground(Color.WHITE); // Mengatur warna teks tombol menjadi putih
        returnButton.setBackground(Color.RED); // Mengatur warna latar tombol menjadi merah
    
        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                returnToMainMenu();
            }
        });
    
        // Menambahkan label permainan berakhir dan tombol kembali ke panel permainan berakhir
        GridBagConstraints gbc = new GridBagConstraints(); // Menggunakan GridBagConstraints untuk mengatur posisi komponen
        gbc.gridx = 0; // Menentukan kolom ke-0
        gbc.gridy = 0; // Menentukan baris ke-0
        gbc.insets = new Insets(0, 0, 0, 0); // Mengatur jarak antara komponen (top, left, bottom, right)
        gameOverPanel.add(gameOverLabel, gbc); // Menambahkan label permainan berakhir ke panel
    
        gbc.gridy = 1; // Menentukan baris ke-1
        gbc.insets = new Insets(00, 0, 0, 0); // Mengatur jarak antara komponen (top, left, bottom, right)
        gameOverPanel.add(returnButton, gbc); // Menambahkan tombol kembali ke panel
    
        // Menghapus konten frame dan menambahkan panel permainan berakhir
        frame.getContentPane().removeAll();
        frame.getContentPane().add(gameOverPanel);
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
    }
}

// jangan di ss
