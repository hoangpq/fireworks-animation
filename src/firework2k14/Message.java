package firework2k14;

//Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 12/01/2014 11:52:05 CH
//Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
//Decompiler options: packimports(3) 
//Source File Name:   Message.java

import java.awt.*;

class Message
{

 Message(String s, Font font1)
 {
     font = font1;
     mes = s;
     FontMetrics fontmetrics = Toolkit.getDefaultToolkit().getFontMetrics(font1);
     width = fontmetrics.stringWidth(s);
     ascent = fontmetrics.getAscent();
     descent = fontmetrics.getDescent();
     height = fontmetrics.getHeight();
 }

 void drawString(Graphics g, int i, int j)
 {
     g.setFont(font);
     g.drawString(mes, i, j + ascent);
 }

 Dimension getSize()
 {
     return new Dimension(width, height);
 }

 String mes;
 Font font;
 int ascent;
 int descent;
 int width;
 int height;
}
