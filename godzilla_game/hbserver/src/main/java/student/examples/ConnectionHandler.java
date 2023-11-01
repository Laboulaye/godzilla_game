package student.examples;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ConnectionHandler extends Thread {
	
	ServerSocket serverSocket;
	
	Map<InetAddress, Socket> clients;
	
	
	public ConnectionHandler(ServerSocket serverSocket, Map<InetAddress, Socket> clients) {
		super();
		this.serverSocket = serverSocket;
		this.clients = clients;
	}


	@Override
	public void run() {
		//connection loop
		while (true) {
			System.out.println("Connection handler while loop");
			Socket clientSocket;
			try {
//				Thread.sleep(1000);
				clientSocket = serverSocket.accept();
				//avoid synch on the accept method
				System.out.println("CLIENT joined on port " + 7777);
				clients.put(clientSocket.getInetAddress(), clientSocket);
				
			} catch (IOException e) {
				e.printStackTrace();
//			} catch (InterruptedException e) {
//				e.printStackTrace();
			}
			break;
		}
	}

}
