package fw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JPanel;


public class FireworksPanel extends JPanel implements MouseListener, MouseMotionListener {
	private static final long serialVersionUID = 1266778429484392409L;
	
	private LinkedList<Spark> sparks = new LinkedList<Spark>();
	
	private final Dimension MAX_DIMENSION = new Dimension(800, 800);
	
	private Random generator = new Random();
	
	public FireworksPanel() {
		super();
		
		this.setPreferredSize(MAX_DIMENSION);
		this.setMinimumSize(MAX_DIMENSION);
		this.setMaximumSize(MAX_DIMENSION);
		
		this.setLayout(null);
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}
	
	public int sparksLeft() {
		return sparks.size();
	}
	
	public boolean removeSpark(Spark s) {
		return this.sparks.remove(s);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.BLACK);
		Rectangle clip = g.getClip().getBounds();
		g.fillRect(0, 0, clip.width, clip.height);
		
		Graphics2D g2d = (Graphics2D)g;
		
		Spark array[] = sparks.toArray(new Spark[0]);
		
		for(Spark s : array) {
			s.draw(g2d);
		}
	}

	private void explode(int x, int y) {
		int sparkCount = 50 + generator.nextInt(20);
		Color c = new Color(generator.nextInt(255), generator.nextInt(255), generator.nextInt(255));
		long lifespan = 1000 + generator.nextInt(1000);
		
		int choice = generator.nextInt(100);

		if (choice < 18) {
			createCircleSpark(x, y, sparkCount, c, lifespan);
		} else if (choice < 36) {
			createPerfectCircleSpark(x, y, sparkCount, c, lifespan);
		} else if (choice < 54) {
			createMovingSpark(x, y, sparkCount, c, lifespan);
		} else if (choice < 72) {
			createBubbleSpark(x, y, sparkCount, c, lifespan);
		} else if (choice < 85) {
			createTrigSpark(x, y, sparkCount, c, lifespan);
		} else {
			createGiantSpark(x, y, sparkCount, c, lifespan);
		}
	}
	
	private void createCircleSpark(int x, int y, int sparkCount, Color c, long lifespan) {
		for (int i = 0; i < sparkCount; i++) {
			double direction = 360 * generator.nextDouble();
			double speed = 10 * generator.nextDouble() + 5;
			sparks.addLast(new CircleSpark(this, direction, x, y, c, lifespan, speed));
		}
	}
	
	private void createPerfectCircleSpark(int x, int y, int sparkCount, Color c, long lifespan) {
		sparkCount *= 2;
		
		lifespan /= 2;
		
		double speed = 20 * generator.nextDouble() + 5;
		
		for (int i = 0; i < sparkCount; i++) {
			double direction = 360 * generator.nextDouble();
			sparks.addLast(new PerfectCircleSpark(this, direction, x, y, c, lifespan, speed));
		}
	}
	
	private void createGiantSpark(int x, int y, int sparkCount, Color c, long lifespan) {
		for (int i = 0; i < sparkCount; i++) {
			double direction = 360 * generator.nextDouble();
			double speed = 10 * generator.nextDouble() + 5;
			sparks.addLast(new GiantSpark(this, direction, x, y, c, lifespan, speed));
		}
	}
	
	private void createTrigSpark(int x, int y, int sparkCount, Color c, long lifespan) {
		for (int i = 0; i < sparkCount; i++) {
			double direction = 360 * generator.nextDouble();
			double speed = 10 * generator.nextDouble() + 5;
			sparks.addLast(new TrigSpark(this, direction, x, y, c, lifespan, speed));
		}
	}
	
	private void createMovingSpark(int x, int y, int sparkCount, Color c, long lifespan) {
		for (int i = 0; i < sparkCount; i++) {
			double direction = 360 * generator.nextDouble();
			double speed = 10 * generator.nextDouble() + 5;
			sparks.addLast(new MovingSpark(this, direction, x, y, c, lifespan, speed));
		}
	}
	
	private void createBubbleSpark(int x, int y, int sparkCount, Color c, long lifespan) {
		for (int i = 0; i < sparkCount; i++) {
			double direction = 360 * generator.nextDouble();
			double speed = 10 * generator.nextDouble() + 5;
			sparks.addLast(new BubbleSpark(this, direction, x, y, c, lifespan, speed));
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		explode(e.getX(), e.getY());
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {
		//System.out.println("POO");
	}
}
