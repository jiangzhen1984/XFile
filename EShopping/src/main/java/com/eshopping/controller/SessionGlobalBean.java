package com.eshopping.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "sessionConfigBean", eager = true)
@SessionScoped
public class SessionGlobalBean {
	
	private boolean mobileBrowserFlag;

	public SessionGlobalBean() {
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
	
	
}
