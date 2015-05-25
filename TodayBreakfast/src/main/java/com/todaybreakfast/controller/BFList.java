package com.todaybreakfast.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.todaybreakfast.model.vo.BreakfastWrapper;
import com.todaybreakfast.service.BreakfastBasicService;


@ManagedBean(name="bfbean", eager = true)
@SessionScoped
public class BFList {
	
	private BreakfastBasicService service;
	
	public BFList() {
		service = new BreakfastBasicService();
	}
	
	public List<BreakfastWrapper> getBFList() {
		return  service.getBreakfastList();
	}

}
