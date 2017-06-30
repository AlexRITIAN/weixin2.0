package com.lenovo.weixin.beans;

public class TextMessageBean extends MessageBeanBase {
	
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	@Override
	public String toString() {
		return "TextMessageBean [Content=" + Content + "]" + super.toString();
	}
	
	
}
