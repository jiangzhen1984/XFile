package com.skyworld.push.msg;

import java.io.Serializable;

public abstract class HttpPushMessage<E extends Comparable<? super E>> implements Serializable, Comparable<HttpPushMessage<E>>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3426575754571205396L;
	
	
	
	
	protected long timestamp;
	
	
	
	public int compareTo(HttpPushMessage<?> other) {
		return this.timestamp > other.timestamp ? 1 : (this.timestamp == other.timestamp ? 0 : -1);
   }

}