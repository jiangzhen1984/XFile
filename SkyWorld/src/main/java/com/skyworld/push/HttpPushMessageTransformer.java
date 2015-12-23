package com.skyworld.push;

import java.io.IOException;
import java.io.InputStream;

import com.skyworld.push.msg.HttpPushMessage;

public interface HttpPushMessageTransformer {
	
	
	
	public HttpPushMessage<?> unserialize(InputStream in) throws IOException;
	
	
	
	public String serialize(HttpPushMessage<?> message);
	
	public String getContentType();
	

}
