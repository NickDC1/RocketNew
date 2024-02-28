import javax.swing.JFrame;
import java.awt.EventQueue;

public class GameLauncher {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame("Asteroid Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            GamePanel gamePanel = new GamePanel();  // Your game panel
            frame.add(gamePanel);
            frame.setSize(800, 600);  // Set your preferred size
            frame.setResizable(false);
            frame.setVisible(true);
        });
    }
}
