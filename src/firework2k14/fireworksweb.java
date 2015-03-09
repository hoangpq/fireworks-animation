package firework2k14;
// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 13/01/2014 11:17:04 CH
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   fireworksweb.java

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.image.MemoryImageSource;
import java.util.Random;

public class fireworksweb extends Applet
    implements Runnable
{

    public fireworksweb()
    {
        first = true;
    }

    public void init()
    {
        //width = getSize().width;
        //height = getSize().height;
        width = 400;
        height = 400;
    	size = width * height;
        //fwsound = getAudioClip(getDocumentBase(), "launch.au");
        //fwsound3 = getAudioClip(getDocumentBase(), "firework.au");
        //fwsound4 = getAudioClip(getDocumentBase(), "firework2.au");
        pixels = new int[size];
        pixels2 = new int[size];
        source = new MemoryImageSource(width, height, pixels, 0, width);
        source.setAnimated(true);
        source.setFullBufferUpdates(true);
        image = createImage(source);
        offImage = createImage(width, height);
        offGraphics = offImage.getGraphics();
        offGraphics.setFont(new Font("Arial", 1, 28));
        offGraphics.setColor(Color.blue);
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
        pixels[0] = 0xff000000;
        pixels2[0] = 0xff000000;
        for(int j = 1; j < size; j += j)
        {
            System.arraycopy(pixels, 0, pixels, j, size - j >= j ? j : size - j);
            System.arraycopy(pixels2, 0, pixels2, j, size - j >= j ? j : size - j);
        }

        //String s = getParameter("fps");
        // fps
        String s = "30";
        try
        {
            if(s != null)
                fps = Integer.parseInt(s);
        }
        catch(Exception exception) { }
        delay = fps <= 0 ? 100 : 1000 / fps;
    }

    public void start()
    {
        if(animatorThread == null)
        {
            animatorThread = new Thread(this);
            Thread _tmp = animatorThread;
            animatorThread.setPriority(1);
            animatorThread.start();
        }
    }

    public void stop()
    {
        animatorThread = null;
    }

    public void run()
    {
        long l = System.currentTimeMillis();
        do
        {
            if(Thread.currentThread() != animatorThread)
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
            secondtick--;
            NewFrame();
            source.newPixels();
            offGraphics.drawImage(image, 0, 0, width, height, null);
            repaint();
            try
            {
                l += delay;
                Thread.sleep(Math.max(0L, l - System.currentTimeMillis()));
                continue;
            }
            catch(InterruptedException interruptedexception) { }
            break;
        } while(true);
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
                        int j3 = 15 + (int)(rand.nextDouble() * 10D);
                        for(int l3 = j; l3 < j + fireworkflares; l3++)
                        {
                            fx[l3] = d3;
                            fy[l3] = d4;
                            double d1 = (6.2800000000000002D * (double)l3) / (double)fireworkflares;
                            fdx[l3] = Math.sin(d1) * 1.5D;
                            fdy[l3] = Math.cos(d1) * 1.5D;
                            fc[l3] = i3;
                            tick[l3] = j3;
                        }

                    } else
                    {
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

    public final synchronized void update(Graphics g)
    {
        g.drawImage(offImage, 0, 0, this);
    }

    public void paint(Graphics g)
    {
        update(g);
    }

    boolean first;
    int width;
    int height;
    int size;
    int pixels[];
    int pixels2[];
    int delay;
    int fps;
    int secondtick;
    MemoryImageSource source;
    Image image;
    Image offImage;
    Graphics offGraphics;
    Graphics2D g2;
    Thread animatorThread;
    byte fireworks;
    byte maxfireworks;
    byte fireworkflares;
    double fx[];
    double fy[];
    double fdx[];
    double fdy[];
    int tick[];
    int fc[];
    int fey[];
    byte fwstat[];
    AudioClip fwsound;
    AudioClip fwsound3;
    AudioClip fwsound4;
    Random rand;
}
