package APXS.module;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.sun.org.apache.bcel.internal.generic.CPInstruction;

import json.MyWriter;

public class APXSJson {
	double ev[] ;
	HashMap<String, Double> element = new HashMap<String,Double>();
	
	double energy =380.84;
	double numberOfSamples = (Math.random()*10.00+40);
	int count = (int) numberOfSamples;	
	double umean = (Math.random()*1000);
	double deviation = (Math.random()*50);
	Random random = new Random();
	double offset = (double)(random.nextInt(2)-1);
	double max = (Double)(umean + deviation);
	double	constCross = (Double)(Math.random()*1.0);
	double min = (Double)(umean - deviation);
	double resp = (Double)(Math.random()*2.0);
	
	{
		element.put("Na",1.83);
		element.put("Mg", 7.58);
		element.put("Al", 9.26);
		element.put("Si", 46.3);
		element.put("S", 4.99);
		element.put("cl", 0.63);
		element.put("K", 0.47);
		element.put("Ca", 7.31);
		element.put("Ti", 1.04);
		element.put("Cr", 0.45);
		element.put("Mn", 0.37);
		element.put("Fe",18.5);
		element.put("Ni", 423.00);
		element.put("Br", 32.00);
		//Iterator iterator = element.entrySet().iterator();
		double[] ev = new double[count];
		double[] cps = new double[count];
		for(double d : element.values()){
	for (int j = 0; j < count; j++) {
		cps[j] = (Double) ((offset + (constCross* resp *d)) + 
				((1 - constCross) * resp *   (Math.random()* (max-min) + min)));	
	}
	}
		
	for (int i = 0; i<count;i++){
		ev[i] = energy;
		energy = energy+(Math.random()*10.00+30);
	}
	JSONArray eV = new JSONArray();
	JSONArray countPerSec = new JSONArray();
	for(int j = 0; j < count; j++){
		eV.add(ev[j]);
		countPerSec.add(cps[j]);
	}
	JSONObject obj = new JSONObject();
	obj.put("ev", eV);
	obj.put("countPerSec", countPerSec);
	System.out.println(obj);
	
	MyWriter mywriter = new MyWriter(obj, 9017);
	
	}
}
