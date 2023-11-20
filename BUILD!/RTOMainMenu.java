import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.Graphics;


// Kelas RTOMainMenu merepresentasikan panel menu utama dalam permainan Galaga.
public class RTOMainMenu extends JPanel {
    private final JFrame frame;
    private JPanel mainPanel;
    private JPanel panel;
    private static Clip Death;



    // Konstruktor untuk RTOMainMenu, menerima JFrame dan panel menu utama sebagai parameter
    public RTOMainMenu(JFrame frame, JPanel mainPanel, JPanel panel, CardLayout cardLayout) {
        this.frame = frame;
        this.mainPanel = mainPanel;
        this.panel = panel;

        frame.remove(panel);
        frame.remove(mainPanel);
        


        setLayout(new GridBagLayout()); // Mengatur tata letak panel menggunakan GridBagLayout
        setBackground(Color.BLACK); // Mengatur warna latar belakang panel menjadi hitam

        // Tombol untuk kembali ke menu utama
        JButton returnButton = new JButton("Kembali ke Menu Utama");
        //returnButton.setFont(new Font("Arial", Font.PLAIN, 24)); // Mengatur jenis font, gaya, dan ukuran
        //returnButton.setForeground(Color.WHITE); // Mengatur warna teks tombol menjadi putih
        //returnButton.setBackground(Color.RED); // Mengatur warna latar tombol menjadi merah


        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                returnToMainMenu();
                //System.out.println("RTO atas");
            }
        });
        
        GridBagConstraints gbc = new GridBagConstraints(); // Menggunakan GridBagConstraints untuk mengatur posisi komponen

        gbc.gridx = 0; // Menentukan kolom ke-0
        gbc.gridy = 0; // Menentukan baris ke-1
        gbc.weightx = 1.0; // Make the button expand horizontally
        gbc.weighty = 1.0; // Make the button expand vertically
        gbc.insets.set(1000, 0, 0, 0); // Mengatur jarak antara komponen (top, left, bottom, right)
        //gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.CENTER;
  
        add(returnButton, gbc); // Menambahkan tombol kembali ke panel dengan menggunakan GridBagConstraints
    }
    
    // Metode untuk kembali ke panel menu utama
    public void returnToMainMenu() {
        frame.getContentPane().removeAll();
        SoundMain.stopDeathSound();
        // Initialize the background music
        SoundMain.playBackgroundMusic();
        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
        //System.out.println("atas");
        cardLayout.show(mainPanel, "mainMenu");
        
        panel.add(mainPanel, BorderLayout.CENTER);
        frame.add(panel);
        frame.getContentPane().add(mainPanel); // Menambahkan panel menu utama asli
        frame.getContentPane().revalidate(); // Memvalidasi kembali hierarki komponen
        frame.setResizable(false);
        frame.getContentPane().setVisible(true);
        frame.getContentPane().repaint(); // Menggambar ulang frame
        panel.revalidate();
        panel.setVisible(true);
        panel.repaint();
        //System.out.println("bawah");
    }
    
    // Metode untuk menampilkan layar permainan berakhir
    public void showGameOverScreen() {
        // Membuat panel permainan berakhir
        SoundMain.stopStartSound();

        SoundMain.playDeathSound();

        ImageIcon ll = new ImageIcon("./BbitYouDied.png");
        Image BgDeath; 
        BgDeath = ll.getImage(); 
        JPanel gameOverPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g ){
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.drawImage(BgDeath, 0, 0, 1707, 1067, this);
            }
        };
        gameOverPanel.setLayout(new GridBagLayout()); // Mengatur tata letak panel menggunakan GridBagLayout
        //gameOverPanel.setBackground(Color.BLACK); // Mengatur warna latar belakang panel menjadi hitam
        
        // Membuat dan menyesuaikan label permainan berakhir
        //JLabel gameOverLabel = new JLabel("Game Over");
        //gameOverLabel.setFont(new Font("Arial", Font.BOLD, 36)); // Mengatur jenis font, gaya, dan ukuran
        //gameOverLabel.setForeground(Color.RED); // Mengatur warna teks label menjadi merah
        
        // Membuat tombol untuk kembali ke menu utama
        JButton returnButton = new JButton("");
        //returnButton.setFont(new Font("Arial", Font.PLAIN, 24)); // Mengatur jenis font, gaya, dan ukuran
        //returnButton.setForeground(Color.WHITE); // Mengatur warna teks tombol menjadi putih
        //returnButton.setBackground(Color.RED); // Mengatur warna latar tombol menjadi merah
        
        URL imageUrl = getClass().getResource("./BMainMenuButtonRed.png");
        ImageIcon ii = new ImageIcon(imageUrl);
        Image scaledImage = ii.getImage().getScaledInstance(205, 55, Image.SCALE_SMOOTH);
        
        returnButton.setIcon(new ImageIcon(scaledImage));
        returnButton.setPreferredSize(new Dimension(200, 50));
        returnButton.setHorizontalTextPosition(JButton.CENTER);
        returnButton.setVerticalTextPosition(JButton.CENTER);

        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //frame.removeAll(); // Menghapus semua komponen dari content pane
                //gameOverPanel.removeAll();
                //gameOverLabel.removeAll();
                //gameOverPanel.revalidate();
                //gameOverPanel.repaint();
                //System.out.println("RTO bawah");
                returnToMainMenu();
            }
        });
    
        // Menambahkan label permainan berakhir dan tombol kembali ke panel permainan berakhir
        GridBagConstraints gbc = new GridBagConstraints(); // Menggunakan GridBagConstraints untuk mengatur posisi komponen
        //gbc.gridx = 0; // Menentukan kolom ke-0
        //gbc.gridy = 0; // Menentukan baris ke-1
        //gbc.weightx = 1.0; // Make the button expand horizontally
        //gbc.weighty = 1.0; // Make the button expand vertically
        //gbc.insets = new Insets(1000, 0, 0, 0); // Mengatur jarak antara komponen (top, left, bottom, right)
        //gbc.fill = GridBagConstraints.CENTER;
        //gbc.anchor = GridBagConstraints.CENTER;
        //System.out.println("gbc = "+gbc);
        gameOverPanel.add(returnButton, gbc); // Menambahkan tombol kembali ke panel
    
        // Menghapus konten frame dan menambahkan panel permainan berakhir
        frame.getContentPane().removeAll();
        frame.getContentPane().add(gameOverPanel);
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
    }
}

// jangan di ss
