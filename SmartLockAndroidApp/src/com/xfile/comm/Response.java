package com.xfile.comm;

public abstract class Response {
	
	protected Request req;

	protected ResponseState mState;
	
	protected Object result;

	public Response(Request req, ResponseState mState, Object result) {
		super();
		this.req = req;
		this.mState = mState;
		this.result = result;
	}

	public Request getReq() {
		return req;
	}

	public void setReq(Request req) {
		this.req = req;
	}

	public ResponseState getState() {
		return mState;
	}

	public void setState(ResponseState state) {
		this.mState = state;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
	
	
	
}
