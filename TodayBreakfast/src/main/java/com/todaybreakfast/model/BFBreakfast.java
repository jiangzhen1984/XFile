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
	
	@Column(name =  "IS_TOADY",  columnDefinition="BOOL")
	private boolean isToday;

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
	
	
	

}
