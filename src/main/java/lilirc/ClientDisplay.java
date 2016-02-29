package lilirc;

import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * thread that read from server and write to display
 * @author 3D
 */
public class ClientDisplay extends Thread {
	private static final Logger LOGGER = LoggerFactory.getLogger(ClientDisplay.class);
	
	private CSSocket socket;
	/**
	 * constructor with socket argument
	 * @param css
	 */
	public ClientDisplay(CSSocket css) {
		socket= css;
	}
	/**
	 * action of display thread
	 */
	public void run() {
		while(socket.online()) {
			readLine();
		}
		socket.online(false);
	}
	/**
	 * single action
	 * @return
	 */
	public String readLine() {
		String line= null;
		try { 
			line = socket.input().nextLine();
		} catch (NoSuchElementException e) {
			if (socket.online()) { 
				LOGGER.error("Socket closed.", e);
			}
		}
		if(line != null) {
			System.out.println(line);
		}
		return line;
	}
}
