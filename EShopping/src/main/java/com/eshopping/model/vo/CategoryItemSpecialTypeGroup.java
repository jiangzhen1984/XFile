package com.eshopping.model.vo;

import java.util.ArrayList;
import java.util.List;

public class CategoryItemSpecialTypeGroup {
	
	private int id;
	
	private String name;
	
	private List<CategoryItemSpecialType> types;
	
	private boolean isShow;
	
	

	public CategoryItemSpecialTypeGroup() {
		super();
		types = new ArrayList<CategoryItemSpecialType>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	public void addType(CategoryItemSpecialType item) {
		this.types.add(item);
	}
	
	public void removeType(CategoryItemSpecialType item) {
		this.types.remove(item);
	}
	
	
	

	public List<CategoryItemSpecialType> getTypes() {
		return types;
	}
	
	

	public boolean isShow() {
		return isShow;
	}

	public void setShow(boolean isShow) {
		this.isShow = isShow;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		CategoryItemSpecialTypeGroup other = (CategoryItemSpecialTypeGroup) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
	

}
