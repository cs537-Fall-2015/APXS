package APXS.APXS_testMain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import json.Constants;
import generic.RoverClientRunnable;

public class APXSClient extends RoverClientRunnable {

	public APXSClient(int port, InetAddress host) throws UnknownHostException {
		super(port, host);
	}

	@Override
	public void run() {
		BufferedReader br = null;

		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader(Constants.commands));
			while ((sCurrentLine = br.readLine()) != null) {
				sendMessage(sCurrentLine);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
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
            Thread.sleep(1000);
            
            // write to socket using ObjectOutputStream
            outputToAnotherObject = new ObjectOutputStream(getRoverSocket()
                                                           .getNewSocket().getOutputStream());
            
            System.out
            .println("=================================================");
            System.out
            .println("APXS Testing Framework: Sending request to Socket Server");
            System.out
            .println("=================================================");
            
            outputToAnotherObject.writeObject(msg);
            
            // read the server response message
            inputFromAnotherObject = new ObjectInputStream(getRoverSocket()
                                                           .getSocket().getInputStream());
            String message = (String) inputFromAnotherObject.readObject();
            System.out.println("APXS Testing Framework received: " + message.toUpperCase());
            
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
