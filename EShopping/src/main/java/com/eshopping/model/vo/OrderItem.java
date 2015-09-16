package com.eshopping.model.vo;

import com.eshopping.model.po.ESOrderItem;


public class OrderItem {
	
	private long id;
	
	
	private String name;
	
	private String picUrl;
	
	private Float price;
	
	private int count;
	
	private Order order;

	
	public OrderItem() {
		
	}
	
	
	public OrderItem(OrderItem item) {
		this.id = item.id;
		this.name = item.name;
		this.picUrl = item.picUrl;
		this.price = item.price;
		this.order = item.order;
	}
	
	public OrderItem(ESOrderItem item) {
		this.id = item.getId();
		this.name = item.getName();
		this.picUrl = item.getPicUrl();
		this.price = item.getPrice();
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	
	

}
