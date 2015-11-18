package APXS.module;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import generic.RoverServerRunnable;

public class APXSServer extends RoverServerRunnable {
	public APXSServer(int port) throws IOException {
		super(port);
	}
	
	@Override
	public void run() {
		APXS apxs = new APXS();
		try {
			while (true) {
				System.out.println("APXS Server: Waiting for client request");
				// creating socket and waiting for client connection
				getRoverServerSocket().openSocket();
				
				// read from socket to ObjectInputStream object
				ObjectInputStream inputFromAnotherObject = new ObjectInputStream(getRoverServerSocket().getSocket().getInputStream());
				
				// convert ObjectInputStream object to String
				String message = (String) inputFromAnotherObject.readObject();

				System.out.println("APXS Server: Message Received from Client - "+ message.toUpperCase());
				
				
				// create ObjectOutputStream object
				ObjectOutputStream outputToAnotherObject = new ObjectOutputStream(getRoverServerSocket().getSocket().getOutputStream());
				Object result = apxs.runCommand(message);
				// write object to Socket

				outputToAnotherObject.writeObject(result);
				// close resources
				inputFromAnotherObject.close();
				outputToAnotherObject.close();
				
				// terminate the server if client sends exit request
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception error) {
			System.out.println("Server: Error:" + error.getMessage());
		}
		try {
			closeAll();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
	}

}
