package com.todaybreakfast.controller.admin;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.todaybreakfast.model.vo.BreakfastWrapper;
import com.todaybreakfast.service.BreakfastBasicService;

@ManagedBean(name = "breakfastManagementBean", eager= false)
@ViewScoped
public class BreakfastManagement {

	private BreakfastBasicService service;
	
	private List<BreakfastWrapper> breakfastSingleList;
	private List<BreakfastWrapper> breakfastSingleCombo;
	
	private String name;
	private String desc;
	private float price;
	private String stuff;
	
	
	public BreakfastManagement() {
		service = new BreakfastBasicService();
	}
	
	
	public List<BreakfastWrapper> getBFList() {
		return  service.getBreakfastList();
	}
	
	
	
	public List<BreakfastWrapper> getBFSingleList() {
		if (breakfastSingleList == null) {
			List<BreakfastWrapper> allList = getBFList();
			breakfastSingleList = new ArrayList<BreakfastWrapper>();
			for (int i = 0; i < allList.size(); i++) {
				BreakfastWrapper wr = allList.get(i);
				if (wr.getType() == BreakfastWrapper.Type.SINGLE) {
					breakfastSingleList.add(wr);
				}
			}
		}
		return breakfastSingleList;
	}
	
	
	public List<BreakfastWrapper> getBFComoboList() {
		if (breakfastSingleCombo == null) {
			List<BreakfastWrapper> allList = getBFList();
			breakfastSingleCombo = new ArrayList<BreakfastWrapper>();
			for (int i = 0; i < allList.size(); i++) {
				BreakfastWrapper wr = allList.get(i);
				if (wr.getType() == BreakfastWrapper.Type.COMBO) {
					breakfastSingleCombo.add(wr);
				}
			}
		}
		return breakfastSingleCombo;
	}


	
	/////==============for create new breakfast
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDesc() {
		return desc;
	}


	public void setDesc(String desc) {
		this.desc = desc;
	}


	public float getPrice() {
		return price;
	}


	public void setPrice(float price) {
		this.price = price;
	}


	public String getStuff() {
		return stuff;
	}


	public void setStuff(String stuff) {
		this.stuff = stuff;
	}
	
	
	
	
	
	
}
