package com.todaybreakfast.model.vo;

import com.todaybreakfast.model.BFBreakfast;

public class BreakfastSingleWrapper extends BreakfastWrapper {
	
	private String url;
	
	
	
	
	public BreakfastSingleWrapper(float price, String name, String url) {
		super();
		this.type = BreakfastWrapper.Type.SINGLE;
		this.price = price;
		this.name = name;
		this.url = url;
	}

	
	

	public BreakfastSingleWrapper(BFBreakfast bf) {
		this.id = bf.getId();
		this.name = bf.getName();
		this.price = bf.getPrice();
		this.url = bf.getPicUrl();
		this.type = BreakfastWrapper.Type.SINGLE;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
