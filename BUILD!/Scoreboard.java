import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Scoreboard {
    private List<Integer> scores;

    public Scoreboard() {
        scores = new ArrayList<>();
    }

    public List<Integer> getScores() {
        return scores;
    }

    public void addPoints(int points) {
        scores.add(points);
    }

    public void saveScoreToFile(String filename) {
        try (FileWriter writer = new FileWriter(filename, true)) { // Open the file in append mode
            for (Integer score : scores) {
                writer.write("Player 1: " + score + System.lineSeparator());
                sortScores();
                break;
            }
            System.out.println("Successfully wrote to the file: " + filename);
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + filename);
            e.printStackTrace();
        }
    }

    public void sortScores() {
        Collections.sort(scores, Collections.reverseOrder()); // Sort scores in descending order
        if (scores.size() > 5) {
            scores = scores.subList(0, 5); // Limit to the top 5 scores
        }
    }
}
 