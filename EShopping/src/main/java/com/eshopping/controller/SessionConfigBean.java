package com.eshopping.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.eshopping.model.vo.User;

@ManagedBean(name = "sessionConfigBean", eager = true)
@SessionScoped
public class SessionConfigBean {
	
	private boolean mobileBrowserFlag;
	
	private boolean isLogedIn;
	
	private User user;

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
}
