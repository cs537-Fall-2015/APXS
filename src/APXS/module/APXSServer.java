package APXS.module;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

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

				System.out.println("APXS : Message Received from GroundStation - "+ message.toUpperCase());
				if (message.equalsIgnoreCase("exit"))
					break;
				if (message.equalsIgnoreCase("APXS ON")){
					apxs.turnOn();
					
				}
				if(message.equalsIgnoreCase("APXS ON")){
					APXS.apxs_checkTemp();
				}
				if (message.equalsIgnoreCase("APXS OFF")){
					apxs.turnOff();
				}
				
				
				
				// create ObjectOutputStream object
				ObjectOutputStream outputToAnotherObject = new ObjectOutputStream(getRoverServerSocket().getSocket().getOutputStream());
				
				// write object to Socket

				outputToAnotherObject.writeObject("APXS Server responseAPXS - " + message);
				// close resources
				inputFromAnotherObject.close();
				outputToAnotherObject.close();
				
				
				// terminate the server if client sends exit request
			}
			System.out.println("APXS: Shutting down connection (Socket server)!!");
			// close the ServerSocket object
			closeAll();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception error) {
			System.out.println("Server: Error:" + error.getMessage());
		}
			
	}

}
