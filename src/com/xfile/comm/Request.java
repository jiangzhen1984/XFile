package com.xfile.comm;

public abstract class Request {

	
	protected long mTransationId;
	
	
	public abstract String toURI();

	@Override
	public String toString() {
		return "Request [mTransationId=" + mTransationId + "]";
	}
	
	
}
