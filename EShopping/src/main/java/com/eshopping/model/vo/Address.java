package com.eshopping.model.vo;

import com.eshopping.model.po.ESUserAddress;

public class Address extends ESUserAddress {
	
	private User user;
	
	public Address() {
	}
	
	public Address(ESUserAddress eua) {
		this.id = eua.getId();
		this.name = eua.getName();
		this.address = eua.getAddress();
		this.postCode = eua.getPostCode();
		this.city = eua.getCity();
		this.phoneNumber = eua.getPhoneNumber();
		this.userId = eua.getUserId();
		this.state = eua.getState();
		this.country = eua.getCountry();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
		if (user != null) {
			this.userId = user.getId();
		}
	}
	
	

}
