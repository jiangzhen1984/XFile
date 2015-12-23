package com.skyworld.pushimpl;

import com.skyworld.push.msg.HttpPushMessage;

public class DebugMessage extends HttpPushMessage {

	public DebugMessage() {
		super();
	}

	@Override
	public int compareTo(Object o) {
		return 0;
	}
	
	

}
