package com.eshopping.model.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="ES_CATEGORY_ITEM_SPECIAL_TYPE")
public class ESCatgoryItemSpecialType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected long id;
	
	@Column(name = "TYPE_GROUP", columnDefinition="int(2)")
	private int group;
	
	@Column(name = "GROUP_NAME", columnDefinition="VARCHAR(100)")
	private String groupName;
	
	
	@Column(name = "TYPE_NAME", columnDefinition="VARCHAR(100)")
	private String name;
	
	@Column(name = "CATEGORY_ID", columnDefinition="bigint")
	private long categoryId;
	
	@Column(name = "IS_SHOW", columnDefinition="BOOL")
	private boolean isShow;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
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

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public boolean isShow() {
		return isShow;
	}

	public void setShow(boolean isShow) {
		this.isShow = isShow;
	}
	
	

}
