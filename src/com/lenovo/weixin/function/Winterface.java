package com.lenovo.weixin.function;

import com.lenovo.weixin.beans.LinkHeadBean;

public interface Winterface {
	String run(LinkHeadBean linkHead,String data) throws Exception;
}
