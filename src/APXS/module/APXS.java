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
			System.out.println("APXS: APXS is turning off");
		}

		public APXS() {
			state = OFF;
	    }
	    
	    // Change state
	    public void turnOn(){
	    	state = ON;
	    	System.out.println("APXS: APXS is turning on");
	    }
		public boolean checkTemp(){
			int temperature = getTemp();
			System.out.println("APXS: current temperature is "+temperature+"'C");
			if(temperature<(-40) && temperature >(-85)){
				return true;
			}
			else{
				return false;
			}
		}
		 
		public  static void con_Sensor_ON(){
			System.out.println("APXS: Sensor is ON [as it is in working condition (temperature is between -40'c to -85'c)].");
		}

	    public boolean isOn(){
	        return state;
	    }
	    
	    private int getTemp(){
	    	Random rand = new Random();
			int randVal = rand.nextInt(180)-100;
			return randVal;
	    }
//	    	    

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
			}else if (message.equalsIgnoreCase("APXS_RUN")){
				run();
				return message;
			}else{
				return "command is not recognized";
			}
		}
	}
