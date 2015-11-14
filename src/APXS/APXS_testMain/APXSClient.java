package APXS.APXS_testMain;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

import generic.RoverClientRunnable;

public class APXSClient extends RoverClientRunnable {

	public APXSClient(int port, InetAddress host) throws UnknownHostException {
		super(port, host);
	}

	@Override
	public void run() {
		sendMessage("APXS ON");
		sendMessage("APXS RUN");
        sendMessage("APXS OFF");
        sendMessage("exit");
        
        try {
            closeAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	private void sendMessage(String msg){
        try {
            ObjectOutputStream outputToAnotherObject = null;
            ObjectInputStream inputFromAnotherObject = null;
            Thread.sleep(1500);
            
            // write to socket using ObjectOutputStream
            outputToAnotherObject = new ObjectOutputStream(getRoverSocket()
                                                           .getNewSocket().getOutputStream());
            
            System.out
            .println("=================================================");
            System.out
            .println("APXS Module Testing Framework: Sending request to Socket Server");
            System.out
            .println("=================================================");
            
            outputToAnotherObject.writeObject(msg);
            
            // read the server response message
            inputFromAnotherObject = new ObjectInputStream(getRoverSocket()
                                                           .getSocket().getInputStream());
            String message = (String) inputFromAnotherObject.readObject();
            System.out.println("APXS Module Testing Framework received: "
                               + message.toUpperCase());
            
            // close resources
            inputFromAnotherObject.close();
            outputToAnotherObject.close();
            Thread.sleep(300);
            
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (Exception error) {
            System.out.println("Testing Framework: Error:" + error.getMessage());
        }

	}

}
