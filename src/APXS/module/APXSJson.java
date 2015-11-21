package APXS.module;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.sun.org.apache.bcel.internal.generic.CPInstruction;

import json.MyWriter;

public class APXSJson {
	//double ev[] ;
	HashMap<String, Double> element = new HashMap<String,Double>();
	Random random;

	public APXSJson(int number){
		random = new Random();
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
		JSONArray elementarray = new JSONArray();
		for(String key : element.keySet()){
			JSONObject ele = new JSONObject();
			ele.put("Name", key);
			ele.put("elementWeight", element.get(key));
			ele.put("offset", randomOffset());
			ele.put("resp", randomResp());
			ele.put("numberOfSamples", randomNumberOfSamples());
			ele.put("const", randomConst());
			ele.put("umean", randomUmean());
			ele.put("deviation", randomDev());
			elementarray.add(ele);
		}
		
		JSONObject obj = new JSONObject();
		obj.put("Spectrum", "B011_CS");
		obj.put("Type", "SU");
		obj.put("element", elementarray);
		MyWriter mywriter = new MyWriter(obj, number);
	}

	private double randomDev() {
		return (Math.random()*50);
	}

	private double randomUmean() {
		return (Math.random()*1000);
	}

	private double randomConst() {
		return (Math.random()*1.0);
	}

	private int randomNumberOfSamples() {
		return random.nextInt(40);
	}

	private double randomResp() {
		return Math.random()*2.0;
	}

	private double randomOffset() {
		return Math.random()*2 - 1;
	}
}

