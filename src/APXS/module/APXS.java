package APXS.module;

import java.util.Calendar;
import java.util.HashMap;

public class APXS {

	    
	    private final static double POWER_LEVEL_ON = 4.2;
	    
	    private final static double POWER_LEVEL_SLEEP = 1.2;
	    
	    private final static double POWER_LEVEL_OFF = 0.0;
	    
	    public final static double MIN_RADIATION = 0.1;
	    
	    public final static double MAX_RADIATION = Math.pow(10, 4);
	    
	    private double powerLevel;
	    
	    private HashMap<Long, Double> data = new HashMap<Long, Double>();
	    
	    private String state = "APXS_OFF";
	    
	    public APXS() {
	    }
	    
	    // Change state
	    
	    public void off() {
	        setState("APXS_OFF");
	    }
	    
	    public void APXS_CHECK_TEMP() {
	        setState("APXS_CHECK_TEMPERATURE");
	    }
	    
	    public void APXS_SET_TEMP() {
	        setState("APXS_SET_TEMPERATURE");
	    }
	    
//	    public void checkout() {
//	        setState("APXS_CHECKOUT");
//	    }
//	    
	    public void shutdown() {
	        setState("APXS_SHUTDOWN");
	    }
	    
	    public void sleep() {
	        setState("APXS_SLEEP");
	    }
//	    
//	    // Rover interaction
//	    
	    public boolean isOn() {
	        return !state.equals("APXS_OFF");
	    }
//	    
	    public boolean isSleeping() {
	        return state.equals("APXS_SLEEP");
	    }
	    
	    public boolean isAPXS() {
	        return state.equals("APXS_SCIENCE");
	    }
//	    
//	    public double getPowerConsumption() {
//	        if (state.equals("APXS_OFF")) {
//	            return POWER_LEVEL_OFF;
//	        }
//	        if (state.equals("APXS_SLEEP")) {
//	            return POWER_LEVEL_SLEEP;
//	        } else {
//	            return POWER_LEVEL_ON;
//	        }
//	    }
	    
	    public void addMeasurement(Double radiationLevel) {
	        data.put(Calendar.getInstance().getTimeInMillis(), radiationLevel);
	    }
	    
	    public void clearData() {
	        data.clear();
	    }
	    
	    // Getters/Setters
	    
	    public HashMap<Long, Double> getData() {
	        if (state.equals("APXS_CHECKOUT")) {
	            return data;
	        } else {
	            return null;
	        }
	    }
	    
	    public void setData(HashMap<Long, Double> data) {
	        this.data = data;
	    }
	    
	    public String getState() {
	        return state;
	    }
	    
	    public void setState(String state) {
	        this.state = state;
	        
//	        setPowerLevel(getPowerConsumption());
	    }
	    
	    public double getPowerLevel() {
	        return powerLevel;
	    }
	    
	    public void setPowerLevel(double powerLevel) {
	        this.powerLevel = powerLevel;
	    }
	    
	}
