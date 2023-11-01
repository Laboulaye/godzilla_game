package student.examples;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import student.examples.Action;


//TODO split the code in client  like server-side
public class HBClient {
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		Socket clientSocket = new Socket("localhost", 7777);
		
		IOStream ioStream = new IOStream(new BufferedOutputStream(clientSocket.getOutputStream()), 
				new BufferedInputStream(clientSocket.getInputStream()));
		
		
		System.out.println("CLIENT STARTED");
		
		//IO loop
		while(true) {
			Thread.sleep(1000);
			if (clientSocket.isConnected()) {
				ioStream.send(Action.POKE.ordinal());
				System.out.println("Client sent message " + Action.POKE);
				
				int in = ioStream.receive();
				if (in >= 0 && in < Action.values().length) {
				
					Action action = Action.values()[in];
					switch (action) {
					case OK:
						System.out.println("Client received " + Action.OK);
						break;
					case POKE:
						System.out.println("Client received " + Action.POKE);
						break;
					case SHUTDOWN:
						System.out.println("Client received " + Action.SHUTDOWN);
						break;
					}
				}
			}
		}
		
	}

}
