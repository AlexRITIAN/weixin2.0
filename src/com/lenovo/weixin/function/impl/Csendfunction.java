package com.lenovo.weixin.function.impl;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.lenovo.weixin.function.Cinterface;
import com.lenovo.weixin.utils.AccessTokenControl;
import com.lenovo.weixin.utils.ClientUtil;
import com.lenovo.weixin.utils.EhcacheUtil;
import com.lenovo.weixin.utils.LoadConfig;

import net.sf.json.JSONObject;

public class Csendfunction implements Cinterface {
	private static Logger logger = Logger.getLogger(Csendfunction.class);
	private EhcacheUtil ehcacheUtil = EhcacheUtil.getEhcacheUtil();

	@Override
	public String run(JSONObject json) throws IOException {
		LoadConfig lc = new LoadConfig("conf.properties");
		if ("text".equals(json.getString("type"))) {
			return text(json, lc);
		} else if ("news".equals(json.getString("type"))) {
			return news(json, lc);
		}
		return null;
	}

	private String text(JSONObject json, LoadConfig lc) throws IOException {
		String url = lc.getProperty("sendUrl");
		String taget = "touser";
		String tagetID = "@all";

		if (Integer.valueOf(json.getString("tagetType")) == 2) {
			taget = "toparty";
		} else if (Integer.valueOf(json.getString("tagetType")) == 3) {
			taget = "totag";
		}
		if (json.getString("taget").equals("2")) {
			tagetID = json.getString("tagetID");
		}

		String jsonStr = "{\"" + taget + "\":\"" + tagetID
				+ "\",\"msgtype\":\"text\",\"agentid\":2,\"text\":{\"content\":\"" + json.getString("msg") + "\"}}";
		return ClientUtil.post(url + AccessTokenControl.getAccessToken(), jsonStr);
	}

	/*
	 * { "touser": "UserID1|UserID2|UserID3", "toparty": " PartyID1 | PartyID2 "
	 * , "totag": " TagID1 | TagID2 ", "msgtype": "news", "agentid": 1, "news":
	 * { "articles":[ { "title": "Title", "description": "Description", "url":
	 * "URL", "picurl": "PIC_URL" }, { "title": "Title", "description":
	 * "Description", "url": "URL", "picurl": "PIC_URL" } ] } }
	 */
	private String news(JSONObject json, LoadConfig lc) throws IOException {
		String url = lc.getProperty("sendUrl");
		String taget = "touser";
		String tagetID = "@all";

		if (Integer.valueOf(json.getString("tagetType")) == 2) {
			taget = "toparty";
		} else if (Integer.valueOf(json.getString("tagetType")) == 3) {
			taget = "totag";
		}
		if (json.getString("taget").equals("2")) {
			tagetID = json.getString("tagetID");
		}

		//String sInfrom = json.getString("description").replace(" ", "").replace(".", "").replace("-", "").replace(":",
//				"");
		String jsonStr = "{" + "\"" + taget + "\":\"" + tagetID + "\"," + "\"msgtype\":\"news\"," + "\"agentid\":2,"
				+ "\"news\":" + "{\"articles\":[{" + "\"title\":\"" + json.getString("title") + "\","
				+ "\"description\":\"" + json.getString("description") + "\"," + "\"picurl\":\""
				+ json.getString("picurl") + "\"" + "}]}}";
		if ("server_status".equals(json.getString("title"))) {
			// logger.info(jsonStr);
			ehcacheUtil.putCache("server_status_infrom", json.getString("description"));
//			logger.info(ehcacheUtil.getCache("server_status_infrom"));
			return "server_status achieved";
		}
		logger.info(jsonStr);
		return ClientUtil.post(url + AccessTokenControl.getAccessToken(), jsonStr);
	}
}
