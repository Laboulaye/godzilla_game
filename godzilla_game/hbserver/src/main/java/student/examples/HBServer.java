package student.examples;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import student.examples.Action;

//TODO create logger and add logs to server/client to the console

public class HBServer {
	
	private final int PORT;
	private final String HOST;
	private final int BACKLOG;
	//usually this is injected
	private ConnectionHandler connectionHandler;
	private IOHandler ioHandler;
	private Map<InetAddress, Socket> clients;
	
	
	public HBServer(int port, String host, int backlog) {
		super();
		this.PORT = port;
		this.HOST = host;
		this.BACKLOG = backlog;
		clients = new ConcurrentHashMap<>();
		try {
			this.connectionHandler = new ConnectionHandler(
					new ServerSocket(PORT, BACKLOG, InetAddress.getByName(HOST)), clients);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.ioHandler = new IOHandler(clients);
	}


	public void run() {
		connectionHandler.start();
		ioHandler.start();
	}

	public static void main(String[] args) throws IOException {
		HBServer hbserver = new HBServer(7777, "localhost", 100);	
		hbserver.run();
		System.out.println("SERVER STARTED");
	}

}
