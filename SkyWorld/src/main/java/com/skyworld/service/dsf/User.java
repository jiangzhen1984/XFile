package com.skyworld.service.dsf;

import com.skyworld.cache.Token;
import com.skyworld.service.po.SWPUser;



public class User extends SWPUser {
	
	private Token token;
	
	private Question currentQuest;

	public User() {
		super();
	}

	public User(SWPUser u) {
		super(u);
	}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}

	public Question getCurrentQuest() {
		return currentQuest;
	}

	public void setCurrentQuest(Question currentQuest) {
		this.currentQuest = currentQuest;
	}
	
	
	
	
	
	
	

}
