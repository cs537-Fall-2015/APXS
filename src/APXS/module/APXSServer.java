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

		try {
			while (true) {
				
<<<<<<< HEAD
				System.out.println("Module 1 Server: Waiting for client request");
=======
				System.out.println("APXS Server: Waiting for client request");
>>>>>>> ba090a8642e180c0b07c666576a2970743b6b501
				
				// creating socket and waiting for client connection
				getRoverServerSocket().openSocket();
				
				// read from socket to ObjectInputStream object
				ObjectInputStream inputFromAnotherObject = new ObjectInputStream(getRoverServerSocket().getSocket().getInputStream());
				
				// convert ObjectInputStream object to String
				String message = (String) inputFromAnotherObject.readObject();
<<<<<<< HEAD
				System.out.println("Module 1 Server: Message Received from Client - "+ message.toUpperCase());
=======
				System.out.println("APXS Server: Message Received from Client - "+ message.toUpperCase());
>>>>>>> ba090a8642e180c0b07c666576a2970743b6b501
				
				// create ObjectOutputStream object
				ObjectOutputStream outputToAnotherObject = new ObjectOutputStream(getRoverServerSocket().getSocket().getOutputStream());
				
				// write object to Socket
<<<<<<< HEAD
				outputToAnotherObject.writeObject("Module 1 Server response Hi Client - " + message);
=======
				outputToAnotherObject.writeObject("APXS Server response Hi Client - " + message);
>>>>>>> ba090a8642e180c0b07c666576a2970743b6b501
				
				// close resources
				inputFromAnotherObject.close();
				outputToAnotherObject.close();
				
				// getRoverServerSocket().closeSocket();
				// terminate the server if client sends exit request
				if (message.equalsIgnoreCase("exit"))
					break;
			}
			System.out.println("Server: Shutting down Socket server 1!!");
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
