package firework2k14;

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.util.Random;

import javax.swing.JPanel;

public class fwpanel extends JPanel implements Runnable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int width, height;
	boolean first;
    int size, pixels[], pixels2[], delay, fps, secondtick;
    MemoryImageSource source;
    Image image;
    BufferedImage offImage = null;
    Graphics2D offGraphics, g2;
    Thread animatorThread;
    byte fireworks, maxfireworks, fireworkflares, fwstat[];
    double fx[], fy[], fdx[], fdy[];
    int tick[], fc[], fey[];
    //AudioClip fwsound, fwsound3, fwsound4;
    fwsound fwfire, fwexplosion, fwfly;
    Random rand;
	public fwpanel(int w, int h) {
		this.width = w;
		this.height = h;
		this.first = true;
		this.fwfire = new fwsound("sounds/fwfire.mp3");
		this.fwfly = new fwsound("sounds/fwfly.mp3");
		this.fwexplosion = new fwsound("sounds/fwexplosion.mp3");
		fwfire.start();
		size = width * height;
        pixels = new int[size];
        pixels2 = new int[size];
        source = new MemoryImageSource(width, height, pixels, 0, width);
        source.setAnimated(true);
        source.setFullBufferUpdates(true);
        image = createImage(source);
        if ( offImage == null ) {
        	offImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        	offGraphics = (Graphics2D)offImage.getGraphics();
        }
        offGraphics.setFont(new Font("Arial", 1, 28));
        offGraphics.setColor(Color.RED);
        g2 = (Graphics2D)offGraphics;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        fps = 25;
        fireworks = 0;
        maxfireworks = 10;
        fireworkflares = 50;
        int i = maxfireworks * fireworkflares;
        fx = new double[i];
        fy = new double[i];
        fey = new int[maxfireworks];
        fdx = new double[i];
        fdy = new double[i];
        fwstat = new byte[maxfireworks];
        tick = new int[i];
        fc = new int[i];
        rand = new Random();
        pixels[0] = 0xffff0000;
        pixels2[0] = 0xffff0000;
        for(int j = 1; j < size; j += j)
        {
            System.arraycopy(pixels, 0, pixels, j, size - j >= j ? j : size - j);
            System.arraycopy(pixels2, 0, pixels2, j, size - j >= j ? j : size - j);
        }
        delay = fps <= 0 ? 100 : 1000 / fps;
		Thread th = new Thread(this);
		th.start();
	}
	public void run() {
		long l = System.currentTimeMillis();
        while(true) {
        	System.out.println("repaint");
            /*if(Thread.currentThread() != animatorThread)
                break;
            if(secondtick == 0)
            {
                if(rand.nextDouble() > 0.20000000000000001D)
                {
                    int i = (int)(rand.nextDouble() * (double)(width - 100)) + 50;
                    int j = (int)(rand.nextDouble() * (double)(height - 110)) + 10;
                    addFirework(i, j);
                }
                secondtick = 10;
            }
            secondtick--;*/
            int i = (int)(rand.nextDouble() * (double)(width - 100)) + 50;
            int j = (int)(rand.nextDouble() * (double)(height - 110)) + 10;
            addFirework(i, j);
            NewFrame();
            source.newPixels();
            offGraphics.drawImage(image, 0, 0, width, height, null);
            repaint();
            try {
            	Thread.sleep(30);
            } catch ( Exception e ) { e.printStackTrace(); } 
            /*try
            {
                l += delay;
                Thread.sleep(Math.max(0L, l - System.currentTimeMillis()));
                continue;
            }
            catch(InterruptedException interruptedexception) { }
            break;*/
        } 
	}
	private void addFirework(int i, int j)
    {
        int k;
        for(k = 0; k < maxfireworks && fwstat[k] != 0; k++);
        if(k < maxfireworks)
        {
            int l = k * fireworkflares;
            fx[l] = i;
            fy[l] = height - 10;
            fdy[l] = -5D;
            fdx[l] = rand.nextDouble() - 0.5D;
            fey[k] = j;
            fwstat[k] = 1;
            if(rand.nextDouble() > 0.80000000000000004D){
                //fwsound.play();
            	//fwfire.start();
            }
        }
    }

    synchronized void NewFrame()
    {
        for(int i = width + 1; i < size - width - 1; i++)
        {
            int l = (((pixels[i - width] & 0xff0000) >> 16) + ((pixels[i - 1] & 0xff0000) >> 16) + ((pixels[i + 1] & 0xff0000) >> 16) + ((pixels[i + width] & 0xff0000) >> 16)) / 16;
            int j1 = (((pixels[i - width] & 0xff00) >> 8) + ((pixels[i - 1] & 0xff00) >> 8) + ((pixels[i + 1] & 0xff00) >> 8) + ((pixels[i + width] & 0xff00) >> 8)) / 16;
            int l1 = ((pixels[i - width] & 0xff) + (pixels[i - 1] & 0xff) + (pixels[i + 1] & 0xff) + (pixels[i + width] & 0xff)) / 16;
            l += (pixels2[i] & 0xff0000) >> 16;
            j1 += (pixels2[i] & 0xff00) >> 8;
            l1 += pixels2[i] & 0xff;
            if(l > 0)
                l = (int)((double)l - (double)l * 0.20000000000000001D);
            if(j1 > 0)
                j1 = (int)((double)j1 - (double)j1 * 0.20000000000000001D);
            if(l1 > 0)
                l1 = (int)((double)l1 - (double)l1 * 0.20000000000000001D);
            int j2 = 0xff000000 | l << 16 | j1 << 8 | l1;
            pixels2[i] = j2;
        }

        System.arraycopy(pixels2, 0, pixels, 0, size);
        for(int k2 = 0; k2 < maxfireworks; k2++)
        {
            if(fwstat[k2] == 1)
            {
                int j = k2 * fireworkflares;
                setPixel((int)fx[j], (int)fy[j], -1);
                setPixel2((int)fx[j], (int)fy[j], 0xff808080);
                setPixel2((int)fx[j], (int)fy[j] + 1, 0xff808080);
                setPixel2((int)fx[j], (int)fy[j] + 2, 0xff808080);
                fy[j] += fdy[j];
                fx[j] += fdx[j];
                if(fy[j] < (double)fey[k2])
                {
                    double d3 = fx[j];
                    double d4 = fy[j];
                    int i1 = (int)(rand.nextDouble() * 206D) + 50;
                    int k1 = (int)(rand.nextDouble() * 206D) + 50;
                    int i2 = (int)(rand.nextDouble() * 206D) + 50;
                    int i3 = 0xff000000 | i1 << 16 | k1 << 8 | i2;
                    if(rand.nextDouble() > 0.69999999999999996D)
                    {
                    	// oval
                        /*int j3 = 15 + (int)(rand.nextDouble() * 10D);
                        for(int l3 = j; l3 < j + fireworkflares; l3++)
                        {
                            fx[l3] = d3;
                            fy[l3] = d4;
                            double d1 = (6.2800000000000002D * (double)l3) / (double)fireworkflares;
                            fdx[l3] = Math.sin(d1) * 1.5D;
                            fdy[l3] = Math.cos(d1) * 1.5D;
                            fc[l3] = i3;
                            tick[l3] = j3;
                        }*/

                    } else
                    {
                    	// random direction
                        for(int k3 = j; k3 < j + fireworkflares; k3++)
                        {
                            fx[k3] = d3;
                            fy[k3] = d4;
                            double d2 = rand.nextDouble() * 6.2800000000000002D;
                            double d = rand.nextDouble() * 2D;
                            fdx[k3] = Math.sin(d2) * d;
                            fdy[k3] = Math.cos(d2) * d;
                            fc[k3] = i3;
                            tick[k3] = 40 + (int)(rand.nextDouble() * 30D);
                        }

                    }
                    if(rand.nextDouble() > 0.5D) {
                        //fwsound3.play();
                    	}
                    else {
                        //fwsound4.play();
                    	}
                    fwstat[k2] = 2;
                }
            }
            if(fwstat[k2] != 2)
                continue;
            boolean flag = false;
            for(int l2 = 0; l2 < fireworkflares; l2++)
            {
                int k = k2 * fireworkflares + l2;
                if(tick[k] <= 0)
                    continue;
                flag = true;
                if(fx[k] > 0.0D && fx[k] < (double)(width - 2) && fy[k] > 1.0D && fy[k] < (double)(height - 3))
                {
                    setPixel((int)fx[k], (int)fy[k], -1);
                    setPixel2((int)fx[k], (int)fy[k], fc[k]);
                }
                fx[k] += fdx[k];
                fy[k] += fdy[k];
                if(fdy[k] < 1.0D)
                    fdy[k] += 0.01D;
                tick[k]--;
            }

            if(!flag)
                fwstat[k2] = 0;
        }

    }

    private void setPixel(int i, int j, int k)
    {
        int l = j * width + i;
        pixels[l] = k;
    }

    private void setPixel2(int i, int j, int k)
    {
        int l = j * width + i;
        pixels2[l] = k;
    }
	public void paintComponent(Graphics g) {
		g.clearRect(0, 0, width, height);
		g.drawImage(offImage, 0, 0, this);
	}
}
