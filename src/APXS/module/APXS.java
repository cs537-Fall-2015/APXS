package APXS.module;

import java.util.HashMap;
import java.util.Random;

public class APXS {
		private final static boolean ON = true;
		private final static boolean OFF = false;
		
		private boolean state;
	    
	    private HashMap<Long, Double> data = new HashMap<Long, Double>();
	    
	    
	    public void turnOff(){
			state = OFF;
			System.out.println("APXS Module: APXS is turning off");
		}

		public APXS() {
			state = OFF;
	    }
	    
	    // Change state
	    public void turnOn(){
	    	state = ON;
	    	System.out.println("APXS Module: APXS is turning on");
	    }
		public static void apxs_checkTemp(){
			Random rand = new Random();
			int hour = 1;
			int randVal = rand.nextInt(85)+1;
			if(randVal<40 || randVal>85){
				System.out.println("APXS : APXS will restart after one hour as it is not in working condition right now. Temperature is "+randVal+"'c");
				System.out.println("After "+hour+" hour");
				apxs_checkTemp();
			}
			else{
				System.out.println("APXS: current temperature is -"+randVal+"'C");
				apxs_con_Sensor_ON();
			}
			hour++;
			
		}
		 
		public  static void apxs_con_Sensor_ON(){
			System.out.println("APXS: Sensor is ON [as it is in working condition (temperature is between -40'c to -85'c)].");
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
