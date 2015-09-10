package com.eshopping.model.vo;

import com.eshopping.model.po.ESItem;



public class SingleShoppingItem extends AbsShoppingItem {
	

	private String picUrl;


	public SingleShoppingItem() {
		super(TYPE_SINGLE);
	}

	
	public SingleShoppingItem(ESItem item) {
		super(TYPE_SINGLE);
		if (item == null) {
			throw new NullPointerException(" ESItem can not be null");
		}
		this.id = item.getId();
		this.name = item.getName();
		this.picUrl = item.getPicUrl();
		this.price = item.getPrice();
		this.stuff = item.getStuff();
		this.description = item.getDescription();
	}


	public String getPicUrl() {
		return picUrl;
	}


	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	
	
}
