package APXS.APXS_testMain;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import json.Constants;
import generic.RoverClientRunnable;

public class APXSClient extends RoverClientRunnable {
	static JFrame frame = new JFrame();
	static JTextArea area = new JTextArea();
	static int flag= 0;
	String filename;  
	static int fileIncrementer =0;

	public APXSClient(int port, InetAddress host) throws UnknownHostException {
		super(port, host);
	}

	@Override
	public void run() {
		BufferedReader filebr = null;
		BufferedReader br = null;
		
		while(true){
			filebr = new BufferedReader(new InputStreamReader(System.in));
			String sCurrentLine;
			System.out.println("Enter the file name for the commands: ");
			try {
				filename = filebr.readLine();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			try {
				if(flag == 0){
				frame = new JFrame( "Earth Client" );
		        frame.setLayout( new GridLayout( 1,0 ) );
		        frame.setBounds(10, 30, 0, 0);
		        //frame.setBackground(Color.BLACK);
		        frame.getContentPane().setBackground( Color.black );
		        JPanel buttons = new JPanel(new FlowLayout());
		        buttons.setOpaque(true);
		        buttons.setForeground( Color.BLACK );
		        area = new JTextArea();
		       // area.append( "\n Sending request to Mars Rover \n" );
		        area.setEditable( false );
		        area.setLineWrap( true );
		        area.setForeground(Color.black);
		        JScrollPane sp = new JScrollPane(area);
		        frame.add( sp );
		        // frame.add( textArea );
		        frame.setMinimumSize( new Dimension( 650, 340 ) );
		       
		        
		        frame.setResizable(false);
		        frame.setVisible( true );
		        flag++;
		        fileIncrementer++;
			}
				br = new BufferedReader(new FileReader(Constants.commands+filename));
				while ((sCurrentLine = br.readLine()) != null) {
					if(sCurrentLine.equalsIgnoreCase("APXS_ON")||sCurrentLine.equalsIgnoreCase("APXS_OFF")||
							sCurrentLine.equalsIgnoreCase("CHECK_TEMPERATURE")||sCurrentLine.equalsIgnoreCase("READ_LOG")||
							sCurrentLine.equalsIgnoreCase("APXS_MEASURE"))
						sendMessage(sCurrentLine);
					else
						System.out.println("APXS Testing Framework: Wrong command - "+sCurrentLine);
				}

			}catch(FileNotFoundException e2){
				System.out.println("File does not exist. Please try again.");
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
            area.append( "\n Sending request to Mars Rover \n" );
            
            outputToAnotherObject.writeObject(msg);
            Thread.sleep(2000);
            // read the server response message
            inputFromAnotherObject = new ObjectInputStream(getRoverSocket()
                                                           .getSocket().getInputStream());
            String message = (String) inputFromAnotherObject.readObject();
            System.out.println("APXS Testing Framework received: APXS server response - " + message.toUpperCase());
            area.append("\n APXS server response: "+message.toUpperCase());
            // close resources
            inputFromAnotherObject.close();
            outputToAnotherObject.close();
            Thread.sleep(1000);
            
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (Exception error) {
            error.printStackTrace();;
        }

	}
}
