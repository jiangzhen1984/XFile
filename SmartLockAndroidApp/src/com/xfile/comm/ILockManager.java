package com.xfile.comm;

public interface ILockManager {
	
	public static final String EXTRA_LOCK_ID = "lock_id";

	public static final String EXTRA_LOCK_MATCH_CODE = "match_code";
	
	public static final String EXTRA_LOCK_SECURE_CODE = "secure_code";
	
	
	
	public boolean lock(boolean flag);

}
