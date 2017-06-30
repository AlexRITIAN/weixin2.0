package com.lenovo.weixin.function.impl;

import com.lenovo.weixin.function.Cinterface;
import com.lenovo.weixin.utils.EhcacheUtil;
import com.lenovo.weixin.utils.ParseJSON;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AlarmLastTimeFunction implements Cinterface {
	private EhcacheUtil ehcacheUtil = EhcacheUtil.getEhcacheUtil();

	@Override
	public String run(JSONObject json) throws Exception {
		String msg = json.getString("msg");
		JSONArray jsonArray = ParseJSON.getJSONArray(msg);
		for (Object obj : jsonArray) {
			JSONObject lastTimeJson = (JSONObject)obj;
			ehcacheUtil.putCache(lastTimeJson.getString("name"),lastTimeJson.getString("date") );
		}
		return null;
	}

}
