package APXS.APXS_testMain;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
		BufferedReader filebr = null;
		String filename;  
		BufferedReader br = null;
		while(true){
			try {
				filebr = new BufferedReader(new InputStreamReader(System.in));
				String sCurrentLine;
				System.out.println("Enter the file name for the commands: ");
				filename = filebr.readLine();
				br = new BufferedReader(new FileReader(Constants.commands+filename));
				while ((sCurrentLine = br.readLine()) != null) {
					if(sCurrentLine.equalsIgnoreCase("APXS_ON")||sCurrentLine.equalsIgnoreCase("APXS_OFF")||
							sCurrentLine.equalsIgnoreCase("CHECK_TEMPERATURE")||sCurrentLine.equalsIgnoreCase("READ_LOG")||
							sCurrentLine.equalsIgnoreCase("APXS_MEASURE"))
						sendMessage(sCurrentLine);
					else
						System.out.println("APXS Module Testing Framework: Wrong command - "+sCurrentLine);
				}

			}catch(FileNotFoundException e2){
				System.out.println("This File does not exist. Please try again.");
			}catch(IOException e) {
				e.printStackTrace();
			}finally {
				try {
					if (br != null)
						br.close();
				} catch (IOException ex) {
				}
			}
			try {
				closeAll();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void sendMessage(String msg){
		try {
			ObjectOutputStream outputToAnotherObject = null;
			ObjectInputStream inputFromAnotherObject = null;
			Thread.sleep(2000);

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
			Thread.sleep(3000);
			// read the server response message
			inputFromAnotherObject = new ObjectInputStream(getRoverSocket()
					.getSocket().getInputStream());
			String message = (String) inputFromAnotherObject.readObject();
			System.out.println("APXS Testing Framework received: APXS server response - " + message.toUpperCase());

			// close resources
			inputFromAnotherObject.close();
			outputToAnotherObject.close();
			Thread.sleep(2000);

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (Exception error) {
			error.printStackTrace();;
		}

	}

}
