package com.lenovo.weixin.beans;

public class SendBean {
	private String tagetType;
	private String taget;
	private String tagetID;
	private String message;

	@Override
	public String toString() {
		return "SendBean [tagetType=" + tagetType + ", taget=" + taget + ", tagetID=" + tagetID + ", message=" + message
				+ "]";
	}

	public String getTagetType() {
		return tagetType;
	}

	public void setTagetType(String tagetType) {
		this.tagetType = tagetType;
	}

	public String getTaget() {
		return taget;
	}

	public void setTaget(String taget) {
		this.taget = taget;
	}

	public String getTagetID() {
		return tagetID;
	}

	public void setTagetID(String tagetID) {
		this.tagetID = tagetID;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
