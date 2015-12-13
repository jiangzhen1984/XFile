package com.skyworld.api;

import com.skyworld.service.dsf.User;

public class CacheManager {
	
	public static CacheManager instance;
	
	
	private CacheManager() {
		
	}

	
	public static synchronized CacheManager getIntance() {
		if (instance == null) {
			instance = new CacheManager();
		}
		return instance;
	}
	
	
	public User getUser(long token) {
		return null;
	}
}
