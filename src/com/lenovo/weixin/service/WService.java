package com.lenovo.weixin.service;

import org.apache.log4j.Logger;

import com.lenovo.weixin.beans.LinkHeadBean;
import com.lenovo.weixin.function.Winterface;

public class WService {
	private static Logger logger = Logger.getLogger(WService.class);

	public static String get(LinkHeadBean linkHead, String data) {
		String echoStr = null;
		try {
			Class<?> matchObj = Class.forName("com.lenovo.weixin.function.impl.Wmatchfunction");
			Winterface match = (Winterface) matchObj.newInstance();
//			logger.info(linkHead);
			echoStr = match.run(linkHead, data);
//			logger.info(echoStr);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return echoStr;
	}
	
	public static String post(LinkHeadBean linkHead,String data){
		String postStr = null;
		try {
			Class<?> wpcObj = Class.forName("com.lenovo.weixin.function.impl.Wpostcontrol");
			Winterface wpc = (Winterface) wpcObj.newInstance();
			postStr = wpc.run(linkHead, data);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(),e);
		} catch (InstantiationException e) {
			logger.error(e.getMessage(),e);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(),e);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return postStr;
	}
}
