package com.events.parser;

import com.events.IEvent;
import com.google.gson.Gson;


/**
 * 
 * Wrapper class for converting JSON to POJO and reverse.
 * Currently using Google's GSON library.
 * 
 * @author gimu2
 *
 */
public class JSONParser {
	
	private Gson gson;
	
	public JSONParser() {
		gson = new Gson();
	}
	
	public String toJson(IEvent obj) {
		
		return gson.toJson(obj);
	}
	
	public IEvent fromJson(String json, Class<?> c) {
		
		return (IEvent)gson.fromJson(json, c);
	}

}
