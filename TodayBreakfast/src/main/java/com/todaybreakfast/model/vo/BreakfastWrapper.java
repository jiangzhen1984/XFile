package com.todaybreakfast.model.vo;

public class BreakfastWrapper {

	protected long id;

	protected Type type;

	protected float price;

	protected String name;

	protected String stuff;

	protected String description;

	public Type getType() {
		return type;
	}
	
	public int getTypeOrdinal() {
		return type.ordinal();
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

	public String getStuff() {
		return stuff;
	}

	public void setStuff(String stuff) {
		this.stuff = stuff;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public enum Type {
		SINGLE, COMBO;
		
		
		public int getValue() {
			return this.ordinal();
		}
		
	}

}
