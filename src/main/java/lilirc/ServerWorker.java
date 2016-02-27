package lilirc;

import java.io.IOException;

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
			client.output().println("Welcome.");
			String line;
			while(((line= readLine()) != null) && Server.online()) { // here thread wait user action
				Server.println(user+":"+line);
			}
			Server.userPool.remove(user);
	}	}
	private String readLine() {
		try {
			return client.input().readLine();
		} catch (IOException e) {
			LOGGER.error(Time.is()+"IO ERROR!", e);
			return null;
	}	}
	public void writeLine(String line) {
		client.output().println(line);
	}
}
