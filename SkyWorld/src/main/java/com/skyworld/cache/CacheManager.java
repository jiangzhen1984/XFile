package com.skyworld.cache;

import java.util.concurrent.ConcurrentHashMap;

import com.skyworld.service.dsf.User;

public class CacheManager {
	
	private ConcurrentHashMap<Token, User> mUserCache;
	private ConcurrentHashMap<Long, Token> mUserIdCache;
	
	public static CacheManager instance;
	
	
	private CacheManager() {
		mUserCache = new ConcurrentHashMap<Token, User>();
		mUserIdCache = new ConcurrentHashMap<Long, Token>();
	}

	
	public static synchronized CacheManager getIntance() {
		if (instance == null) {
			instance = new CacheManager();
		}
		return instance;
	}
	
	
	public User getUser(Token token) {
		return mUserCache.get(token);
	}
	
	
	public Token saveUser(User user) {
		if (user == null) {
			throw new NullPointerException(" user is null");
		}
		//remove cache
		Token cacheToken = mUserIdCache.get(user.getId());
		if (cacheToken != null) {
			mUserCache.remove(cacheToken);
		}
		
		LongToken token = (LongToken)TokenFactory.generateLongToken();
		user.setToken(token);
		mUserCache.put(token, user);
		mUserIdCache.put(user.getId(), token);
		
		return token;
	}
}
