import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class StartButton extends JButton {
    private final JFrame frame;
    private final GalagaGame game;
    private Random random; // Declare the Random object

    public StartButton(JFrame frame, GalagaGame game) {
        super("Start");
        this.frame = frame;
        this.game = game;
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
    }

    private void startGame() {
        frame.getContentPane().removeAll();
        frame.repaint();

        
        // Initialize the Random object and handle exceptions
        
        game.initializePlayerShip(frame.getWidth(), frame.getHeight());
        random = new Random();
        game.initializeRandomMovingEnemy(frame.getWidth(), frame.getHeight(), random);
        ArrayList<Enemy> enemies = game.getEnemies();
        game.initializeRespawn(enemies, random, frame.getWidth());

        frame.add(game);
        frame.revalidate();
        frame.repaint();
        game.requestFocusInWindow();
    }

}
