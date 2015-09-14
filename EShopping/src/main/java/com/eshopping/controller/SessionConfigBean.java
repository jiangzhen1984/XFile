package com.eshopping.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "sessionConfigBean", eager = true)
@SessionScoped
public class SessionConfigBean {
	
	private boolean mobileBrowserFlag;
	
	private boolean isLogedIn;

	public SessionConfigBean() {
	}

	public String getBrowserCssConfig() {
		if (mobileBrowserFlag) {
			return "smartphone";
		} else {
			return "web";
		}
	}
	
	
	public void setBrowserConfig(boolean isMobile) {
		mobileBrowserFlag = isMobile;
	}
	
	public boolean isLogin() {
		return isLogedIn;
	}
	
	public void setLogin(boolean flag) {
		this.isLogedIn = true;
	}
	
	
}
