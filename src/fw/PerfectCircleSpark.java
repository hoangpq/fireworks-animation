package fw;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class PerfectCircleSpark implements Spark {
	public double MAX_SPEED = 10;
	public double ACCELERATION;
	
	private final int MAX_RADIUS = 5;
	private final int MAX_DIAMETER = 2 * MAX_RADIUS;
	
	private double direction;
	private long spawnTime;
	
	private Color clrGlowInnerHi;
	private Color clrGlowInnerLo;
	private Color clrGlowOuterHi;
	private Color clrGlowOuterLo;
	
	private Ellipse2D.Double spark;
	private FireworksPanel parent;

	private long LIFESPAN;
	
	public PerfectCircleSpark(FireworksPanel parent, double direction, double x, double y, Color c, long LIFESPAN, double MAX_SPEED) {
		this.direction = direction;
		this.parent = parent;
		
		Color c2 = c.brighter().brighter();
		clrGlowInnerHi = new Color(c2.getRed(), c2.getGreen(), c2.getBlue(), 120);
		clrGlowInnerLo = c;
		clrGlowOuterHi = new Color(c2.getRed(), c2.getGreen(), c2.getBlue(), 100);
		clrGlowOuterLo = c2;
		this.LIFESPAN = LIFESPAN;
		
		this.MAX_SPEED = MAX_SPEED;
		
		this.spark = new Ellipse2D.Double(x - MAX_RADIUS, y - MAX_RADIUS, MAX_DIAMETER, MAX_DIAMETER);
		
		this.spawnTime = System.currentTimeMillis();
		
		this.ACCELERATION = - 1.0 / LIFESPAN * MAX_SPEED / 1.05;
	}
	
	private void step() {
		//current time?
		long currentTime = System.currentTimeMillis();
		long currentLifeLength = currentTime - spawnTime;
		
		//if still within life span
		if ( currentLifeLength < LIFESPAN) {
			//calculate new speed
			double currentSpeed = MAX_SPEED + ACCELERATION * currentLifeLength;
			
			//calculate movement
			double dx = currentSpeed * Math.cos(Math.toRadians(direction));
			double dy = currentSpeed * Math.sin(Math.toRadians(direction));
			
			//move spark
			spark.x += dx;
			spark.y += dy;
			
			//shrink spark
			double shrink = 1 - ((double)currentLifeLength / LIFESPAN);
			
			spark.height = MAX_DIAMETER * shrink;
			spark.width  = MAX_DIAMETER * shrink;
		} else {
			if (parent.sparksLeft() == 1) {
				parent.repaint();
			}
			parent.removeSpark(this);
		}
	}
	
	private Color getMixedColor(Color c1, float pct1, Color c2, float pct2) {
	    float[] clr1 = c1.getComponents(null);
	    float[] clr2 = c2.getComponents(null);
	    for (int i = 0; i < clr1.length; i++) {
	        clr1[i] = (clr1[i] * pct1) + (clr2[i] * pct2);
	    }
	    return new Color(clr1[0], clr1[1], clr1[2], clr1[3]);
	}
	
	public void draw(Graphics2D g2d) {
		step();

		int gw = MAX_RADIUS * 3;
		for (int i = gw; i >= 4; i -= 4) {
			float pct = (float)(gw - i) / (gw - 1);

	        Color mixHi = getMixedColor(clrGlowInnerHi, pct,
	                                    clrGlowOuterHi, 1.0f - pct);
	        Color mixLo = getMixedColor(clrGlowInnerLo, pct,
	                                    clrGlowOuterLo, 1.0f - pct);
	        g2d.setPaint(new GradientPaint(0.0f, MAX_DIAMETER*0.25f,  mixHi,
	                                      0.0f, MAX_DIAMETER, mixLo));


	        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, pct));
	        g2d.setStroke(new BasicStroke(i));
	        g2d.draw(spark);
		}
	}
}
