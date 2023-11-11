import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Kelas Scoreboard merepresentasikan papan skor permainan Galaga.
public class Scoreboard {
    public List<Integer> scores; // Daftar skor

    // Konstruktor untuk Scoreboard, inisialisasi ArrayList skor
    public Scoreboard() {
        scores = new ArrayList<>();
    }

    // Metode untuk mendapatkan daftar skor
    public List<Integer> getScores() {
        return scores;
    }

    // Metode untuk menambahkan poin ke daftar skor
    public void addPoints(int points) {
        scores.add(points);
    }

    // Metode untuk menyimpan skor ke file dengan nama tertentu
    public void saveScoreToFile(String filename) {
        try (FileWriter writer = new FileWriter(filename)) { // Membuka file dalam mode append
            if (!scores.isEmpty()) {
                for (Integer score : scores) {
                    writer.write("Player 1: " + score + System.lineSeparator());
                }
            }
            System.out.println("Berhasil menulis ke file: " + filename);
        } catch (IOException e) {
            System.err.println("Error menulis ke file: " + filename);
            e.printStackTrace();
        }
    }
    
    // Metode untuk mengurutkan skor secara descending dan membatasi ke 5 skor teratas
    public void sortScores() {
        Collections.sort(scores, Collections.reverseOrder()); // Mengurutkan skor secara descending
        if (scores.size() > 5) {
            scores = scores.subList(0, 5); // Membatasi ke 5 skor teratas
        }
    }

    // Metode untuk memuat skor dari file dengan nama tertentu
    public void loadScoresFromFile(String filename) {
        scores.clear(); // Menghapus skor yang ada sebelum memuat skor baru
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            System.out.println(filename);
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim(); // Menghapus spasi di awal dan akhir
                if (!line.isEmpty() && line.startsWith("Player 1: ")) {
                    try {
                        // Mendapatkan skor dari baris
                        int score = Integer.parseInt(line.substring("Player 1: ".length()));
                        scores.add(score);
                    } catch (NumberFormatException e) {
                        // Menangani kesalahan parsing, jika ada
                        System.err.println("Error parsing score: " + line);
                    }
                }
            }
            System.out.println("Skor setelah memuat: " + scores); // Pernyataan debug
            sortScores(); // Mengurutkan skor setelah memuat semuanya
            System.out.println("Skor setelah pengurutan: " + scores); // Pernyataan debug
        } catch (IOException e) {
            System.err.println("Error membaca dari file: " + filename);
            e.printStackTrace();
        }
    }
}
