// 1-31-2026
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent; 
import javax.imageio.ImageIO;  
import java.io.IOException; 

BufferedImage[][] enemyImages = new BufferedImage[3][3];

public class ReticleCursorFrame extends JFrame {
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 800;
    private float hue = 0.0f; // Starting hue
    private int cursorMode = 0;

    public ReticleCursorFrame() {
        setTitle("Reticle Color Cursor Frame");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBackground(Color.BLACK);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                    cursorMode++;
                    if (cursorMode > 2) cursorMode = 0;
                    updateCursorColor();
                }
            }
        });

        setFocusable(true);
        requestFocusInWindow();

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

        g2d.setColor(Color.getHSBColor(hue, 1.0f, 1.0f));
        g2d.setStroke(new BasicStroke(2f));

        if (cursorMode == 0) {
            g2d.drawLine(0, 8, 4, 8);
            g2d.drawLine(11, 8, 15, 8);
			g2d.drawLine(8,0,8,4);
			g2d.drawLine(8,11,8,15);
        } else if (cursorMode == 1) {
            g2d.drawLine(0, 0, 6, 6);
			g2d.drawLine(9, 9, 15, 15);
			g2d.drawLine(15,0,9,6);
			g2d.drawLine(6,9,0,15);
        } else if (cursorMode == 2) {
            g2d.drawLine(0, 0, 6, 6);
            g2d.drawLine(15, 0, 9, 6);
            g2d.drawLine(8, 9, 8, 15);
        }

        g2d.dispose();
        return image;
    }
	public void loadEnemyImages() {
		try {
			for (int type = 0; type < 3; type++){
				for (int frame = 0; frame <3; frame++){
					enemyImages[type][frame] = ImageIO.read(
					new File("enemy" + type + "_" + frame + ".png")
				);
				}
			}
		}catch (IOException e){
		e.printStackTrace();
		}
	}
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ReticleCursorFrame frame = new ReticleCursorFrame();
			frame.loadEnemyImages();
            frame.setVisible(true);
            frame.requestFocusInWindow();// ensure key input works
        });
    }
}
