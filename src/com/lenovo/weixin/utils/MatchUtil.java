package com.lenovo.weixin.utils;

import java.io.IOException;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

public class MatchUtil {
	private static LoadConfig lc;
	private static Logger logger = Logger.getLogger(MatchUtil.class);

	public static String codeMatch(String code) throws IOException {
		String flag = "false";
		lc = new LoadConfig("conf.properties");
		String userInfrom = ClientUtil
				.get(lc.getProperty("codeUrl") + AccessTokenControl.getAccessToken() + "&code=" + code);
		JSONObject json = ParseJSON.getJSON(userInfrom);
		try {
			if (json.getString("UserId") != null) {
				return json.getString("UserId");
			} else {
				return flag;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return flag;
		}
	}

	public static String loginMatch(String code) throws IOException {
		String flag = "false";
		lc = new LoadConfig("conf.properties");
		String jsonStr = "{\"auth_code\":\"" + code + "\"}";
		String post = ClientUtil.post(lc.getProperty("loginUrl"), jsonStr);
		JSONObject json = ParseJSON.getJSON(post);
		if (json.getInt("usertype") == 1) {
			String email = json.getJSONObject("user_info").getString("email");
			if (email != null) {
				flag = "yuhao5";
			}
		} else {
			String userid = json.getJSONObject("user_info").getString("userid");
			if(userid != null){
				flag =  userid;
			}
		}
		return flag;
	}

}
