package com.todaybreakfast.controller;

import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.todaybreakfast.model.Place;
import com.todaybreakfast.service.PlaceService;

@ManagedBean(name="global", eager=true)
@ApplicationScoped
public class GlobalBean {

	
	public static String GLOBAL_HOST = "http://192.168.1.104:8080/";
	
	public static String GLOBAL_CONTEXT = "TodayBreakfast/";
	
	public static String GLOBAL_IMAGE_HOST = "http://192.168.1.104:8080/TodayBreakfast/";
	
	private PlaceService plService;
	
	public GlobalBean() {
		plService = new PlaceService();
	}
	
	public String getGlobalUrl() {
		return GLOBAL_HOST + GLOBAL_CONTEXT;
	}
	
	public String getImageHost() {
		return GLOBAL_IMAGE_HOST;
	}
	
	public List<Place> getPlaces() {
		return plService.getPlaceList();
	};
}
