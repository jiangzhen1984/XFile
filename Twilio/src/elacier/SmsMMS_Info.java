package elacier;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

public class SmsMMS_Info {

	public static final int ACTION_STATUS_PREP = 0;
	public static final int ACTION_STATUS_PARSING = 1;
	public static final int ACTION_STATUS_NEEDMORE = 2;
	public static final int ACTION_STATUS_FINISHED = 3;
	public static final int ACTION_STATUS_DROP = 4;
	
	
	public static final int DATA_STATUS_MORE_NONE = 0x00000000;
	public static final int DATA_STATUS_MORE_PLACE = 0x00000001;
	public static final int DATA_STATUS_MORE_PERSON = 0x00000002;
	public static final int DATA_STATUS_MORE_RESTAURANT_NAME = 0x00000004;
	public static final int DATA_STATUS_MORE_FOOD = 0x00000008;
	
	public static final int BOADYARRAY_SIZE = 100;
	
	
	
	/*below is request*/
	public String From;
	public String To;
	public String MessageSid;
	public String AccountSid;
	public BodyText Body;
	//public String NumMedia;
	
	/*below is request for MMS if NumMedia>0*/
	//public String MediaContentType[];
	//public String MediaUrl[];
	
	/*below is optional*/
	public String FromCity;
	public String FromState;
	public String FromZip;
	public String FromCountry;
	public String ToCity;
	public String ToState;
	public String ToZip;
	public String ToCountry;
	
	/*Elacier create info for this sms/mms*/
	private Object lock;
	private long time;
	public int action_status;
	public int data_status;
	
	
	public class BodyText{
		public int curLength;
		public String [] BodyArray;
		public String TotalString;
		
		public BodyText(){
			curLength = 0;
			BodyArray = new String[BOADYARRAY_SIZE];
		}
		
		public synchronized boolean append (String text){
			if(curLength == BOADYARRAY_SIZE){
				return false;
			}else{
				BodyArray[curLength] = text;
				curLength++;
				for(int i=0;i<curLength;i++){
					TotalString = TotalString + BodyArray[i];
				}
				return true;
			}
		}

		public String toString(){
			return TotalString;
		}

		
	}
	
	public SmsMMS_Info() {
		// TODO Auto-generated constructor stub
		Body = new BodyText();
		time = System.currentTimeMillis();
		action_status = SmsMMS_Info.ACTION_STATUS_PREP;
		data_status = SmsMMS_Info.DATA_STATUS_MORE_NONE;
		lock = new Object();
	}

	public Object smsMMS_getLock(){
		return lock;
	}
		
	public void smsMMS_setActionStatus(int action_status){

			this.action_status = action_status;

	}
	
	public int smsMMS_getActionStatus(){
			return this.action_status;
	}

	public int smsMMS_getDataStatus(){
		return data_status;
	}

	public void smsMMS_setDataStatus(int status){
		data_status = status;
	}

	public void smsMMS_setTime(long time){
		this.time = time;
	}

	public long smsMMS_getTime(){
		return time;
	}
}
