package lilirc;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientRunner {
	private static final Logger LOGGER = LoggerFactory.getLogger(ClientRunner.class);
	
	public static void main(String[] args) {

		Client client= null;
		try {
			client = new Client("localhost", 8091);
		} catch (IOException e) {
			LOGGER.error("NO Server.", e);
			return;
		}
		client.start();
	}
}
