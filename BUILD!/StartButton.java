import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class StartButton extends JButton {
    private final JFrame frame;

    public StartButton(JFrame frame) {
        super("Start");
        this.frame = frame; // Store the JFrame instance
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle the "Start" button click
                startGame();
            }
        });
    }

    private void startGame() {
        frame.getContentPane().removeAll();
        frame.repaint();
        GalagaGame game = new GalagaGame();

        game.initializePlayerShip(frame.getWidth(), frame.getHeight());
        Random random = new Random();
        game.initializeRandomMovingEnemy(frame.getWidth(), frame.getHeight(), random);
        ArrayList<Enemy> enemies = game.getEnemies();
        game.initializeRespawn(enemies, random, frame.getWidth());

        frame.add(game);
        frame.revalidate();
        frame.repaint();
        game.requestFocusInWindow();
    }
}
