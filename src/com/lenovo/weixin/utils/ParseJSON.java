package com.lenovo.weixin.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ParseJSON {
	public static JSONObject getJSON(String JSONStr){
		JSONObject jsonObj = JSONObject.fromObject(JSONStr);
		return jsonObj;
	}
	
	public static JSONArray getJSONArray(String jsonStr){
		return JSONArray.fromObject(jsonStr);
	}
}
