package com.todaybreakfast.controller;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name="global")
@ApplicationScoped
public class GlobalBean {

	
	public static String GLOBAL_HOST = "http://localhost:8080/";
	
	public static String GLOBAL_CONTEXT = "TodayBreakfast/";
	
	
	
	public String getGlobalUrl() {
		return GLOBAL_HOST + GLOBAL_CONTEXT;
	}
}
