import javax.swing.JButton;
import java.awt.Font;

public class GearButton extends JButton {
    public GearButton() {
        super("@"); // Set the button label to "@"

        // You can customize the button's appearance here
        Font buttonFont = new Font("Arial", Font.BOLD, 20);
        setFont(buttonFont);
    }
}

