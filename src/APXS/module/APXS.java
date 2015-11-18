package APXS.module;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
	    	threadSleep(3000);
			log.println("APXS OFF -" +dateFormat.format(new Date()));
			log.println("");
			log.close();
		}

		public APXS() {
			state = OFF;
	    }
	    
	    // Change state
	    private void turnOn(){
	    	state = ON;
	    	System.out.println("APXS: APXS is turning on");
	    	threadSleep(3000);
	    	log.println("APXS turned ON "+dateFormat.format(new Date()));
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
			System.out.println("APXS: current temperature is "+temperature+"'C");
			if(temperature<(-40) && temperature >(-85)){
				log.println("Temperature is -"+temperature+"'C "+dateFormat.format(new Date()));
				return true;
			}
			else{
				log.println("Temperature not compatible : -"+ temperature +"'C "+dateFormat.format(new Date()));
				return false;
			}			
		}
		 
		private void con_Sensor_ON(){
			System.out.println("APXS: Sensor is ON [as it is in working condition (temperature is between -40'c to -85'c)].");
	    	threadSleep(2000);
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

		private void run() {
			if(!isOn()){
				System.out.println("APXS: APXS is not on. Please turn on APXS first.");
				return;
			}
			while(!checkTemp()){
				System.out.println("APXS: current temperature is not in the working condition. Check temperature after one hour");
			    threadSleep(3000);
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
			}else if (message.equalsIgnoreCase("APXS_MEASURE")){
				run();
				return message;
			}else if (message.equalsIgnoreCase("CHECK_TEMPERATURE")){
				return checkTemp()? "temperature meets working requirement" : "temperature does not meet the working requirement";
			}else if (message.equalsIgnoreCase("READ_LOG")){
				return getLog();
			}else{
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
	
