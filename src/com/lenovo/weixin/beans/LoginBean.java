package com.lenovo.weixin.beans;

public class LoginBean extends LinkHeadBean {
	private String auth_code;
	private String state;

	public String getAuth_code() {
		return auth_code;
	}

	public void setAuth_code(String auth_code) {
		this.auth_code = auth_code;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "LoginBean [auth_code=" + auth_code + ", state=" + state + "]";
	}

}
