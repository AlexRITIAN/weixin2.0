package com.lenovo.weixin.function.impl;

import com.lenovo.weixin.function.Cinterface;
import com.lenovo.weixin.utils.EhcacheUtil;
import com.lenovo.weixin.utils.ParseJSON;

import net.sf.json.JSONObject;

public class JenkinsFunction implements Cinterface {
	private EhcacheUtil ehcacheUtil = EhcacheUtil.getEhcacheUtil();

	@Override
	public String run(JSONObject json) throws Exception {
		String msg = json.getString("msg");
		JSONObject msgJson = ParseJSON.getJSON(msg);
		ehcacheUtil.putCache("names", msgJson.getString("names"));
		ehcacheUtil.putCache("jobs", msgJson.getJSONArray("jobs").toString());
		ehcacheUtil.putCache("views_names", msgJson.getString("views_names"));
		ehcacheUtil.putCache("views", msgJson.getJSONArray("views").toString());
		return null;
	}


}
