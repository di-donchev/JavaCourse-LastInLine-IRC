package lilirc;

import java.io.IOException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * implement client console reading thread
 * wait user to made input from console and output it to client::socket.output 
 * @author 3D
 *
 */
public class ClientConsole extends Thread {

	private String hint= ":>";
	private String quit= "quit";
	private CSSocket socket;
	private Scanner console= new Scanner(System.in);
	private static final Logger LOGGER = LoggerFactory.getLogger(ClientConsole.class);
	/**
	 * main constructor
	 * @param cli
	 */
	public ClientConsole(CSSocket css) { 
		socket= css; 
	}
	/**
	 * init hint that user see in console 
	 * @param ht
	 * @return
	 */
	public ClientConsole hint(String ht) {
		hint= ht;
		return this;
	}
	/**
	 * init quit word (default "quit")
	 * @param qt
	 * @return
	 */
	public ClientConsole quit(String qt) {
		quit= qt;
		return this;
	}
	/**
	 * thread action
	 * thread wait user enter in console and if not quit word
	 * output it to client.output
	 */
	public void run() {
		/**
		 * there no logic to stop only client console
		 * user must void quit word or client class to close socket 
		 */
		while (socket.online() && (read() != null)) {
		}
		close();
	}
	/**
	 * DRY
	 * @return
	 */
	public String read() {
		String line;
		System.out.print(hint);
		
		if(!(line= console.nextLine()).equalsIgnoreCase(quit)) {
			socket.output().println(line);
			return line;
		}
		return null;
	}
	public void close() {
		socket.online(false);
		console.close();
		try {
			socket.socket().close();
		} catch (IOException e) {
			LOGGER.error("Close", e);
	}	}
}
