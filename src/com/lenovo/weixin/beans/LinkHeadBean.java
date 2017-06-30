package com.lenovo.weixin.beans;

public class LinkHeadBean {
	private String msg_signature;
	private String timestamp;
	private String nonce;
	public String getMsg_signature() {
		return msg_signature;
	}
	public void setMsg_signature(String msg_signature) {
		this.msg_signature = msg_signature;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getNonce() {
		return nonce;
	}
	public void setNonce(String nonce) {
		this.nonce = nonce;
	}
	@Override
	public String toString() {
		return "LinkHeadBean [msg_signature=" + msg_signature + ", timestamp=" + timestamp + ", nonce=" + nonce + "]";
	}
	
	
}
