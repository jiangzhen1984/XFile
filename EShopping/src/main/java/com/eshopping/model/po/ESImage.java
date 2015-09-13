package com.eshopping.model.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ES_IMAGE")
public class ESImage {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected long id;
	
	@Column(name = "ITEM_TYPE", columnDefinition = "int(2)")
	protected int itemType;
	
	@Column(name = "IMAGE_TYPE", columnDefinition = "int(2)")
	protected int imageType;
	
	@Column(name = "IMAGE_URI", columnDefinition = "VARCHAR(200)")
	protected String uri;
	
	@Column(name = "ES_ITEM_ID", columnDefinition = "bigint")
	protected long itemId;
	
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
	public int getItemType() {
		return itemType;
	}

	public void setItemType(int itemType) {
		this.itemType = itemType;
	}

	public int getImageType() {
		return imageType;
	}

	public void setImageType(int imageType) {
		this.imageType = imageType;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public long getItemID() {
		return itemId;
	}

	public void setItemID(long itemID) {
		this.itemId = itemID;
	}
	
	
	

}
