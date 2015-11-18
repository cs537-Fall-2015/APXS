package APXS.module;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import json.Constants;

public class APXS {
		private final static boolean ON = true;
		private final static boolean OFF = false;
		static PrintWriter log;
		private boolean state;
		static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		static Date date = new Date();
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
	    public void turnOff(){
			state = OFF;
			System.out.println("APXS: APXS is turning off");
			log.println("APXS OFF -" +dateFormat.format(date));
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
	    	log.println("APXS turned ON "+dateFormat.format(date));
	    }
	    
	    public void sensorTurnOff(){
	    	System.out.println("APXS: Sensor is OFF.");
			log.println("Sensor OFF "+dateFormat.format(date));
			
	    }
		public boolean checkTemp(){
			int temperature = getTemp();
			System.out.println("APXS: current temperature is "+temperature+"'C");
			if(temperature<(-40) && temperature >(-85)){
				log.println("Temperature is -"+temperature+"'C "+dateFormat.format(date));
				con_Sensor_ON();
				return true;
				
			}
			else{
				log.println("Temperature not compatible : -"+ temperature +"'C "+dateFormat.format(date));
				return false;
			}			
		}
		 
		public  static void con_Sensor_ON(){
			System.out.println("APXS: Sensor is ON [as it is in working condition (temperature is between -40'c to -85'c)].");
			log.println("Sensor ON "+dateFormat.format(date));
			
		}

	    public boolean isOn(){
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
				return;
			}
			while(!checkTemp()){
				try {
					System.out.println("APXS: current temperature is not in the working condition. Check temperature after one hour");
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			con_Sensor_ON();
			System.out.println("APXS: APXS is gathering the data");
		}

		public Object runCommand(String message) {
			if (message.equalsIgnoreCase("APXS_ON")){
				turnOn();
				return message;
			}else if (message.equalsIgnoreCase("APXS_OFF")){
				turnOff();
				return message;
			}else if (message.equalsIgnoreCase("APXS_CHECKTEMP")){
				checkTemp();
				return message;
			}else if(message.equalsIgnoreCase("APXS_RUN")){
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
			else{
				return "command is not recognized";
			}
		}
	
	    }
	
