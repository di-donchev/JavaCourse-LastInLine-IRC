package lilirc;

import java.io.IOException;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * implement complete Client with Socket and output / input connected to this socket
 * if user enter right username Client start ClientConsole and ClientDisplay threads
 * @author 3D
 *
 */
public class Client {
	private String username;
	private CSSocket socket;
	private ClientConsole console;
	private ClientDisplay display;
	private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);

	public Client(String addr, int port) throws UnknownHostException, IOException {
		socket= new CSSocket(addr, port);
	}
	/**
	 * main action 
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public void start() {
		display= new ClientDisplay(socket);
		display.readLine();
		console= new ClientConsole(socket).quit("quit");
		console.printHint("username:>");
		if((username= console.readLine()) != null) {
			if(display.readLine().contains("Welcome")) {
				display.start();
				console.hint(username + ":>").start(); 
				synchronized (console) { 
					try {
						console.wait();
					} catch (InterruptedException e) {
						LOGGER.error("Psicho.", e);
		}	}	}	}
		socket.online(false);
		try {
			socket.socket().close();
		} catch (IOException e) {
			LOGGER.error("Socket close error.", e);
		}
		System.out.println("Goodbye!");
	}	
}
