package elacier;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GCThread extends Thread{

	final long timeInterval = 1*60*60*1000;
	
	public GCThread(String name) {
		super(name);
	}
	
	public void run(){
		System.out.println("gc thread is running");
		while(true){
			try{
				synchronized(this) {
					wait(timeInterval);
				}
				cleanMoreDataMsg();
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		
	}

	private void cleanMoreDataMsg(){
		System.out.println("gc thread cleanMoreDataMsg");
		ElacierService se = ElacierService.getInstance();
		Iterator<String> it = se.map.keySet().iterator();
		String phone = null;
		
		while (it.hasNext()) {
			phone = (String)it.next();
			SmsMMS_Info msg =  se.getMsg(phone);
			if((System.currentTimeMillis() - msg.smsMMS_getTime())> 2*timeInterval  
					&& msg.smsMMS_getActionStatus()==SmsMMS_Info.ACTION_STATUS_NEEDMORE){
				se.removeSpecifidMsg(phone, msg);
			}
		}
	}

}
