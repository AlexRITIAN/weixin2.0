package com.lenovo.weixin.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

//import org.apache.log4j.Logger;

public class LoadConfig {
	private FileInputStream in;
//	private Logger logger = Logger.getLogger(LoadConfig.class);
	Properties pro;

	public LoadConfig(String confName) throws IOException {
		in = new FileInputStream(this.getClass().getClassLoader().getResource(confName).getPath());
//		logger.info(in);
		pro = new Properties();
		pro.load(in);
	}

	public String getProperty(String key) throws IOException {
		return pro.getProperty(key);
	}
}
