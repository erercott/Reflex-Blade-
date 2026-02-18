 
// 1-31-2026
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class ReticleCursorFrame extends JFrame {
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 800;
    private float hue = 0.0f; // Starting hue

    public ReticleCursorFrame() {
        setTitle("Reticle Color Cursor Frame");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBackground(Color.WHITE);

        // Start a timer to change the hue
        Timer timer = new Timer(50, new ActionListener() { // Faster color change
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCursorColor();
            }
        });
        timer.start();
    }

    private void updateCursorColor() {
        hue += 0.05f; // Increase speed of color rotation
        if (hue > 1.0f) {
            hue = 0.0f; // Reset hue to cycle again
        }
        Cursor newCursor = Toolkit.getDefaultToolkit().createCustomCursor(
            createReticleCursorImage(hue),
            new Point(8, 8), // Center of the cursor
            "cursor"
        );
        setCursor(newCursor);
    }

    private Image createReticleCursorImage(float hue) {
        BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        // Draw reticle lines
        g2d.setColor(Color.getHSBColor(hue, 1.0f, 1.0f));
        g2d.setStroke(new BasicStroke(2f));

        // Horizontal line
        g2d.drawLine(0, 8, 16, 8);
        // Vertical line
        g2d.drawLine(8, 0, 8, 16);
        
        g2d.dispose();
        return image;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ReticleCursorFrame frame = new ReticleCursorFrame();
            frame.setVisible(true);
        });
    }
}
