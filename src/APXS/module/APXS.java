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
			System.out.println("APXS Module: APXS is turning off");
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
	    	System.out.println("APXS Module: APXS is turning on");
	    	log.println("APXS turned ON "+dateFormat.format(date));
			
	    }
		public static void apxs_checkTemp(){
			Random rand = new Random();
			int hour = 1;
			int randVal = rand.nextInt(85)+1;
			if(randVal<40 || randVal>85){
				System.out.println("APXS : APXS will restart after one hour as it is not in working condition right now. Temperature is "+randVal+"'c");
				log.println("Temperature not compatible : -"+ randVal +"'C "+dateFormat.format(date));
				System.out.println("After "+hour+" hour");
				apxs_checkTemp();
			}
			else{
				System.out.println("APXS: current temperature is -"+randVal+"'C");
				log.println("Temperature is -"+randVal+"'C "+dateFormat.format(date));
				apxs_con_Sensor_ON();

			}
			hour++;
			
		}
		 
		public  static void apxs_con_Sensor_ON(){
			System.out.println("APXS: Sensor is ON [as it is in working condition (temperature is between -40'c to -85'c)].");
			log.println("Sensor ON "+dateFormat.format(date));
			
		}

	    public boolean isOn(){
	        return state;
	    }
	    
	    private int getTemp(){
	    	Random rand = new Random();
			int randVal = rand.nextInt(40)+1;
			return randVal;
	    }
//	    	    

		public void run() {
			//check temperature
			//if the temperature is within the working range, run the measurement
			//else wait for "an hour" then do run again
			System.out.println("APXS Module: APXS is gathering the data");
		}
	
	    }
	
