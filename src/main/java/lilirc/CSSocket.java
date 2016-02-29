package lilirc;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class CSSocket {

	private boolean online;
	private Socket socket;
	private PrintWriter output;
	private Scanner input;
	
	public CSSocket(Socket soct) throws IOException {
		socket= soct;
		iofactory();
	}

	public CSSocket(String addr, int port) throws IOException {
		socket= new Socket(addr, port);
		iofactory();
	}
	/**
	 * return connected to socket output
	 * @return
	 */
	public PrintWriter output() { // getOutput
		return output;
	}
	/**
	 * return connected to socket input
	 * @return
	 */
	public Scanner input() { // getInput
		return input;
	}
	private void iofactory() throws IOException {
		output= new PrintWriter(socket.getOutputStream(), true);
		 input= new Scanner(socket.getInputStream());
		online= true;
	}

	public Socket socket() {
		return socket;
	}
	public boolean online() { return online; }
	public CSSocket online(boolean onl) {
		online= onl;
		return this;
	}
}
