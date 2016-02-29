package lilirc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * thread that send chat lines to users
 * @author 3D
 *
 */
public class ServerSender extends Thread {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServerSender.class);
	
	/**
	 * 1. read next line from buffer
	 * 2. if line send it to all users
	 * 3. go to wait() state
	 */
	public void run() {
		String line;
		while(Server.online()) {
			if((line= Server.readLine()) != null) {
				for(ServerWorker user: Server.userPool.values()) {
					user.writeLine(line);
			}	}
			synchronized (this) { 
				try {
					wait();
				} catch (InterruptedException e) {
					LOGGER.error("Psicho ", e);
	} 	}	}	}
}
