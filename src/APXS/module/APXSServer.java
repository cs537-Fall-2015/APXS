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
				
				// write object to Socket
				outputToAnotherObject.writeObject("APXS Server response Hi Client - " + message);
				Random rand = new Random();
				int randVal = rand.nextInt(40)+1;
				// close resources
				inputFromAnotherObject.close();
				outputToAnotherObject.close();
				
				// getRoverServerSocket().closeSocket();
				// terminate the server if client sends exit request
				if (message.equalsIgnoreCase("exit"))
					break;
				if (message.equalsIgnoreCase("APXS ON"))
					System.out.println("APXS Server: APXS is ON");
				if (message.equalsIgnoreCase("APXS OFF"))
					System.out.println("APXS Server: APXS is OFF");
				
				if (message.equalsIgnoreCase("Check Temperature")){
					System.out.println("APXS Server: Current Temperature is "+randVal);
				if(randVal>30){
					System.out.println("APXS Server: APXS is shutting down and closing connection to Socket server");
					break;
				}}
				if (message.equalsIgnoreCase("Check Power Level")){
					System.out.println("APXS Server: Current Power Level is 5.2");
					
					}
			}
			System.out.println("Server: Shutting down Socket server !!");
			// close the ServerSocket object
			closeAll();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception error) {
			System.out.println("Server: Error:" + error.getMessage());
		}

	}

}
