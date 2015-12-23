package com.skyworld.pushimpl;

import java.io.IOException;
import java.io.InputStream;

import com.skyworld.push.HttpPushMessageTransformer;
import com.skyworld.push.msg.HttpPushMessage;

public class JSONTransformer implements HttpPushMessageTransformer {

	@Override
	public HttpPushMessage<?> unserialize(InputStream in) throws IOException {
		return null;
	}

	@Override
	public String serialize(HttpPushMessage<?> message) {
		return "{header:{ret: 0}}";
	}

	@Override
	public String getContentType() {
		return "application/json";
	}

}
