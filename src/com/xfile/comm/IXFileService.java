package com.xfile.comm;

public interface IXFileService {

	public void signIn(Request req, ResponseListener listener);

	public void signUp(Request req, ResponseListener listener);

	public void queryDoctors(Request req, ResponseListener listener);
	
	public void cancelWaiting(Request req);
}
