package com.lenovo.weixin.function.impl;

import com.lenovo.weixin.function.Binterface;
import com.lenovo.weixin.utils.EhcacheUtil;

public class BServerBuildFunction implements Binterface {
	private EhcacheUtil ehcacheUtil = EhcacheUtil.getEhcacheUtil();

	@Override
	public String run(String type) {
		String order = ehcacheUtil.getCache("order");
		order = (order == null)?"nall":order;
		ehcacheUtil.putCache("order", order + "@-@" + type);
		return null;
	}

}
