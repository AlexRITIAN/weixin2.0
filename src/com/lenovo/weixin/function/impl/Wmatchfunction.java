package com.lenovo.weixin.function.impl;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.lenovo.weixin.beans.LinkHeadBean;
import com.lenovo.weixin.function.Winterface;
import com.lenovo.weixin.utils.LoadConfig;
import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;

public class Wmatchfunction implements Winterface {
	private final String CONFIG_FILE_NAME = "conf.properties";
	private Logger logger = Logger.getLogger(Wmatchfunction.class);

	private String match(String msg_signature, String timestmp, String nonce, String echostr) {
		LoadConfig lc;
		WXBizMsgCrypt wx;
		String str = null;
		try {
			lc = new LoadConfig(CONFIG_FILE_NAME);
			String token = lc.getProperty("token");
//			logger.info(token);
			String encodingAesKey = lc.getProperty("encodingAesKey");
//			logger.info(encodingAesKey);
			String corpId = lc.getProperty("corpId");
//			logger.info(corpId);
			wx = new WXBizMsgCrypt(token,encodingAesKey,corpId);
			str = wx.VerifyURL(msg_signature, timestmp, nonce, echostr);
		} catch (FileNotFoundException e) {
			logger.info(e);
		} catch (AesException e) {
			logger.info(e);
		} catch (IOException e) {
			logger.info(e);			
		}
		return str;
	}

	@Override
	public String run(LinkHeadBean linkHead, String data){
		return match(linkHead.getMsg_signature(), linkHead.getTimestamp(), linkHead.getNonce(), data);
	}

}
