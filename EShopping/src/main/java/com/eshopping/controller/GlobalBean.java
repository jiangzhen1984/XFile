package com.eshopping.controller;

import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.eshopping.model.vo.Category;
import com.eshopping.service.GlobalCache;


@ManagedBean(name="global", eager=true)
@ApplicationScoped
public class GlobalBean {

	
	public static String GLOBAL_HOST = "http://127.0.0.1:8080/";
	
	public static String GLOBAL_CONTEXT = "";
	
	public static String GLOBAL_IMAGE_HOST = GLOBAL_HOST + GLOBAL_CONTEXT;
	
	
	public String getGlobalUrl() {
		return GLOBAL_HOST + GLOBAL_CONTEXT;
	}
	
	public String getImageHost() {
		return GLOBAL_IMAGE_HOST;
	}
	
	
	public String getContext() {
		return GLOBAL_CONTEXT;
	}
	
	
	public List<Category> getTopCategoryList() {
		return GlobalCache.getInstance().getTopLevelCategoryList();
	}
	

}
