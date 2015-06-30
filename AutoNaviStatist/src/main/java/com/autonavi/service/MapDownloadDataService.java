package com.autonavi.service;

import org.json.JSONArray;
import org.json.JSONObject;

public class MapDownloadDataService {
	
	private ThreadLocal localLock = new ThreadLocal();

	public MapDownloadDataService() {
	}

	
	public JSONObject getMapData() {
		JSONArray ret = new JSONArray();
		JSONObject zj = new JSONObject();
		zj.put("code", "ZJ");
		zj.put("value", "45");
		zj.put("dataLabels", "浙江");
		ret.put(zj);
		
		JSONObject sh = new JSONObject();
		sh.put("code", "SH");
		sh.put("value", "45");
		sh.put("dataLabels", "上海");
		ret.put(sh);
		
		JSONObject data = new JSONObject();
		data.put("retcode" , 0);
		data.put("data", ret);
		return data;
	}
}
