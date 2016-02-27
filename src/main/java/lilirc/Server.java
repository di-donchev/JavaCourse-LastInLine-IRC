package lilirc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 1. Server ������� ����� �� ������� � � ��������
 * 2. ������� �� ������� ��������� ���� username � �������� � ��� �� �� ������� � ������� �� ������� 
 * 3. ������� ����������� ���� FIFO ����� ������ 
 * 	  1. ������� ������� ��������� �� � ������ � ����� notify() �� �������
 * 	  2. ������ ���� �� �������� �� FIFO ������ � ������� �� ������ output �� �������
 * 	  3. ��� ������ ������ �� ���� �� ������ � � ���������� �������� �������� 
 *       - �� ������� ��� �����
 *		  4. ������ ������� ��� wait()
 *    ������ �� �� �������� ����� ������� ����������
 * @author 3D
 *
 */
public class Server {
	public static Map<String, ServerWorker> userPool= new HashMap<String, ServerWorker>();
	public static ServerSender sender;
	private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);
	
	/**
	 * ��� �� ����� ����������� �� ������� ������� ����� �������
	 * 1. ����� ����� ������ �������� ����� ��� ������ (������������� � �� ���� ��)
	 * 2. ��� ����������� �� ������ ����� (����������� �� ���� ���������)
	 * 3. ������ - ��������� ������ �� ��������� ����� 
	 * 
	 * ������ � ����� 2 
	 * @param args
	 */
	public static void main(String[] args) {
		try (ServerSocket server = new ServerSocket(8091)) {
			LOGGER.info("{} Server Started.", Time.is());
			file= getnewfile();
			output= new PrintWriter(file);
			irc= new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
			sender= new ServerSender();
			sender.start();
			while (true) {
				new ServerWorker(new CSSocket(server.accept())).start();
			}
		} catch (IOException e) {
			LOGGER.error(Time.is()+"Server ERROR!", e);
	}	}
	
	/**
	 * server buffer members and io methods
	 */
	private static BufferedReader irc;
	private static boolean  newBuffer= true;
	private static File file;
	private static PrintWriter output;
	
	/**
	 * create / and write to buffer 
	 * @param line
	 */
	public static synchronized void println(String line) {
		// check for OS file size limit
		if(file.length() > 2000000000L) {
			output.close();
			output= null;
		}
		if(output == null) {
			try {
				output= new PrintWriter(file= getnewfile());
			} catch (IOException e) {
				LOGGER.error("Print to buffer", e);
			}
			newBuffer= true;
		}
		output.println(Time.is()+line);
		output.flush();
		synchronized (sender) { sender.notify();}
	}
	private static File getnewfile() throws IOException {
		return File.createTempFile("irc", ".log", new File(".\\"));
	}
	/**
	 * read from buffer
	 * @return
	 */
	public static synchronized String readln() {
		String result=null;
		try {
			result = irc.readLine();
		} catch (IOException e) {
			LOGGER.error("IRC readln", e);
		}
		if(result == null) {
			if(newBuffer) {
				try {
					irc.close();
				} catch (IOException e) {
					LOGGER.error("IRC close", e);
				}
				try {
					irc= new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
				} catch (FileNotFoundException e) {
					LOGGER.error("FIle error", e);
				}
				try {
					result= irc.readLine();
				} catch (IOException e) {
					LOGGER.error("IRC read", e);
				}
				newBuffer= false;
		}	}
		return result;
	}
	/**
	 * server control flag
	 */
	private static boolean online= true;
	/**
	 * getter - return server control flag value 
	 * @return boolean
	 */
	public static boolean online() {
		return online;
	}
	/**
	 * setter - set server control flag value and return it
	 * @param onln
	 * @return boolean
	 */
	public static boolean online(boolean onln) {
		return online= onln;
	}
}
