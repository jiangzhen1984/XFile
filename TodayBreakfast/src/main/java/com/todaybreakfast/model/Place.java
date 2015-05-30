package com.todaybreakfast.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="BF_PLACE")
public class Place {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(name="DISTRICT", columnDefinition="VARCHAR(50)")
	private String district;
	
	@Column(name="ADDRESS", columnDefinition="VARCHAR(200)")
	private String address;

	
	
	public Place() {
	}
	
	
	public Place(long id, String district, String address) {
		super();
		this.id = id;
		this.district = district;
		this.address = address;
	}
	
	public Place(Place p) {
		this.id = p.getId();
		this.district = p.getDistrict();
		this.address = p.getAddress();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
	

}
