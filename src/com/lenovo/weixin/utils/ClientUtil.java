package com.lenovo.weixin.utils;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
//import org.apache.log4j.Logger;


public class ClientUtil {
//	private static Logger logger = Logger.getLogger(ClientUtil.class);
	//https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=wx94ba1d7a4712e74b&corpsecret=-V3iBCzfULaIEkJisRsdsFnJ0s_2GhmTFc0DbZuOlDKoMwPEWNGjW-nt-Mws-QF8
	//{"access_token":"Y0DuiQAnxnHD0g6_oWv3TTZ4uJRKfxSH7f51f1zYT6sMEb_F3HT5gKcBvFvevoEP","expires_in":7200}
	
	public static String get(String url) throws ClientProtocolException, IOException{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet get = new HttpGet(url);
		CloseableHttpResponse response = httpClient.execute(get);
		HttpEntity entity = response.getEntity();
		String entityStr = EntityUtils.toString(entity);
//		logger.info(entityStr);
		httpClient.close();
		return entityStr;
	}
	
	// "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token="
	/*{
			   "touser": "UserID1|UserID2|UserID3",
			   "toparty": " PartyID1 | PartyID2 ",
			   "totag": " TagID1 | TagID2 ",
			   "msgtype": "text",
			   "agentid": 1,
			   "text": {
			       "content": "Holiday Request For Pony(http://xxxxx)"
			   },
			   "safe":0
			}*/
	
	public static String post(String url,String jsonStr) throws ClientProtocolException, IOException{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
//		logger.info(jsonStr);
		post.addHeader("Content-type","application/json; charset=utf-8");  
        post.setHeader("Accept", "application/json");  
		post.setEntity(new StringEntity(jsonStr, Charset.forName("UTF-8")));
		CloseableHttpResponse response = httpClient.execute(post);
		HttpEntity entity = response.getEntity();
		String entityStr = EntityUtils.toString(entity);
//		logger.info(entityStr);
		httpClient.close();
		return entityStr;
	}
	
	public static CloseableHttpResponse getMedia(String url) throws ClientProtocolException, IOException{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet get = new HttpGet(url);
		return httpClient.execute(get);
	}
}
