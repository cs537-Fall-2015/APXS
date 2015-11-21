package APXS.module;

import java.io.BufferedReader;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import json.Constants;

public class APXS {
		private final static boolean ON = true;
		private final static boolean OFF = false;
		static JFrame frame;
		static JTextArea area;
		static JButton cmd1;
		static JButton cmd2,cmd3,cmd4,cmd5,cmd6;
		static PrintWriter log;
		private boolean state;
		static public int flag2 =0;
		static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		//System.out.println(dateFormat.format(date)); //2014/08/06 15:59:48
	    private HashMap<Long, Double> data = new HashMap<Long, Double>();
	    {
	    	try{
	    		log = new PrintWriter(
	    				new BufferedWriter(new FileWriter(Constants.apxs_logpath, true)));
	    	}
	    	catch(IOException e){

	    	}
	    }
	    private void turnOff(){
			state = OFF;
			System.out.println("APXS: APXS is turning off");
			area.append("\n APXS is turning OFF");
			cmd5.setBackground(Color.red);
			cmd1.setBackground(Color.white);
			cmd6.setBackground(Color.white);
			cmd3.setBackground(Color.white);
	    	cmd2.setBackground(Color.white);
			threadSleep(3000);
			log.println("APXS OFF -" +dateFormat.format(new Date()));
			log.println("");
			log.close();
		}

		public APXS() {
			state = OFF;
	    }
	    
	    // Change state

	    public void turnOn(){
	    	
	    	state = ON;
	    	System.out.println("APXS: APXS is turning on");
	    	threadSleep(3000);
	    	area.append("\n APXS is turning ON");
	    	log.println("APXS turned ON "+dateFormat.format(new Date()));
	    	cmd1.setBackground(Color.RED);
	    	cmd6.setBackground(Color.red);
	    }
	    
	    public void sensorTurnOff(){
	    	System.out.println("APXS: Sensor is OFF.");
	    	area.append("\n Sensor is OFF");
	    	cmd4.setBackground(Color.red);
	    	cmd3.setBackground(Color.white);
	    	cmd2.setBackground(Color.white);
	    	cmd6.setBackground(Color.red);
			log.println("Sensor OFF "+dateFormat.format(new Date()));
	    }
	    
		private void threadSleep(long time) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		private boolean checkTemp(){
			int temperature = getTemp();
			cmd2.setBackground(Color.red);
			cmd6.setBackground(Color.red);
			System.out.println("APXS: current temperature is "+temperature+"'C");
			area.append("\n Current temperature is "+temperature+"'C");
			if(temperature<(-40) && temperature >(-85)){
				log.println("Temperature is -"+temperature+"'C "+dateFormat.format(new Date()));
				area.append("\n The temperature is compatible for APXS to work");
				con_Sensor_ON();
				return true;
				
			}
			else{
				area.append("\n Temperature is not favorable for APXS to work. Will try after one hour.");
				log.println("Temperature not compatible : -"+ temperature +"'C "+dateFormat.format(new Date()));
				return false;
			}			
		}
		 
		private void con_Sensor_ON(){
			System.out.println("APXS: Sensor is ON [as it is in working condition (temperature is between -40'c to -85'c)].");
			threadSleep(2000);
			area.append("\n Sensor is ON");
			cmd3.setBackground(Color.red);
			cmd2.setBackground(Color.red);
			cmd6.setBackground(Color.red);
			log.println("Sensor ON "+dateFormat.format(new Date()));
		}

	    private boolean isOn(){
	        return state;
	    }
	    
	    private int getTemp(){
	    	Random rand = new Random();
			int randVal = rand.nextInt(180)-100;
			return randVal;
	    }


		public void run() {
			if(!isOn()){
				System.out.println("APXS: APXS is not on. Please turn on APXS first.");
				area.append("\n APXS is not ON. Please turn on the APXS first");
				return;
			}
			while(!checkTemp()){
				try {
					System.out.println("APXS: current temperature is not in the working condition. Check temperature after one hour");
					area.append("\n Current temperature is not in working condition. Check temperature after one hour");
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
	
			con_Sensor_ON();
			System.out.println("APXS: APXS is gathering the data");
			area.append("\n APXS is gathering data..");
		}
	
		public Object runCommand(String message) {
			if(flag2==0){
				JLabel imageLabel = null;
	            try {
	                URL url = new URL("http://i.imgur.com/WOaPXig.gif");
	                Icon image = new ImageIcon(url);
	                imageLabel = new JLabel(image);
	                
	            } catch (IOException ex) {
	                System.out.println("");
	            }
	            
			frame = new JFrame( "Mars rover" );
	        frame.setLayout( new GridLayout( 3,0 ) );
	        frame.setBounds(10, 30, 0, 0);
	        frame.getContentPane().setBackground( new Color(255,255,255) );
	        JPanel buttons = new JPanel(new FlowLayout());
	        
	        buttons.setBackground( new Color(255,255,255) );
	         cmd1 = new JButton( "APXS ON" );
	        cmd1.setHorizontalTextPosition( SwingConstants.LEFT );
	       // cmd1.setPreferredSize(new Dimension(100,100));
	        buttons.add(cmd1);
	        
	       cmd2 = new JButton( "Check Temperature" );
	        cmd2.setHorizontalTextPosition( SwingConstants.LEFT );
	        buttons.add(cmd2);
	         cmd3 = new JButton( "Sensor ON" );
	        cmd3.setHorizontalTextPosition( SwingConstants.LEFT );
	        buttons.add(cmd3);
	        cmd6 = new JButton("Log");
	        cmd6.setHorizontalTextPosition( SwingConstants.LEFT );
	        buttons.add(cmd6);
	        cmd4 = new JButton( "Sensor OFF" );
	        cmd4.setHorizontalTextPosition( SwingConstants.LEFT );
	        buttons.add(cmd4);
	        cmd5 = new JButton( "APXS OFF" );
	        cmd5.setHorizontalTextPosition( SwingConstants.LEFT );
	        buttons.add(cmd5);
	       
	
	        area = new JTextArea();
	        area.append( " \n Sending request to client \n " );
	        area.setEditable( false );
	        area.setLineWrap( true );
	        JScrollPane sp = new JScrollPane(area);
	        sp.setBackground(Color.black);
	       // frame.add(imageLabel);
	        frame.add(buttons);
	        frame.add( sp );
	        frame.add(imageLabel);
			//frame.add(cmd1.setPreferredSize(new Dimension(100,100)));
	        //frame.add(cmd1);
	        //frame.add(cmd2);
	        frame.setMinimumSize( new Dimension( 650, 340 ) );
	        frame.setResizable(false);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);;
	       // frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	        frame.setVisible( true );
	        frame.setLocationRelativeTo(null);
	        flag2++;
			}
			if (message.equalsIgnoreCase("APXS_ON")){
				
				turnOn();
				return message;
			}else if (message.equalsIgnoreCase("APXS_OFF")){
				turnOff();
				return message;
			}else if (message.equalsIgnoreCase("CHECK_TEMPERATURE")){
				return checkTemp()? "Temperature meets working requirement" : "temperature does not meet working requirement";
			}else if(message.equalsIgnoreCase("APXS_MEASURE")){
				run();
				return message;
			}
			else if(message.equalsIgnoreCase("APXS_SENSOR_ON")){
				con_Sensor_ON();
				return message;
			}
			else if(message.equalsIgnoreCase("APXS_SENSOR_OFF")){
				sensorTurnOff();
				return message;
			}
			else if(message.equalsIgnoreCase("READ_LOG")){
				return getLog();
			}
			else{
				return "command is not recognized";
			}
		}

		private String getLog() {
			String logMessages = "";
			try(BufferedReader br = new BufferedReader(new FileReader(Constants.apxs_logpath))) {
			    StringBuilder sb = new StringBuilder();
			    String line = br.readLine();

			    while (line != null) {
			        sb.append(line);
			        sb.append(System.lineSeparator());
			        line = br.readLine();
			    }
			    logMessages = sb.toString();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return logMessages;
		}
}
	
