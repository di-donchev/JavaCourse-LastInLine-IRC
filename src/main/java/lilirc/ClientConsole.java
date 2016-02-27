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
	 * print external message trough Console 
	 * @param ht
	 * @return
	 */
	public ClientConsole printHint(String ht) {
		if(ht == null) {
			ht= hint;
		}
		System.out.print(ht); // in this case just console
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
		do { 
			printHint(null);
		} while(readLine() != null);
		socket.online(false);
		console.close();
	}
	/**
	 * read from console and in the same time check if socket online
	 * @return
	 */
	public String readLine() {
		String line;
		try {
			while(socket.online()) {
				if(System.in.available() != 0) {
					if(!(line= console.nextLine()).equalsIgnoreCase(quit)) {
						socket.output().println(line);
						return line;
					} else { 
						break;	
			}	}	}
		} catch (IOException e){
			LOGGER.error("Console error", e);
		}
		return null;
	}
}
