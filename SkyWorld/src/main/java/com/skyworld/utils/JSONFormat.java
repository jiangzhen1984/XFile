package com.skyworld.utils;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.json.JSONTokener;

public class JSONFormat {

	public static Map<String, JSONObject> parse(String data) {
		JSONTokener jsonParser = new JSONTokener(data);
		JSONObject request = (JSONObject) jsonParser.nextValue();
		try {
			JSONObject header = request.getJSONObject("header");
			JSONObject body = request.getJSONObject("body");
			if (header == null || body == null) {
				return null;
			}
			Map<String, JSONObject> map = new HashMap<String, JSONObject>();
			map.put("header", header);
			map.put("body", body);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
