package firework2k14;

import java.net.URL;

import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.EndOfMediaEvent;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Player;

public class fwsound extends Thread {
	private String path;
	private Player playMP3;

	public fwsound(String mp3) {
		this.path = mp3;
	}

	public void run() {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        

		try {
			playMP3 = Manager.createPlayer(new URL("file:///" + this.path));
		} catch (java.io.IOException e) {
			System.out.println(e.getMessage());
		} catch (javax.media.NoPlayerException e) {
			System.out.println(e.getMessage());
		}

		playMP3.addControllerListener(new ControllerListener() {
			public void controllerUpdate(ControllerEvent e) {
				if (e instanceof EndOfMediaEvent) {
					playMP3.stop();
					playMP3.close();
				}
			}
		});
		playMP3.realize();
		playMP3.start();
	}
}
