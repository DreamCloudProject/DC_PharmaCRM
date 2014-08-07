package ru.dreamcloud.alexion.zk.integration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.json.JSONArray;
import org.zkoss.json.JSONObject;
import org.zkoss.json.parser.JSONParser;

public class FiasBasicDataLoader {
	
	private static String REST_SERVICE_URL = "http://basicdata.ru/api/json/fias/addrobj/";
	
	public FiasBasicDataLoader() {
		// TODO Auto-generated constructor stub
	}
	
	public List<String> getRussianRegions(){
		JSONParser json = new JSONParser();
		List<String> regionsList = null;
		try {
			InputStream is = new URL(REST_SERVICE_URL).openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			JSONObject obj = (JSONObject)json.parse(rd);
			JSONArray regions = (JSONArray)obj.get("data");
			regionsList = new ArrayList<String>();
			for (Object reg : regions.toArray()) {
				JSONObject jsonObj = (JSONObject)reg;
				String regionTitle = jsonObj.get("formalname") + " " + jsonObj.get("shortname");
				regionsList.add(regionTitle);
			}			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return regionsList;
	}
}
