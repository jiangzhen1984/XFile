package com.todaybreakfast.model.vo;

public class BreakfastWrapper {

	protected long id;

	protected Type type;

	protected float price;

	protected String name;

	protected String stuff;

	protected String description;
	
	protected String url;

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
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BreakfastWrapper other = (BreakfastWrapper) obj;
		if (id != other.id)
			return false;
		return true;
	}





	public enum Type {
		SINGLE, COMBO;
		
		
		public int getValue() {
			return this.ordinal();
		}
		
	}

}
