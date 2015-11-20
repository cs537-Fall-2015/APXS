package APXS.APXS_testMain;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
	
	public APXSClient(int port, InetAddress host) throws UnknownHostException {
		super(port, host);
	}

	@Override
	public void run() {
		BufferedReader br = null;
		frame = new JFrame( "Earth Client" );
        frame.setLayout( new GridLayout( 3,0 ) );
        frame.setBounds(10, 30, 0, 0);
        frame.getContentPane().setBackground( new Color(255,255,255) );
        JPanel buttons = new JPanel(new FlowLayout());
        buttons.setBackground( new Color(255,255,255) );
        area = new JTextArea();
        area.append( "\n Sending request to Socket Server \n" );
        area.setEditable( false );
        area.setLineWrap( true );
        JScrollPane sp = new JScrollPane(area);
        frame.add( sp );
        // frame.add( textArea );
        frame.setMinimumSize( new Dimension( 650, 340 ) );
        frame.setResizable(false);
        frame.setVisible( true );
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
            area.append( "\n Sending request to Socket Server \n" );
            
            outputToAnotherObject.writeObject(msg);
            
            // read the server response message
            inputFromAnotherObject = new ObjectInputStream(getRoverSocket()
                                                           .getSocket().getInputStream());
            String message = (String) inputFromAnotherObject.readObject();
            System.out.println("APXS Testing Framework received: APXS server response - " + message.toUpperCase());
            area.append("\n APXS server response: "+message.toUpperCase());
            // close resources
            inputFromAnotherObject.close();
            outputToAnotherObject.close();
            Thread.sleep(500);
            
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (Exception error) {
            error.printStackTrace();;
        }

	}

}
