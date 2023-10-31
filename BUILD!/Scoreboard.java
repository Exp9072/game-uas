import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Scoreboard {
    public List<Integer> scores;

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
        try (FileWriter writer = new FileWriter(filename)) { // Open the file in append mode
            if (!scores.isEmpty()) {
                for (Integer score : scores) {
                    writer.write("Player 1: " + score + System.lineSeparator());
                }
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

    public void loadScoresFromFile(String filename) {
        scores.clear(); // Clear the existing scores before loading new scores
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            System.out.println(filename);
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim(); // Remove leading and trailing whitespace
                if (!line.isEmpty() && line.startsWith("Player 1: ")) {
                    try {
                        // Parse the score from the line
                        int score = Integer.parseInt(line.substring("Player 1: ".length()));
                        scores.add(score);
                    } catch (NumberFormatException e) {
                        // Handle parsing errors, if any
                        System.err.println("Error parsing score: " + line);
                    }
                }
            }
            System.out.println("Scores after loading: " + scores); // Debug statement
            sortScores(); // Sort the scores after loading all of them
            System.out.println("Scores after sorting: " + scores); // Debug statement
        } catch (IOException e) {
            System.err.println("Error reading from the file: " + filename);
            e.printStackTrace();
        }
    }
     
}
 