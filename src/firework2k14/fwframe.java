package firework2k14;
import java.awt.BorderLayout;
import javax.swing.JFrame;

public class fwframe extends JFrame{
	int width = 500, height = 500;
	public fwframe(){
		setSize(width, height);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		// get content
		getContentPane().add(new fwpanel(width, height), BorderLayout.CENTER);
	}
	public static void main(String args[]) {
		new fwframe();
	}
}
