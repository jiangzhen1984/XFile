package com.eshopping.model.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="ES_USER_ADDRESS")
public class ESUserAddress {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected long id;
	
	@Column(name = "ADDR_COUNTRY", columnDefinition = "VARCHAR(200)")
	protected String  country;
	
	@Column(name = "ADDR_STATE", columnDefinition = "VARCHAR(200)")
	protected String  state;
	
	@Column(name = "ADDR_CITY", columnDefinition = "VARCHAR(200)")
	protected String  city;
	
	@Column(name = "ADDR_DETAIL", columnDefinition = "VARCHAR(200)")
	protected String  address;
	
	
	@Column(name = "ADDR_POST_CODE", columnDefinition = "VARCHAR(200)")
	protected String  postCode;
	
	@Column(name = "NAME", columnDefinition = "VARCHAR(200)")
	protected String  name;
	
	@Column(name = "PHONE_NUMBER", columnDefinition = "VARCHAR(200)")
	protected String  phoneNumber;
	
	@Column(name = "ADDR_DEF", columnDefinition = "BOOL")
	protected boolean isDefault;
	
	
	@Column(name="ES_USER_ID", columnDefinition="bigint")
	protected long userId;
	

	public ESUserAddress() {
	}

	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	
	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}



	public long getUserId() {
		return userId;
	}



	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	
	

}
