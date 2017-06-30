package com.lenovo.weixin.utils;

import java.util.List;

//import org.apache.log4j.Logger;


public class UserUtil {
//	private static Logger logger = Logger.getLogger(UserUtil.class);
	
	public static boolean checkDeptement(String depts,List<String> rules){
		boolean flag = false;
		for (String str : rules) {
//			logger.info(depts.indexOf(str));
			if(depts.indexOf(str) != -1){
				flag = true;
				break;
			}
		}
		return flag;
	}
}
