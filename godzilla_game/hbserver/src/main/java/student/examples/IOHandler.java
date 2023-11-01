package student.examples;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Map;

public class IOHandler extends Thread {
	
	Map<InetAddress, Socket> clients;
	
	
	public IOHandler(Map<InetAddress, Socket> clients) {
		super();
		this.clients = clients;
	}


	@Override
	public void run() {
		// IO loop
		while (true) {
			System.out.println("IO handler while loop");
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			synchronized(clients) {
				clients.forEach((inetAddress, clientSocket) -> {
					try {
						IOStream ioStream = new IOStream(new BufferedOutputStream(clientSocket.getOutputStream()), 
														new BufferedInputStream(clientSocket.getInputStream()));
						
						if (clientSocket.isConnected()) {
							int in = ioStream.receive();
							if (in >= 0 && in < Action.values().length) {
		
								Action action = Action.values()[in];
								switch (action) {
								case POKE:
									System.out.println("Server received " + Action.POKE);
									ioStream.send(Action.OK.ordinal());
									System.out.println("Server sent " + Action.OK);
									break;
								case SHUTDOWN:
									System.out.println("Server received " + Action.SHUTDOWN);
									ioStream.send(Action.SHUTDOWN.ordinal());
									System.out.println("Server sent " + Action.SHUTDOWN);
									break;
								default:
									System.out.println("Server received something unknown");
									ioStream.send(Action.ERROR.ordinal());
									System.out.println("Server sent " + Action.ERROR);
								}
							} 
						} else {
							System.out.println("Client disconected");
						}
					} catch (IOException e) {
						e.printStackTrace();
					} 
	
				});
			}
		}
	}

}
