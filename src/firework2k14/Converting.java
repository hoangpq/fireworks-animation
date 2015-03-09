package firework2k14;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Converting{
    public static void main(String[] args) {
        
    	//create a frame for the application
        final JFrame frame = new JFrame("PApplet in Java Application");

        //make sure to shut down the application, when the frame is closed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //create a panel for the applet and the button panel
        JPanel panel = new JPanel();

        //create an instance of your processing applet
        final fireworksweb applet = new fireworksweb();

        //start the applet
        //applet.init();
        
        //store the applet in panel
        panel.add(applet);
        applet.init();

        //store the panel in the frame
        frame.add(panel);
        //assign a size for the frame
        //reading the size from the applet
        frame.setSize(applet.getSize().width, applet.getSize().height +200);

        //display the frame
        frame.setVisible(true);
    }
}
