package com.eshopping.model.vo;

import com.eshopping.model.po.ESCatgoryItemSpecialType;

public class CategoryItemSpecialType {
	
	
	protected long id;
	
	private CategoryItemSpecialTypeGroup group;
	
	private String groupName;
	
	private String name;
	
	private Category category;
	
	private boolean isShow;
	
	public CategoryItemSpecialType() {
		super();
	}
	
	public CategoryItemSpecialType(ESCatgoryItemSpecialType esType) {
		this.id = esType.getId();
		this.group = new CategoryItemSpecialTypeGroup(esType.getGroup());
		this.groupName = esType.getGroupName();
		this.name = esType.getName();
		this.isShow = esType.isShow();
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public CategoryItemSpecialTypeGroup getGroup() {
		return group;
	}

	public void setGroup(CategoryItemSpecialTypeGroup group) {
		this.group = group;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public boolean isShow() {
		return isShow;
	}

	public void setShow(boolean isShow) {
		this.isShow = isShow;
	}

	
	

}
