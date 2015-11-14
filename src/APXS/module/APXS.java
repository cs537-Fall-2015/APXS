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
			System.out.println("APXS Module : APXS is turning off");
		}

		public APXS() {
			state = OFF;
	    }
	    
	    // Change state
	    public void turnOn(){
	    	state = ON;
	    	System.out.println("APXS Module : APXS is turning on");
	    }
	    
	    public boolean isOn(){
	        return state;
	    }
	    
	  //  private int getTemp(){
	   // 	Random rand = new Random();
	//		int randVal = rand.nextInt(40)+1;
	//		return randVal;
	 //   }
//	    	    

		public void run() {
			//check temperature
			//if the temperature is within the working range, run the measurement
			//else wait for "an hour" then do run again
			System.out.println("APXS Module : APXS is gathering the data");
		}

		public int checktemparature() {
			// TODO Auto-generated method stub
			Random rand = new Random();
			int randVal = rand.nextInt(40)+1;
			return randVal;
		}
	}
