package lilirc;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * thread that read from server and write to display
 * @author 3D
 */
public class ClientDisplay extends Thread {
	private static final Logger LOGGER = LoggerFactory.getLogger(ClientRunner.class);
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
		while(socket.online() && (read() != null)) {
		}
		socket.online(false);
		try {
			socket.socket().close();
		} catch (IOException e) {
			LOGGER.error("Close", e);
		}
		System.out.println("Server down. Press <Enter> to close.");
	}
	/**
	 * single action
	 * @return
	 */
	public String read() {
		String line;
		try { 
	  		line = socket.input().readLine();
			System.out.println(line);
			return line; 
		} catch (IOException e) { 
			LOGGER.error("Read", e);
			return null;
	}	}
}
