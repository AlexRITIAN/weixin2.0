package com.lenovo.weixin.service;


import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.lenovo.weixin.function.Binterface;
import com.lenovo.weixin.utils.LoadConfig;
import com.lenovo.weixin.utils.MatchUtil;

public class BService {
	private static Logger logger = Logger.getLogger(BService.class);
	private static final String CLASSNAME_BASE = "com.lenovo.weixin.function.impl.";

	public static String get(String code, String state,HttpSession session) {
		String errorUrl = null;
		try {
			LoadConfig lc = null;
			lc = new LoadConfig("conf.properties");
			errorUrl = lc.getProperty("errorPage");
			if(session.getAttribute("userID") == null){
				if (code != null) {
					String codeMatch = MatchUtil.codeMatch(code);
					if (codeMatch.equals("false")) {
						return errorUrl;
					}
					session.setAttribute("userID", codeMatch);
					return state;
				}else{
					return errorUrl;
				}
			}else{
				return state;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return errorUrl;
		}
	}

	public static String post(String type, String state, HttpSession session) {
		if (session != null && session.getAttribute("userID") != null) {
			try {
				Class<?> objec = Class.forName(CLASSNAME_BASE + state);
				Binterface binterface = (Binterface) objec.newInstance();
				String json = binterface.run(type);
				logger.info("type : " + type);
				return json;
			} catch (ClassNotFoundException e) {
				logger.error(e.getMessage(), e);
			} catch (InstantiationException e) {
				logger.error(e.getMessage(), e);
			} catch (IllegalAccessException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return null;
	}
	
	
}
