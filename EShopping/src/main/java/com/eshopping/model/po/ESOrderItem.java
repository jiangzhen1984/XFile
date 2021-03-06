package com.eshopping.model.po;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ES_ORDER_ITEM")
public class ESOrderItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	
	@Column(name="NAME", columnDefinition="VARCHAR(100)")
	private String name;
	
	@Column(name="PIC_PATH", columnDefinition="VARCHAR(200)")
	private String picUrl;
	
	@Column(name = "PRICE", columnDefinition =  "NUMERIC(6,2)")
	private Float price;
	
	@Column(name = "ITEM_COUNT", columnDefinition =  "NUMERIC(3)")
	private int count;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "ES_ORDER_ID", referencedColumnName = "ID", unique = false)
	private ESOrder order;

	
	public ESOrderItem() {
		
	}
	
	
	public ESOrderItem(ESOrderItem item) {
		this.id = item.id;
		this.name = item.name;
		this.picUrl = item.picUrl;
		this.price = item.price;
		this.order = item.order;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public ESOrder getOrder() {
		return order;
	}

	public void setOrder(ESOrder order) {
		this.order = order;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	
	

}
