package elacier;

import java.util.Map;

public class GCThread extends Thread{

	final long timeInterval = 1*60*60*1000;
	
	public GCThread(String name) {
		super(name);
	}
	
	public void run(){
		System.out.println("gc thread is running");
		while(true){
			try{
				Thread.sleep(timeInterval);
				cleanMoreDataMsg();
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		
	}

	private void cleanMoreDataMsg(){
		ElacierService se = ElacierService.getInstance();
		
		for(Map.Entry<String, SmsMMS_Info> e: se.map.entrySet() ){
			SmsMMS_Info msg = e.getValue();
			String phone = e.getKey();

			if((System.currentTimeMillis() - msg.smsMMS_getTime())> 2*timeInterval  
				&& msg.smsMMS_getActionStatus()==SmsMMS_Info.ACTION_STATUS_NEEDMORE){
				se.removeSpecifidMsg(phone, msg);
			}
		}
		
	}

}
