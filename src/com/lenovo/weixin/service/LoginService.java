package com.lenovo.weixin.service;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.lenovo.weixin.utils.AccessTokenControl;
import com.lenovo.weixin.utils.ClientUtil;
import com.lenovo.weixin.utils.LoadConfig;
import com.lenovo.weixin.utils.ParseJSON;

import net.sf.json.JSONObject;

public class LoginService {
	private static Logger logger = Logger.getLogger(LoginService.class);

	public static String login(String user, String password, HttpSession session) {
		String flag = "1";
		LoadConfig lc;
		try {
			lc = new LoadConfig("conf.properties");
			String userInfor = ClientUtil
					.get(lc.getProperty("loginUrl") + AccessTokenControl.getAccessToken() + "&userid=" + user);
			JSONObject json = ParseJSON.getJSON(userInfor);
			if (0 == json.getInt("errcode") && "ok".equals(json.getString("errmsg"))
					&& user.equals(json.getString("userid")) && password.equals(json.getString("mobile"))) {
				flag = "0";
				session.setAttribute("userID", user);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
}
