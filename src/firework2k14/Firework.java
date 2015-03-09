package firework2k14;

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.image.DirectColorModel;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.util.Random;
import java.util.StringTokenizer;

import javax.swing.JPanel;

class Firework extends JPanel implements Runnable{
	private static final int MAX = 1024;
    private static final int MAG = 2;
    private static final int OX = 1;
    private static final int OY = 1;
    private static final int ANGLE = 1024;
    private static final Random rnd = new Random();
    private int umax;
    private int tmax;
    private int mwait;
    private int ctime;
    private boolean isMove;
    private boolean isPressed;
    private boolean isCount;
    private boolean isBlur;
    private boolean isGush;
    private AudioClip ban;
    private AudioClip hyu;
    private Dimension msize;
    private Message mtext[];
    private int fontSize;
    private long lastTime;
    private Thread th;
    private int w;
    private int h;
    private Image img;
    private static final Message MCOUNT[] = getMessages("1|2|3", 36, "TimesRoman");
    private static final int CPAL[][] = {
        {
            255, 255, 255
        }, {
            255, 255, 100
        }, {
            148, 248, 198
        }, {
            20, 255, 255
        }, {
            255, 128, 192
        }, {
            255, 235, 165
        }, {
            255, 128, 255
        }, {
            128, 158, 255
        }, {
            255, 128, 0
        }, {
            133, 222, 20
        }
    };
    private static final float SIN[];
    private static final float COS[];

    static 
    {
        SIN = new float[1024];
        COS = new float[1024];
        for(int i = 0; i < 1024; i++)
        {
            double d = (3.1415926535897931D * (double)i * 2D) / 1024D;
            SIN[i] = (float)Math.sin(d);
            COS[i] = (float)Math.cos(d);
        }

    }
	public Firework() {
		isMove = false;
        isPressed = false;
        isCount = false;
        isBlur = true;
        isGush = true;
        th = null;
        img = null;
		String s = "20";
        String s1 = "300";
        String s2 = "80";
        String s3 = "8000";
        String s4 = "300";
        String s5 = "ON";
        String s6 = "ON";
        String s7 = "300 Shots|Right Click!";
        fontSize = 22;
        if(s != null)
            fontSize = Integer.parseInt(s);
        if(s7 == null || s7.trim().length() == 0)
            mtext = null;
        else
            mtext = getMessages(s7, fontSize);
        umax = 50;
        tmax = 100;
        mwait = 1000;
        ctime = 40;
        isBlur = true;
        isGush = true;
        if(s1 != null)
            umax = Integer.parseInt(s1);
        if(s2 != null)
            tmax = Integer.parseInt(s2);
        if(s3 != null)
            mwait = Integer.parseInt(s3);
        if(s4 != null)
            ctime = Integer.parseInt(s4);
        if(s5 != null && s5.equalsIgnoreCase("OFF"))
            isBlur = false;
        if(s6 != null && s6.equalsIgnoreCase("OFF"))
            isGush = false;
        if(th == null)
            th = new Thread(this);
        th.start();
	}
	public void update(Graphics g){
	    paintComponent(g);
	}
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, 100, 100);
	}
    private int[][] createPixels()
    {
        int ai[];
        try
        {
            Image image;
            ai = getPixels(image = getToolkit().getImage("sunset.jpg"));
            if(ai == null)
                throw new NullPointerException();
            w = image.getWidth(this);
            h = image.getHeight(this);
        }
        catch(NullPointerException nullpointerexception)
        {
            //showStatus("ImageNotFound.");
            ai = new int[w * h];
        }
        int ai1[] = new int[ai.length];
        int ai2[] = new int[ai.length];
        int ai3[] = new int[ai.length];
        for(int i = 0; i < ai.length; i++)
        {
            ai1[i] = ai[i] >> 16 & 0xff;
            ai2[i] = ai[i] >> 8 & 0xff;
            ai3[i] = ai[i] & 0xff;
        }

        return (new int[][] {
            ai1, ai2, ai3
        });
    }

    public void run()
    {
        int i = umax;
        int j = tmax;
        int ai7[] = new int[3];
        int ai8[][] = new int[i][3];
        int ai4[][] = new int[i][j];
        float af[][] = new float[i][j];
        float af1[][] = new float[i][j];
        float af2[][] = new float[i][j];
        float af3[][] = new float[i][j];
        int ai5[][] = new int[i][4];
        int ai6[] = new int[i];
        for(int k = 0; k < i; k++)
            ai6[k] = -1;

        byte byte0 = -1;
        int ai9[][] = createPixels();
        int ai[] = new int[w * h];
        int ai1[] = new int[w * h];
        int ai2[] = new int[w * h];
        int ai3[] = new int[w * h];
        MemoryImageSource memoryimagesource = new MemoryImageSource(w, h, new DirectColorModel(24, 0xff0000, 65280, 255), ai, 0, w);
        memoryimagesource.setAnimated(true);
        memoryimagesource.setFullBufferUpdates(true);
        img = createImage(memoryimagesource);
        int ai10[] = new int[1024];
        int ai11[] = new int[1024];
        int l = 0;
        if(mtext != null)
            l = getPoints(mtext[0], ai10, ai11);
        else
            msize = new Dimension(0, 0);
        int i1 = 0;
        int j1 = 0;
        int k1 = 0;
        int l1 = 0;
        boolean flag = false;
        int ai12[] = CPAL[ran(CPAL.length)];
        int j2 = w - msize.width * 2;
        int k2 = h - msize.height * 2;
        int l2 = (int)((double)w * 0.20000000000000001D);
        int i3 = w * h;
        int j3 = (int)(Math.sqrt(h) / 2D);
        int k3 = 0;
        int l3 = 1;
        int i2 = ran(100) + 100;
        k1 = i1 = j2 / 2;
        l1 = j1 = k2 / 2;
        isMove = true;
        isCount = false;
        System.gc();
        lastTime = System.currentTimeMillis();
        while(th != null) 
        {
            if(i2 != 0x7fffffff && i2-- < 0)
            {
                if(mtext != null)
                {
                    if(l3 >= mtext.length)
                        l3 = 0;
                    l = getPoints(mtext[l3++], ai10, ai11);
                    j2 = w - msize.width * 2;
                    k2 = h - msize.height * 2;
                }
                i1 = ran(j2);
                j1 = ran(k2);
                ai12 = CPAL[ran(CPAL.length)];
                i2 = 0x7fffffff;
            } else
            {
                int i4 = (i1 - k1) / 8;
                int i5 = (j1 - l1) / 8;
                k1 += i4;
                l1 += i5;
                if(i2 == 0x7fffffff && i4 + i5 == 0)
                    i2 = ran(100) + 150;
            }
            if(isPressed && !isCount)
            {
                isCount = true;
                i2 = 0;
                k3 = 4;
                i1 = w / 2 - (fontSize * 2) / 2;
                j1 = h / 2 - (fontSize * 2) / 2;
                isPressed = false;
                ai12 = CPAL[ran(CPAL.length)];
                for(int j4 = 0; j4 < i; j4++)
                    ai5[j4][0] = 0x7fffffff;

            }
            if(isCount && i2 < 0)
                if(k3 == 0)
                {
                    isCount = false;
                } else
                {
                    i2 = 24;
                    if(--k3 == 0)
                    {
                        for(int k4 = 0; k4 < i; k4++)
                            ai5[k4][0] = ran(ctime);

                        l = 0;
                        i2 = ctime;
                    } else
                    {
                        l = getPoints(MCOUNT[k3 - 1], ai10, ai11);
                    }
                }
            for(int l4 = 0; l4 < l; l4++)
                if(ran(4) == 0)
                {
                    int j5 = ai10[l4] + k1 + ran(2) + (ai11[l4] + l1 + ran(2)) * w;
                    ai1[j5] = ai12[0];
                    ai2[j5] = ai12[1];
                    ai3[j5] = ai12[2];
                }

            for(int k5 = 0; k5 < i; k5++)
            {
                switch(ai6[k5])
                {
                case -1: 
                    af2[k5][0] = ran(w - l2) + l2 / 2;
                    ai6[k5] = 0;
                    ai5[k5][0] = ran(mwait);
                    ai5[k5][2] = isGush ? ran(8) : 1;
                    ai5[k5][3] = 0;
                    if(ai5[k5][2] == 0)
                    {
                        ai4[k5][0] = 0;
                        af3[k5][0] = h - 2;
                    } else
                    {
                        ai4[k5][0] = ran((j3 * 2) / 3) + j3;
                        af3[k5][0] = h;
                    }
                    int l5 = ran(CPAL.length);
                    ai8[k5][0] = CPAL[l5][0];
                    ai8[k5][1] = CPAL[l5][1];
                    ai8[k5][2] = CPAL[l5][2];
                    break;

                case 0: // '\0'
                    ai5[k5][0]--;
                    if(ai5[k5][0] <= 0)
                    {
                        ai6[k5] = 1;
                        if(hyu != null)
                            hyu.play();
                    }
                    break;

                case 1: // '\001'
                    af3[k5][0] -= ai4[k5][0];
                    if(ai5[k5][1]++ % 3 == 0)
                        ai4[k5][0]--;
                    if(ai4[k5][0] > 1)
                        af2[k5][0] += rnd.nextInt() % 2;
                    int i6;
                    if((i6 = (int)af2[k5][0] + (int)af3[k5][0] * w) >= 0)
                        ai1[i6] = ai2[i6] = ai3[i6] = 255;
                    if(ai4[k5][0] <= 0)
                        ai6[k5] = 2;
                    break;

                case 2: // '\002'
                    float f = rnd.nextFloat() * 2.0F + 0.4F;
                    int j7 = ran(1024 / j + 1);
                    if(ai5[k5][2] != 0)
                    {
                        for(int j8 = 0; j8 < j; j8++)
                        {
                            af3[k5][j8] = af3[k5][0];
                            af2[k5][j8] = af2[k5][0];
                            int l6 = ((2048 * j8) / j + j7) % 1024;
                            float f1 = rnd.nextFloat() * f;
                            af[k5][j8] = f1 * COS[l6];
                            af1[k5][j8] = f1 * SIN[l6];
                        }

                    } else
                    {
                        for(int k8 = 0; k8 < j; k8++)
                        {
                            af3[k5][k8] = af3[k5][0];
                            af2[k5][k8] = af2[k5][0];
                            int i7 = ((1024 * k8) / j / 8 + 704 + j7) % 1024;
                            float f2 = rnd.nextFloat() * (f + 1.0F);
                            af[k5][k8] = f2 * COS[i7];
                            af1[k5][k8] = f2 * SIN[i7];
                        }

                    }
                    ai6[k5] = 3;
                    ai5[k5][1] = 0;
                    if(ban != null)
                        ban.play();
                    break;

                case 3: // '\003'
                    int ai13[] = ai8[k5];
                    boolean flag1 = ai5[k5][2] == 0;
                    if(ai5[k5][3] > 25)
                    {
                        ai13[0] -= 8;
                        if(ai13[0] < 0)
                            ai13[0] = 0;
                        ai13[1] -= 8;
                        if(ai13[1] < 0)
                            ai13[1] = 0;
                        ai13[2] -= 8;
                        if(ai13[2] < 0)
                            ai13[2] = 0;
                    }
                    ai5[k5][3]++;
                    for(int l7 = 0; l7 < j; l7++)
                    {
                        af2[k5][l7] += af[k5][l7];
                        af3[k5][l7] += af1[k5][l7];
                        int j6;
                        if((j6 = (int)af2[k5][l7] + (int)af3[k5][l7] * w) >= 0 && j6 < i3 - w && ran(2) == 0)
                        {
                            ai1[j6] += ai13[0];
                            if(ai1[j6] > 255)
                                ai1[j6] = 255;
                            ai2[j6] += ai13[1];
                            if(ai2[j6] > 255)
                                ai2[j6] = 255;
                            ai3[j6] += ai13[2];
                            if(ai3[j6] > 255)
                                ai3[j6] = 255;
                        }
                    }

                    break;
                }
                if(ai5[k5][3] > 60)
                    ai6[k5] = -1;
            }

            for(int k6 = w; k6 < i3 - w; k6++)
            {
                ai1[k6] -= 6;
                if(ai1[k6] < 0)
                    ai1[k6] = 0;
                int k7 = ai9[0][k6] + ai1[k6];
                int i8 = k7 <= 255 ? k7 : 255;
                ai2[k6] -= 6;
                if(ai2[k6] < 0)
                    ai2[k6] = 0;
                k7 = ai9[1][k6] + ai2[k6];
                int l8 = k7 <= 255 ? k7 : 255;
                ai3[k6] -= 6;
                if(ai3[k6] < 0)
                    ai3[k6] = 0;
                k7 = ai9[2][k6] + ai3[k6];
                int i9 = k7 <= 255 ? k7 : 255;
                if(isBlur)
                {
                    ai1[k6] = (ai1[k6 - 1] + ai1[k6 + 1] + ai1[k6] + ai1[k6 + w]) / 4;
                    ai2[k6] = (ai2[k6 - 1] + ai2[k6 + 1] + ai2[k6] + ai2[k6 + w]) / 4;
                    ai3[k6] = (ai3[k6 - 1] + ai3[k6 + 1] + ai3[k6] + ai3[k6 + w]) / 4;
                } else
                {
                    ai1[k6] -= 10;
                    ai2[k6] -= 10;
                    ai3[k6] -= 10;
                }
                ai[k6] = i8 << 16 | l8 << 8 | i9;
            }

            memoryimagesource.newPixels();
            repaint();
            sleepThread();
        }
    }

    private void sleepThread()
    {
        Thread.yield();
        long l = System.currentTimeMillis();
        int i = (int)(l - lastTime);
        if(i < 34)
        {
            try
            {
                Thread.sleep(34 - i);
            }
            catch(InterruptedException interruptedexception) { }
            lastTime = System.currentTimeMillis();
        } else
        {
            try
            {
                Thread.sleep(1L);
            }
            catch(InterruptedException interruptedexception1) { }
            lastTime = l + 1L;
        }
    }

    private static final int ran(int i)
    {
        int j = rnd.nextInt() % i;
        return j >= 0 ? j : -j;
    }
	public int[] getPixels(Image image)
    {
        MediaTracker mediatracker = new MediaTracker(this);
        mediatracker.addImage(image, 0);
        int i;
        int j;
        try
        {
            do
            {
                mediatracker.waitForAll();
                i = image.getWidth(this);
                j = image.getHeight(this);
                if(mediatracker.isErrorID(0))
                    throw new NullPointerException("file not found");
            } while(i < 0 || j < 0);
            mediatracker.removeImage(image);
            mediatracker = null;
        }
        catch(InterruptedException interruptedexception)
        {
            return null;
        }
        int ai[] = new int[i * j];
        PixelGrabber pixelgrabber = new PixelGrabber(image, 0, 0, i, j, ai, 0, i);
        try
        {
            pixelgrabber.grabPixels();
        }
        catch(InterruptedException interruptedexception1)
        {
            return null;
        }
        if((pixelgrabber.getStatus() & 0x80) != 0)
            return null;
        else
            return ai;
    }

    private int getPoints(Message message, int ai[], int ai1[])
    {
        msize = message.getSize();
        if(msize.width <= 0 || msize.height <= 0)
            return -1;
        Image image = createImage(msize.width, msize.height);
        Graphics g = image.getGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, msize.width, msize.height);
        g.setColor(Color.white);
        message.drawString(g, 0, 0);
        g.dispose();
        g = null;
        int ai2[] = getPixels(image);
        int i = 0;
        for(int j = 0; j < msize.width; j++)
        {
            for(int k = 0; k < msize.height; k++)
                if(ai2[msize.width * k + j] != 0xff000000)
                {
                    ai[i] = (j + 1) * 2;
                    ai1[i] = (k + 1) * 2;
                    if(++i >= 1024)
                        return i;
                }

        }

        return i;
    }

    private static final Message[] getMessages(String s, int i, String s1)
    {
        StringTokenizer stringtokenizer = new StringTokenizer(s, "|");
        Message amessage[] = new Message[stringtokenizer.countTokens()];
        for(int j = 0; stringtokenizer.hasMoreTokens(); j++)
        {
            amessage[j] = new Message(stringtokenizer.nextToken(), new Font(s1, 0, i));
            Dimension dimension = amessage[j].getSize();
        }

        return amessage;
    }

    private static final Message[] getMessages(String s, int i)
    {
        return getMessages(s, i, "Monospaced");
    }
}