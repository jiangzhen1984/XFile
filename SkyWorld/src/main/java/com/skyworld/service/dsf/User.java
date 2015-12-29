package com.skyworld.service.dsf;

import com.skyworld.cache.Token;
import com.skyworld.push.ClientTerminal;
import com.skyworld.service.po.SWPUser;



public class User extends SWPUser {
	
	private Token token;
	
	
	private ClientTerminal pushTerminal;
	
	private UserType userType;

	public User() {
		super();
		userType = UserType.CUSTOMER;
	}
	
	public User(User u) {
		this.setAddress(u.getAddress());
		this.setCellPhone(u.getCellPhone());
		this.setName(u.getName());
		this.setId(u.getId());
		switch(u.getuType()) {
		case 0:
			this.userType = UserType.CUSTOMER;
			break;
		case 1:
			this.userType = UserType.SERVICER;
			break;
		case 2:
			this.userType = UserType.GROUP;
			break;
		}
		
	}

	public User(SWPUser u) {
		this.setAddress(u.getAddress());
		this.setCellPhone(u.getCellPhone());
		this.setName(u.getName());
		this.setId(u.getId());
		switch(u.getuType()) {
		case 0:
			this.userType = UserType.CUSTOMER;
			break;
		case 1:
			this.userType = UserType.SERVICER;
			break;
		case 2:
			this.userType = UserType.GROUP;
			break;
		}
	}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}


	public ClientTerminal getPushTerminal() {
		return pushTerminal;
	}

	public void setPushTerminal(ClientTerminal pushTerminal) {
		this.pushTerminal = pushTerminal;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	
	
	
	
	
	
	

}
