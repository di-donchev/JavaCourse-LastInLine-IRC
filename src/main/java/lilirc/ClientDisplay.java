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
		while(socket.online() && (readLine() != null)) {
		}
		socket.online(false);
	}
	/**
	 * single action
	 * @return
	 */
	public String readLine() {
		String line;
		try { 
	  		line = socket.input().readLine();
			System.out.println(line);
			return line; 
		} catch (IOException e) {
			/**
			 * used internal java mechanism to terminate display thread from outside
			 * in our case from ClientConsole 
			 */
			if(socket.online()) { // if true -> error else down
				LOGGER.error("Socket down.", e);
				System.out.println("Server down.");
		}	}
		return null;
	}
}
