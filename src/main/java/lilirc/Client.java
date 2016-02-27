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
		display.read();
		console= new ClientConsole(socket).hint("username:>").quit("quit");
		if(console.read() != null) {
			if(display.read().equalsIgnoreCase("Welcome.")) {
				socket.online(true);
				display.start();
				console.hint("console:>").start();
				synchronized (display) { 
					try {
						display.wait();
					} catch (InterruptedException e) {
						LOGGER.info("Display is dead.", e);
					}	}
				console.close();
	}	}	}	
}
