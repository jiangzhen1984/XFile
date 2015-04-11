package com.xfile.comm;

public class SignInResponse extends Response {

	public SignInResponse(Request req, ResponseState mState, Object result) {
		super(req, mState, result);
	}

	public SignInResponse(Request req) {
		super(req, ResponseState.SUCCESS, null);
	}
}
