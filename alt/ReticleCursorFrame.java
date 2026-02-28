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
	private ArrayList<BufferedImage> enemyImages = new ArrayList<>();
	private ArrayList<Enemy> enemies = new ArrayList<>();
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
		try {
		loadEnemyImages(); 
		} catch (IOException e){
			e.printStackTrace();
			//optional message 
			JOptionPane.showMessageDialog(null, "Failed to load enemy images");
			System.exit(1);
		}
		spawnEnemies(); 
		GamePanel gamePanel = new GamePanel(); 
		setContentPane(gamePanel);
		
		
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
	public void loadEnemyImages() throws IOException {
		enemyImages.add(ImageIO.read(new File("../img/enemy.png")));
		enemyImages.add(ImageIO.read(new File("../img/enemy1.png")));
		enemyImages.add(ImageIO.read(new File("../img/enemy3.png")));
		enemyImages.add(ImageIO.read(new File("../img/enemy4.png")));
		enemyImages.add(ImageIO.read(new File("../img/enemy5.png")));
		enemyImages.add(ImageIO.read(new File("../img/enemy6.png")));
		enemyImages.add(ImageIO.read(new File("../img/enemy7.png")));
		enemyImages.add(ImageIO.read(new File("../img/enemy8.png")));
		enemyImages.add(ImageIO.read(new File("../img/enemy9.png")));
	}
	class GamePanel extends JPanel {
	@Override 
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			for (Enemy e : enemies) {
				g.drawImage(enemyImages.get(e.getType()), e.getX(), e.getY(), null);
			}
			
		}
	}
		public void spawnEnemies(){
			Random rand = new Random();
			for (int i= 0; i < 9; i++){
			int type = rand.nextInt(enemyImages.size());
			int x = rand.nextInt(FRAME_WIDTH - 50);
			int y = rand.nextInt(FRAME_HEIGHT - 50);
			enemies.add(new Enemy(type, x, y, 3));
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
