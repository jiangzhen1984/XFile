package com.eshopping.controller;

import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;


@ManagedBean(name="global", eager=true)
@ApplicationScoped
public class GlobalBean {

	
	public static String GLOBAL_HOST = "http://10.147.6.103:8080/";
	
	public static String GLOBAL_CONTEXT = "TodayBreakfast/";
	
	public static String GLOBAL_IMAGE_HOST = "http://10.147.6.103:8080/TodayBreakfast/";
	
	
	public String getGlobalUrl() {
		return GLOBAL_HOST + GLOBAL_CONTEXT;
	}
	
	public String getImageHost() {
		return GLOBAL_IMAGE_HOST;
	}
	

}
