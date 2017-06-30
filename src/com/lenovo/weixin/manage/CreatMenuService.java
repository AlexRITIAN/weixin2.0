package com.lenovo.weixin.manage;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.lenovo.weixin.utils.AccessTokenControl;
import com.lenovo.weixin.utils.ClientUtil;

public class CreatMenuService {
	private static Logger logger = Logger.getLogger(CreatMenuService.class);
	public static String creat(String buildMenu){
		String url = "https://qyapi.weixin.qq.com/cgi-bin/menu/create?";//access_token=ACCESS_TOKEN&agentid=AGENTID";
		String agentID = "&agentid=2";
		try {
			return ClientUtil.post(url + "access_token=" + AccessTokenControl.getAccessToken() + agentID, buildMenu);
		} catch (IOException e) {
			logger.error(e);
			e.printStackTrace();
		}
		return "creat failed :(";
	}

	public static String delete() {
		String url = "https://qyapi.weixin.qq.com/cgi-bin/menu/delete?";
		String agentID = "&agentid=2";
		try {
			return ClientUtil.get(url + "access_token=" + AccessTokenControl.getAccessToken() + agentID);
		} catch (IOException e) {
			logger.error(e);
			e.printStackTrace();
		}
		return "delete failed :(";
	}
}
