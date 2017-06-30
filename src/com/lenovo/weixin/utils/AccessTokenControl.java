package com.lenovo.weixin.utils;

import java.io.IOException;
import java.util.Date;

import org.apache.http.client.ClientProtocolException;
//import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

public class AccessTokenControl {
//	private static Logger logger = Logger.getLogger(AccessTokenControl.class);

	// 向微信服务器请求access_token
	private static String reqAccessToken() throws ClientProtocolException, IOException {

		String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=wx94ba1d7a4712e74b&corpsecret=-V3iBCzfULaIEkJisRsdsFnJ0s_2GhmTFc0DbZuOlDKoMwPEWNGjW-nt-Mws-QF8";

		// {"access_token":"Y0DuiQAnxnHD0g6_oWv3TTZ4uJRKfxSH7f51f1zYT6sMEb_F3HT5gKcBvFvevoEP","expires_in":7200}

		return ClientUtil.get(url);
	}

	// 获取access_token
	public static String getAccessToken() throws ClientProtocolException, IOException {
		EhcacheUtil ehcacheUtil = EhcacheUtil.getEhcacheUtil();
		String accessToken = ehcacheUtil.getCache("access_token");
		String expiresIn = ehcacheUtil.getCache("expires_in");
		String creatTime = ehcacheUtil.getCache("creat_time");
		long nowTime = new Date().getTime();
		if (accessToken == null || (nowTime - Long.valueOf(creatTime)) >= Long.valueOf(expiresIn)) {
			JSONObject json = ParseJSON.getJSON(reqAccessToken());
			ehcacheUtil.remove("access_token");
			ehcacheUtil.remove("expires_in");
			ehcacheUtil.remove("creat_time");
			// EhcacheUtil.putCache(json.getString("access_token"),
			// json.getLong("expires_in"), nowTime);
			ehcacheUtil.putCache("access_token", json.getString("access_token"));
			ehcacheUtil.putCache("expires_in", json.getString("expires_in"));
			ehcacheUtil.putCache("creat_time", String.valueOf(nowTime));

			return ehcacheUtil.getCache("access_token");
		}
//		logger.info("Access_token creattime : " + creatTime);
//		logger.info(nowTime - Long.valueOf(creatTime));
//		logger.info(Long.valueOf(expiresIn));
		return accessToken;
	}
	
	private static String reqBaiduAccessToken() throws ClientProtocolException, IOException {

		String url = "https://openapi.baidu.com/oauth/2.0/token?grant_type=client_credentials&client_id=UWFZBNEvD9OAFO6ZLdgVuSx8&client_secret=gGgN3v8LBwFrMazQadpy4SxWtQG5mGXq&";
		// {"access_token":"Y0DuiQAnxnHD0g6_oWv3TTZ4uJRKfxSH7f51f1zYT6sMEb_F3HT5gKcBvFvevoEP","expires_in":7200}

		return ClientUtil.post(url,"");
	}
	
	public static String getBaiduAccessToken() throws ClientProtocolException, IOException{
		EhcacheUtil ehcacheUtil = EhcacheUtil.getEhcacheUtil();
		String accessToken = ehcacheUtil.getCache("baidu_access_token");
		String expiresIn = ehcacheUtil.getCache("expires_in");
		String creatTime = ehcacheUtil.getCache("creat_time");
		long nowTime = new Date().getTime();
		if (accessToken == null || (nowTime - Long.valueOf(creatTime)) >= Long.valueOf(expiresIn)) {
			JSONObject json = ParseJSON.getJSON(reqBaiduAccessToken());
			ehcacheUtil.remove("baidu_access_token");
			ehcacheUtil.remove("expires_in");
			ehcacheUtil.remove("creat_time");
			// EhcacheUtil.putCache(json.getString("access_token"),
			// json.getLong("expires_in"), nowTime);
			ehcacheUtil.putCache("baidu_access_token", json.getString("access_token"));
			ehcacheUtil.putCache("expires_in", json.getString("expires_in"));
			ehcacheUtil.putCache("creat_time", String.valueOf(nowTime));

			return ehcacheUtil.getCache("baidu_access_token");
		}
//		logger.info("Access_token creattime : " + creatTime);
//		logger.info(nowTime - Long.valueOf(creatTime));
//		logger.info(Long.valueOf(expiresIn));
		return accessToken;
	}
}
