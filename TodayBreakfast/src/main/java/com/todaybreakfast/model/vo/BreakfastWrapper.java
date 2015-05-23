package com.todaybreakfast.model.vo;

public class BreakfastWrapper {
	
	protected long id;
	
	protected Type type;
	
	protected float price;
	
	protected String name;
	
	


	public Type getType() {
		return type;
	}




	public void setType(Type type) {
		this.type = type;
	}




	public long getId() {
		return id;
	}




	public void setId(long id) {
		this.id = id;
	}




	public float getPrice() {
		return price;
	}




	public void setPrice(float price) {
		this.price = price;
	}




	public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
	}




	public enum Type {
		SINGLE,
		COMBO
	}

}
