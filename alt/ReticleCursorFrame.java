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
import java.util.Random; 
import java.util.List; 
import java.util.ArrayList; 
import java.io.File;


public class ReticleCursorFrame extends JFrame {
	BufferedImage[][] enemyImages = new BufferedImage[3][3];
	private List<Enemy> enemies = new ArrayList<>();
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
		
		loadEnemyImages(); 
		spawnEnemies(); 
		
		Timer cursorTimer = new Timer(50, e -> updateCursorColor());
		cursorTimer.start(); 
		
		Timer repaintTimer = new Timer(100, e -> repaint());
		repaintTimer.start();
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
	@Override 
		public void paint(Graphics g){
			super.paint(g);
			for (Enemy e : enemies){
			BufferedImage img = enemyImages[e.getType()][e.getCurrentFrame()];
			g.drawImage(img, e.getX(), e.getY(), null);
			e.updateAnimation();
			}
		}
		public void spawnEnemies(){
		Random rand = new Random();
		for (int type = 0; type < 3; type++){
			for (int i= 0; i <3; i++){
			int x = rand.nextInt(FRAME_WIDTH - 50);
			int y = rand.nextInt(FRAME_HEIGHT - 50);
			enemies.add(new Enemy(type, x, y, 3));
			}
		}
	}
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ReticleCursorFrame frame = new ReticleCursorFrame();
            frame.setVisible(true);
            frame.requestFocusInWindow();// ensure key input works
        });
    }
}
