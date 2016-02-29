package lilirc;

import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * server user thread with associated socket and io streams 
 * @author 3D
 *
 */
public class ServerWorker extends Thread {
	private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);
	private CSSocket client;
	/**
	 * construct thread
	 * @param soct
	 */
	public ServerWorker(CSSocket soct) {
		client= soct;
	}
	/**
	 * when run finished thread finished too
	 */
	public void run() {
		LOGGER.info("{}[{}] connected."
				   , Time.is(), client.socket().getRemoteSocketAddress());
		client.output().println("Enter username:");
		String user=readLine();
		if(Server.userPool.containsKey(user)) {
			LOGGER.info("{}[{}] duplicated username - terminate."
					   , Time.is(), client.socket().getRemoteSocketAddress());
			client.output().println("Username not match.");
		} else {	
			LOGGER.info("{}[{}] username [{}]"
					   , Time.is(), client.socket().getRemoteSocketAddress(), user);
			Server.userPool.put(user, this);
			client.output().println("Welcome " + user + ".");
			String line;
			while(Server.online() && ((line= readLine()) != null)) {
				Server.printLine(user+":"+line);
			}
			Server.userPool.remove(user);
	}	}
	/**
	 * 
	 * @return
	 */
	private String readLine() {
		String line= null;
		try {
			line= client.input().nextLine();
		} catch (NoSuchElementException e) {
			LOGGER.error("Client down.",e);
		}
		return line;	
	}
	public  void  writeLine(String line) { client.output().println(line); }
}
