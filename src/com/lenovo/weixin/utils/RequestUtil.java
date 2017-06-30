package com.lenovo.weixin.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;

import com.lenovo.weixin.beans.TextMessageBean;

import net.sf.json.JSONObject;

public class RequestUtil {
	private static Logger logger = Logger.getLogger(RequestUtil.class);
	private static EhcacheUtil ehcacheUtil = EhcacheUtil.getEhcacheUtil();
	
	public static String processRequest(String xmlStr) throws IOException, DocumentException {
		Map<String, String> messageMap = null;
		messageMap = MessageUtil.resolverXML(xmlStr);

		TextMessageBean textMessage = new TextMessageBean();

		textMessage.setFromUserName(messageMap.get("ToUserName"));
		textMessage.setToUserName(messageMap.get("FromUserName"));
		textMessage.setCreateTime(messageMap.get("CreateTime"));
		textMessage.setMsgType("text");

		if (messageMap.get("MsgType").equals("text")) {
			text(messageMap, textMessage);
		} else if (messageMap.get("MsgType").equals("event")) {
			logger.info(messageMap.toString());
			click(messageMap, textMessage);
		} else {
			textMessage.setContent("Parse Request Failed :(");
		}
		return MessageUtil.textMeassageToXML(textMessage);

	}

	private static void click(Map<String, String> messageMap, TextMessageBean textMessage) {

		if (messageMap.get("Event").equals("click")) {
			if (messageMap.get("EventKey").equals("server_status")) {
				//textMessage.setContent("服务正在开发中......");
				ehcacheUtil.putCache("order", "server_status");
			} else {
				textMessage.setContent("Wrong Key :(");
			}
		}
	}

	private static void text(Map<String, String> messageMap, TextMessageBean textMessage)
			throws ClientProtocolException, IOException {

		logger.info("FromUserName : " + textMessage.getFromUserName());
		logger.info("ToUserName : " + textMessage.getToUserName());
		logger.info(messageMap.get("MsgType"));

		String content = messageMap.get("Content");
		if (content.equals("重启") || content.equals("restart")) {
			List<String> rules = new ArrayList<>();
			rules.add("2");
			String url = "https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token="
					+ AccessTokenControl.getAccessToken() + "&userid=" + messageMap.get("FromUserName");
			JSONObject json = ParseJSON.getJSON(ClientUtil.get(url));
//			logger.info(json.getString("department"));
			boolean flag = UserUtil.checkDeptement(json.getString("department"), rules);
			if (flag) {
				textMessage.setContent("命令已发送");
				ehcacheUtil.putCache("order", "restart");
			} else {
				textMessage.setContent("没有权限!");
			}
		} else {
			textMessage.setContent("o(︶︿︶)o 唉,无效指令");
		}
//		logger.info(textMessage);

	}

}
