package com.eshopping.model.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ES_ITEM_SPECIAL_TYPE_M")
public class EStemSpecialTypeMapping implements Serializable{

	
	@Column(name = "ES_CATEGORY_ID", columnDefinition = "NUMERIC(20)")
	private long categoryId;
	
	@Id
	@Column(name = "ES_ITEM_ID", columnDefinition = "NUMERIC(20)")
	private long itemId;
	
	@Id
	@Column(name = "ES_SPECIAL_TYPE_ID", columnDefinition = "NUMERIC(20)")
	private long typeId;
	
	@Id
	@Column(name = "ES_ITEM_TYPE", columnDefinition = "int(2)")
	private int itemType;
	
	public EStemSpecialTypeMapping() {
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public long getTypeId() {
		return typeId;
	}

	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}

	public int getItemType() {
		return itemType;
	}

	public void setItemType(int itemType) {
		this.itemType = itemType;
	}



	
	
}
