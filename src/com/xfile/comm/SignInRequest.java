package com.xfile.comm;

public class SignInRequest extends Request {

	public String userName;
	public String password;
	
	public SignInRequest(String userName, String password) {
		this.mTransationId = System.currentTimeMillis();
		this.userName = userName;
		this.password = password;
	}

	@Override
	public String toURI() {
		return null;
	}
	
	
	
	
	
}
