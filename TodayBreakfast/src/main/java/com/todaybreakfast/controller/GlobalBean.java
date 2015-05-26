package com.todaybreakfast.controller;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name="global", eager=true)
@ApplicationScoped
public class GlobalBean {

	
	public static String GLOBAL_HOST = "http://localhost:8080/";
	
	public static String GLOBAL_CONTEXT = "TodayBreakfast/";
	
	public static String GLOBAL_IMAGE_IMAGE_PATH = "/home/CORPUSERS/28851274/Pictures/bf_test/";
	
	public static String GLOBAL_IMAGE_HOST = "http://localhost:8080/TodayBreakfast/";
	
	
	
	public String getGlobalUrl() {
		return GLOBAL_HOST + GLOBAL_CONTEXT;
	}
	
	public String getImageHost() {
		return GLOBAL_IMAGE_HOST;
	}
}
