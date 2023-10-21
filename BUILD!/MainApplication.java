import javax.swing.*;
import java.awt.*;

public class MainApplication {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Galaga Game");
        GalagaGame game = new GalagaGame(); // Create an instance of GalagaGame
        frame.add(game);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.getContentPane().setPreferredSize(new Dimension(850, 600));
        frame.pack();

        game.initializePlayerShip(frame.getWidth());
    }
}
