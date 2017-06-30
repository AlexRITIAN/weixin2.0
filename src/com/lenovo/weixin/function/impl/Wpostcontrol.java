package com.lenovo.weixin.function.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.apache.log4j.Logger;

import com.lenovo.weixin.beans.ItemBean;
import com.lenovo.weixin.beans.LinkHeadBean;
import com.lenovo.weixin.beans.NewsMessageBean;
import com.lenovo.weixin.beans.TextMessageBean;
import com.lenovo.weixin.function.Winterface;
import com.lenovo.weixin.utils.AccessTokenControl;
//import com.lenovo.weixin.utils.Base64;
import com.lenovo.weixin.utils.ClientUtil;
import com.lenovo.weixin.utils.EhcacheUtil;
import com.lenovo.weixin.utils.LoadConfig;
import com.lenovo.weixin.utils.MessageUtil;
import com.lenovo.weixin.utils.ParseJSON;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Wpostcontrol implements Winterface {
	private EhcacheUtil ehcacheUtil = EhcacheUtil.getEhcacheUtil();
	private Logger logger = Logger.getLogger(Wpostcontrol.class);

	@Override
	public String run(LinkHeadBean linkHead, String data) throws Exception {
		LoadConfig lc = new LoadConfig("conf.properties");
		WXBizMsgCrypt wx = new WXBizMsgCrypt(lc.getProperty("token"), lc.getProperty("encodingAesKey"),
				lc.getProperty("corpId"));
		String xmlStr = wx.DecryptMsg(linkHead.getMsg_signature(), linkHead.getTimestamp(), linkHead.getNonce(), data);
		Map<String, String> xmlMap = MessageUtil.resolverXML(xmlStr);
		if ("text".equals(xmlMap.get("MsgType"))) {
			return wx.EncryptMsg(text(xmlMap.get("Content"), xmlMap.get("ToUserName"), xmlMap.get("FromUserName")),
					linkHead.getTimestamp(), linkHead.getNonce());
		} else if ("event".equals(xmlMap.get("MsgType"))) {
			if ("enter_agent".equals(xmlMap.get("Event"))) {
				return wx.EncryptMsg(event(xmlMap.get("ToUserName"), xmlMap.get("FromUserName")),
						linkHead.getTimestamp(), linkHead.getNonce());
			} else if ("click".equals(xmlMap.get("Event"))) {
				if ("jenkins_red".equals(xmlMap.get("EventKey"))) {
					return wx.EncryptMsg(event_jenkins_red(xmlMap.get("ToUserName"), xmlMap.get("FromUserName")),
							linkHead.getTimestamp(), linkHead.getNonce());
				} else if ("jenkins_restart".equals(xmlMap.get("EventKey"))) {
					return wx.EncryptMsg(event_jenkins_view("Restart",xmlMap.get("ToUserName"), xmlMap.get("FromUserName")),
							linkHead.getTimestamp(), linkHead.getNonce());
				}else if("jenkins_upload".equals(xmlMap.get("EventKey"))){
					return wx.EncryptMsg(event_jenkins_view("Upload",xmlMap.get("ToUserName"), xmlMap.get("FromUserName")),
							linkHead.getTimestamp(), linkHead.getNonce());
				}
			}

		}else if("voice".equals(xmlMap.get("MsgType"))){
			return wx.EncryptMsg(voice(xmlMap.get("ToUserName"), xmlMap.get("FromUserName"),xmlMap.get("MediaId")),
					linkHead.getTimestamp(), linkHead.getNonce());
		}
		return null;
	}

	private String textbasic(String content, String toUser, String formUser) {
		TextMessageBean tx = new TextMessageBean();
		tx.setContent(content);
		tx.setToUserName(formUser);
		tx.setFromUserName(toUser);
		tx.setCreateTime(String.valueOf((new Date().getTime())));
		tx.setMsgType("text");
		
		return MessageUtil.textMeassageToXML(tx);
	}

	private String event(String toUser, String formUser) {

		return textbasic("欢迎进入TT应用!\n lalalala", toUser, formUser);
	}

	private String text(String content, String toUser, String formUser) {
		String cache = ehcacheUtil.getCache("order");
		cache = (cache == null) ? "nall" : cache;
		ehcacheUtil.putCache("order", cache + "@-@" + content);
		return textbasic("收到" + content + "指令 \n 正在执行指令", toUser, formUser);
	}

	private String event_jenkins_red(String toUser, String formUser) throws IOException {
		LoadConfig lc = new LoadConfig("conf.properties");
		StringBuffer buffer = new StringBuffer();
		String[] titles = { "状态为红色的项目" };
		String[] urls = { lc.getProperty("jenkins_red_url") };
		JSONArray jobs = ParseJSON.getJSONArray(ehcacheUtil.getCache("jobs"));
		int n = 0;
		for (Object jsonObj : jobs) {
			JSONObject job = (JSONObject) jsonObj;
			if ("red".equals(job.getString("color"))) {
				buffer.append(n++ + "  |  " + job.getString("color") + "  |  " + job.getString("name") + "\n");
			}
		}
		logger.info("buffer : " + buffer.toString());
		String[] descriptions = { buffer.toString() };
		String[] picUrls = { "" };
		return newsbasic(formUser, toUser, 1, getItemList(1, titles, descriptions, picUrls, urls));
	}
	
	private String event_jenkins_view(String viewname,String toUser, String formUser) throws IOException {
		LoadConfig lc = new LoadConfig("conf.properties");
		StringBuffer buffer = new StringBuffer();
		String[] titles = { viewname + "View" };
		String[] urls = { lc.getProperty("jenkins_" + viewname + "_url") };
		JSONArray views = ParseJSON.getJSONArray(ehcacheUtil.getCache("views"));
		int n = 0;
		for (Object viewObj : views) {
			JSONObject view = (JSONObject) viewObj;
//			logger.info("view : " + view);
			if (viewname.equals(view.getString("name"))) {
				JSONArray jobs = view.getJSONArray("jobs");
				for (Object jobObj : jobs) {
					JSONObject job = (JSONObject) jobObj;
					if(!"blue".equals(job.getString("color"))){
						buffer.append(n++ + "  |  " + job.getString("color") + "  |  " + job.getString("name") + "\n");
					}
				}
			}
		}
		logger.info("buffer : " + buffer.toString());
		String[] descriptions = { buffer.toString() };
		String[] picUrls = { "" };
		return newsbasic(formUser, toUser, 1, getItemList(1, titles, descriptions, picUrls, urls));
	}
	
	private String newsbasic(String toUser, String fromUser, int articleCount, List<ItemBean> articles) {
		NewsMessageBean newsMessageBean = new NewsMessageBean();
		newsMessageBean.setToUserName(fromUser);
		newsMessageBean.setFromUserName(toUser);
		newsMessageBean.setCreateTime(String.valueOf((new Date().getTime())));
		newsMessageBean.setArticleCount(articleCount);
		newsMessageBean.setArticles(articles);
		newsMessageBean.setMsgType("news");

		return MessageUtil.newsMessageToXML(newsMessageBean);
	}

	private List<ItemBean> getItemList(int articleCount, String[] titles, String[] descriptions, String[] picUrls,
			String[] urls) {
		List<ItemBean> itemList = new ArrayList<>();
		for (int n = 0; n < articleCount; n++) {
			ItemBean item = new ItemBean();
			item.setDescription(descriptions[n]);
			item.setPicUrl(picUrls[n]);
			item.setTitle(titles[n]);
			item.setUrl(urls[n]);
			itemList.add(item);
		}
		return itemList;
	}
	
	private String voice(String toUser,String fromUser,String media_id) throws Exception{
		LoadConfig lc = new LoadConfig("conf.properties");
		logger.info(media_id);
		String media = ClientUtil.get(lc.getProperty("media_url") + AccessTokenControl.getAccessToken() + "&media_id=" + media_id);
		String base64 = DatatypeConverter.printBase64Binary(media.getBytes());
		logger.info("media : " + media);
		String jsonStr = "{\"format\":\"amr\",\"rate\":8000,\"channel\":1,\"cuid\":\"lenovo_ttweixin\",\"token\":\"" + AccessTokenControl.getBaiduAccessToken()+ "\",\"speech\":\"" + base64 + "\",\"len\":" + media.getBytes().length + "}";
//		String post = ClientUtil.post(lc.getProperty("baidu_yuyin_url"), jsonStr);
//		String jsonStr = "{\"format\":\"amr\",\"rate\":8000,\"channel\":1,\"cuid\":\"lenovo_ttweixin\",\"token\":\"" + AccessTokenControl.getBaiduAccessToken()+ "\",\"url\":\""+ lc.getProperty("media_url_encode")+AccessTokenControl.getAccessToken()+"&media_id=" + media_id + "\",\"callback\":\"" + lc.getProperty("callback_url") + "toUser=" + toUser + "&fromUser=" + fromUser +"\"}";
//		String post = ClientUtil.post(lc.getProperty("baidu_yuyin_url"), jsonStr);
		logger.info("jsonStr : " + jsonStr);
//		JSONObject json = ParseJSON.getJSON(post);
		
		String sendStr = "{\"touser\":\"" + fromUser
		+ "\",\"msgtype\":\"voice\",\"agentid\":2,\"voice\":{\"media_id\":\"" + media_id + "\"}}";
		String sendP = ClientUtil.post(lc.getProperty("sendUrl") + AccessTokenControl.getAccessToken(), sendStr);
		return textbasic(sendP, toUser, fromUser);
	}
}
