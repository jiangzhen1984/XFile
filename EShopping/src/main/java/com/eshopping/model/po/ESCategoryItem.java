package com.eshopping.model.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ES_CATEGORY_ITEM")
public class ESCategoryItem implements Serializable{

	@Id
	@Column(name = "ES_CATEGORY_ID", columnDefinition = "NUMERIC(20)")
	private long categoryId;
	
	@Id
	@Column(name = "ES_ITEM_ID", columnDefinition = "NUMERIC(20)")
	private long itemId;
	
	@Column(name = "ITEM_TYPE", columnDefinition = "NUMERIC(1)")
	private int type;
	
	public ESCategoryItem() {
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	
	
}
