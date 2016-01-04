package com.skyworld.push;

import java.io.IOException;
import java.io.Serializable;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.skyworld.cache.Token;
import com.skyworld.push.event.MessageEvent;
import com.skyworld.push.event.SHPEvent;

public class ClientTerminal  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4197293545564086480L;
	
	private Log log = LogFactory.getLog(this.getClass());


	protected Token token;
	
	
	protected long lastUpdate;
	
	protected boolean isTimeout;
	
	protected long timeout;
	
	
	protected Queue<SHPEvent> events;
	

	protected TerminalSocket socket;
	
	
	protected  HttpPushMessageTransformer transformer;

	public ClientTerminal(Token token, TerminalSocket socket, HttpPushMessageTransformer transformer) {
		this.token = token;
		this.socket = socket;
		this.transformer = transformer;
		lastUpdate = System.currentTimeMillis();
		events = new LinkedBlockingDeque<SHPEvent>();
	}
	
	
	public void updateTimeStamp() {
		synchronized(this) {
			this.lastUpdate = System.currentTimeMillis();
		}
	}
	
	
	
	
	public boolean isTimeout() {
		return isTimeout;
	}


	public void setTimeout(boolean isTimeout) {
		synchronized(this) {
			this.isTimeout = isTimeout;
		}
	}


	public long getTimeout() {
		return timeout;
	}


	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}


	public boolean postEvents(SHPEvent ev) {
		if (ev == null) {
			throw new NullPointerException(" event is  null");
		}
		if (!socket.isAvailable()) {
			return false;
		}
		boolean flag = false;
		synchronized (events) {
			events.add(ev);
			events.notify();
			log.info(" ==== > post event :" + ev+"  to queue :"+events+" client :" + this);
		}
		
		return flag;
	}

	
	
	public void waitForEvent() {
		synchronized (events) {
			SHPEvent ev = events.poll();
			if (ev == null) {
				try {
					events.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			ev = events.poll();
			if (ev == null) {
				throw new IllegalArgumentException(" event is null ");
			}
			
			try {
				log.info("start to handle event: " + ev);
				handleEvent(ev);
			} catch (IOException e) {
				log.error("handle event error " + ev , e);
			}
			
		}
	}
	
	
	
	private void handleEvent(SHPEvent event) throws IOException {
		SHPEvent.Type st = event.getEvType();
		switch (st) {
		case POST_MESSAGE:
			String data = transformer.serialize(((MessageEvent)event).getMessage());
			socket.setHeader("Content-Type", transformer.getContentType());
			//socket.setHeader("Content-Length", (data.length()+"Content-Type".length() + transformer.getContentType().length() +"Content-Length".length())+"");
			socket.write(data);
			log.info(">>>>" + data);
			break;
		case CLOSE:
			break;
		case TIME_OUT:
			this.isTimeout = true;
			this.timeout = System.currentTimeMillis();
			socket.writeError(Code.SC_REQUEST_TIMEOUT, " time out ");
			break;
		default:
			break;
		}
	}


	@Override
	public String toString() {
		return "[token : "+token+"  lastUpdate:+"+lastUpdate+"  isTimeout:+"+isTimeout+"   timeout:"+timeout+"]";
	}
	
	
	
	
	
}
