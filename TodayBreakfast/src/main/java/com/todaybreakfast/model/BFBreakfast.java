package com.todaybreakfast.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "BF_BREAKFAST")
public class BFBreakfast {
	
	@Transient
	private BFCategory category;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(name="NAME", columnDefinition="VARCHAR(40)")
	private String name;
	
	@Column(name="PIC_PATH", columnDefinition="VARCHAR(200)")
	private String picUrl;
	
	@Column(name =  "IS_TOADY",  columnDefinition="BOOL default TRUE", nullable=false)
	private boolean isToday;
	
	@Column(name = "PRICE", columnDefinition =  "NUMERIC(6,2)")
	private Float price;

	public BFCategory getCategory() {
		return category;
	}

	public void setCategory(BFCategory category) {
		this.category = category;
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

	public boolean isToday() {
		return isToday;
	}

	public void setToday(boolean isToday) {
		this.isToday = isToday;
	}

	public float getPrice() {
		return price == null? 0 : price;
	}

	public void setPrice(float price) {
		this.price = price;
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
		BFBreakfast other = (BFBreakfast) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	

}
