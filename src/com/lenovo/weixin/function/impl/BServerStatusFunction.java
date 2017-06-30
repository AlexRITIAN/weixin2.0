package com.lenovo.weixin.function.impl;

import org.apache.log4j.Logger;

import com.lenovo.weixin.function.Binterface;
import com.lenovo.weixin.utils.EhcacheUtil;
import com.lenovo.weixin.utils.ParseJSON;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class BServerStatusFunction implements Binterface {
	private static Logger logger = Logger.getLogger(BServerStatusFunction.class);
	private EhcacheUtil ehcacheUtil = EhcacheUtil.getEhcacheUtil();

	@Override
	public String run(String type) {
		String date = null;
		if("all".equals(type)){
			date = getAll();
		}else if("red".equals(type)){
			date = getRed();
		}else if("restart".equals(type)){
			date = getView("Restart");
		}else if("upload".equals(type)){
			date = getView("Upload");
		}
		logger.info("date : " + date);
		return date;
	}

	private String getAll() {
		
		return ehcacheUtil.getCache("jobs");
	}

	private String getRed(){
		String jobStr = ehcacheUtil.getCache("jobs");
		JSONArray jobs = ParseJSON.getJSONArray(jobStr);
		StringBuffer buffer = new StringBuffer();
		buffer.append("[");
		for (Object jobObj : jobs) {
			JSONObject job = (JSONObject)jobObj;
			if("red".equals(job.getString("color"))){
				buffer.append("{\'name\':\'" + job.getString("name") + "\',\'color\':\'" + job.getString("color") + "\'},");
			}
		}
		buffer.append("{}]");
		return buffer.toString();
	}
	
	private String getView(String viewName){
		String viewStr = ehcacheUtil.getCache("views");
		JSONArray views = ParseJSON.getJSONArray(viewStr);
		StringBuffer buffer = new StringBuffer();
		buffer.append("[");
		for (Object viewObj : views) {
			JSONObject view = (JSONObject)viewObj;
			if(viewName.equals(view.getString("name"))){
				JSONArray jobs = view.getJSONArray("jobs");
				for (Object jobObj : jobs) {
					JSONObject job = (JSONObject) jobObj;
					buffer.append("{\'name\':\'" + job.getString("name") + "\',\'color\':\'" + job.getString("color") + "\'},");
				}
			}
		}
		buffer.append("{}]");
		return buffer.toString();
	}
}
