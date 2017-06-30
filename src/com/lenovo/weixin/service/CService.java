package com.lenovo.weixin.service;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.lenovo.weixin.beans.LinkHeadBean;
import com.lenovo.weixin.function.Cinterface;
import com.lenovo.weixin.utils.LoadConfig;
import com.lenovo.weixin.utils.ParseJSON;
import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;

import net.sf.json.JSONObject;

public class CService {
	private static final String CLASSNAME_BASIS = "com.lenovo.weixin.function.impl.";
	public static String post(LinkHeadBean linkHead,String postData){
		Logger logger = Logger.getLogger(CService.class);
		LoadConfig lc;
		WXBizMsgCrypt wx;
		String dataStr = null;
		try {
			lc = new LoadConfig("conf.properties");
			wx = new WXBizMsgCrypt(lc.getProperty("token"), lc.getProperty("encodingAesKey"), lc.getProperty("corpId"));
			dataStr = wx.DecryptMsg(linkHead.getMsg_signature(), linkHead.getTimestamp(),linkHead.getNonce(),postData);
			logger.info(dataStr);
			JSONObject json = ParseJSON.getJSON(dataStr);
			String status = json.getString("status");
			Class<?> obj = Class.forName(CLASSNAME_BASIS+status);
			Cinterface objEntity = (Cinterface) obj.newInstance();
			objEntity.run(json);
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
			e.printStackTrace();
		} catch (AesException e) {
			logger.error(e.getMessage(),e);
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(),e);
			e.printStackTrace();
		} catch (InstantiationException e) {
			logger.error(e.getMessage(),e);
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(),e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			e.printStackTrace();
		}
		return dataStr;
	}
}
