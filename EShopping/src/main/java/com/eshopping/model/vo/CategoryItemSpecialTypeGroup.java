package com.eshopping.model.vo;

import java.util.ArrayList;
import java.util.List;

public class CategoryItemSpecialTypeGroup {
	
	private int id;
	
	private String name;
	
	private List<CategoryItemSpecialType> types;
	
	private boolean isShow;
	
	private Category belongs;
	
	private boolean isCommon;
	
	

	public CategoryItemSpecialTypeGroup() {
		super();
		types = new ArrayList<CategoryItemSpecialType>();
	}
	
	public CategoryItemSpecialTypeGroup(int id) {
		super();
		this.id = id;
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
		if (item == null) {
			return;
		}
		item.setCategory(belongs);
		item.setGroup(this);
		this.types.add(item);
	}
	
	public void removeType(CategoryItemSpecialType item) {
		if (item == null) {
			return;
		}
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
	
	
	
	

	public Category getBelongs() {
		return belongs;
	}

	public void setBelongs(Category belongs) {
		this.belongs = belongs;
	}

	public boolean isCommon() {
		return isCommon;
	}

	public void setCommon(boolean isCommon) {
		this.isCommon = isCommon;
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
