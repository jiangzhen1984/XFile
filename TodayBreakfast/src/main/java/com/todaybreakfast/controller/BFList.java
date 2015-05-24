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
	private List<BreakfastWrapper> bfList;
	
	public BFList() {
		service = new BreakfastBasicService();
	}
	
	public List<BreakfastWrapper> getBFList() {
		if (bfList == null) {
			bfList = service.getBreakfastList();
		}
		return bfList;
	}

}
