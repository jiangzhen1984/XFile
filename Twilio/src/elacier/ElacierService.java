package elacier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import com.twilio.sdk.resource.list.MessageList;

import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class ElacierService {
	private boolean TestMode = false;

	private static ElacierService sMe;
	public final static String AccountSid = "AC5401bf3dd6b4dd93741a9938ea537324";
	public final static String AuthToken = "3053c36c7cd98f48ebf6c8d429d7d75b";
	public final static String ElacierNumber = "+12282858213";
	public final static String PC_NUM = "1001";
	public final static String LUNCH_SERVELT_URL = "http://localhost:8080/Twilio/lunch?"+"id=";
	public final static int ELAICER_THREAD_POOLSIZE = 50;
	public ConcurrentHashMap<String, smsLock> lock_map;	
	public ConcurrentHashMap<String, SmsMMS_Info> map ;
	
	public LinkedBlockingQueue<SmsMMS_Info> smsMMS_queue;
	
	public RestaurantQuery restaurant_query;
	public LinkedBlockingQueue<Transaction_Info> confirm_queue;
	public ConcurrentHashMap<Long,Transaction_Info> commu_map; 
	
	public ConcurrentHashMap<String, Transaction_Info> form_map ;
	
	private ExecutorService executor;
	public boolean isTestMode(){
		return TestMode;
	}
	
	public ElacierService() {
		sMe = this;
	}
	
	public static ElacierService getInstance()
	{
		if (sMe == null) {
			throw new IllegalStateException("No ElacierService here!");
		}
		return sMe;
	}
	
	public void onCreate(){
		lock_map = new ConcurrentHashMap<String, smsLock>();
		map = new ConcurrentHashMap<String, SmsMMS_Info>();
		smsMMS_queue = new LinkedBlockingQueue<SmsMMS_Info>();
		form_map = new ConcurrentHashMap<String, Transaction_Info>(); 
		restaurant_query = new RestaurantQuery();
		confirm_queue = new LinkedBlockingQueue<Transaction_Info>();
		commu_map = new  ConcurrentHashMap<Long, Transaction_Info>(); 
		
		executor = Executors.newFixedThreadPool(ElacierService.ELAICER_THREAD_POOLSIZE);
	}
	
	public void handleMsg(SmsMMS_Info msg){
		Runnable runner = new ElacierRunnable(msg);
		executor.execute(runner);
	}
	
	public void putsmsMMSQueue(SmsMMS_Info msg){
		try{
			smsMMS_queue.put(msg);
		}catch(InterruptedException e){
			e.printStackTrace();
			ElacierService.getInstance().removeSpecifidMsg(msg.From,msg);
		}
	}
	
	public SmsMMS_Info takesmsMMSQueue(){
		SmsMMS_Info msg=null;
		try{
			msg = smsMMS_queue.take();
		} catch(InterruptedException e) {
			e.printStackTrace();
			msg = null;
			return msg;
		} 
		
		return msg;
	}
	
	public void putConfirmQueue(Transaction_Info msg){
		try{
			confirm_queue.put(msg);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	
	public Transaction_Info takeConfirmQueue(){
		Transaction_Info msg=null;
		try{
			msg = confirm_queue.take();
		} catch(InterruptedException e) {
			e.printStackTrace();
			msg = null;
			return msg;
		} 
		
		return msg;
	}



	public smsLock putLock(String key, smsLock lock){
		return lock_map.put(key,lock);
	}

	public smsLock getLock(String key){
		return lock_map.get(key);
	}

	public smsLock removeLock(String key)
	{
		return lock_map.remove(key);
	}
	
	public boolean isMsgExisted(String From){
		return map.containsKey(From);
	}

	
	
	public SmsMMS_Info putMsg(String key, SmsMMS_Info msg){
		return map.put(key, msg);
	}
	
	public SmsMMS_Info getMsg(String key)
	{
		return map.get(key);
	}
	
	public SmsMMS_Info removeMsg(String key)
	{
		return map.remove(key);
	}
	
	public boolean removeSpecifidMsg(String key, SmsMMS_Info msg)
	{
		return map.remove(key,msg);
	}
	
	public Transaction_Info putFormMsg(String key, Transaction_Info msg){
		return form_map.put(key, msg);
	}
	
	public Transaction_Info getFormMsg(String key)
	{
		return form_map.get(key);
	}
	
	public Transaction_Info removeFormMsg(String key)
	{
		return form_map.remove(key);
	}
	
	public Transaction_Info putCommuMsg(Long key, Transaction_Info msg){
		return commu_map.put(key, msg);
	}
	
	public Transaction_Info getCommuMsg(Long key)
	{
		return commu_map.get(key);
	}
	
	public Transaction_Info removeCommuMsg(Long key)
	{
		return commu_map.remove(key);
	}
	
	public boolean removeSpecifidCommuMsg(Long key,Transaction_Info msg)
	{
		return commu_map.remove(key,msg);
	}





	/*twilio client request: sending SMS to end user*/
	public boolean sendTwilioSMS(String to, String body) {
		try{
			TwilioRestClient client = new TwilioRestClient(AccountSid, AuthToken);

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("Body", body));
			params.add(new BasicNameValuePair("To", to));
			params.add(new BasicNameValuePair("From", ElacierNumber));
     
			MessageFactory messageFactory = client.getAccount().getMessageFactory();
			Message message = messageFactory.create(params);
			System.out.println(message.getSid());
		
			return true;
		}catch(TwilioRestException e){
			e.printStackTrace();
			return false;
		}
	}
	
	

}
