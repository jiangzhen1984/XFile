package elacier;

import java.util.List;

import com.twilio.sdk.TwilioRestException;

public class ElacierThread extends Thread{

    static final String LOG_TAG = "ElacierThread";
    static final int PARSE_RESULT_FINISH = 0;
    static final int PARSE_RESULT_MORE = 1;
    static final int PARSE_RESULT_EXCEPTION = 2;
    static final int PARSE_RESULT_NONE = 3;
    
    QueryParametersAnalyzer analyzer;
    IRestaurantService IRService;
	public ElacierThread(String name){
        super(name);
        analyzer = new DefaultQueryParametersAnalyzer();
        IRService = new RestaurantServiceDBImpl();
    }
    
    public void run() {
    	RestaurantQueryParameters parm = null;
    	while(true){
    		SmsMMS_Info msg = ElacierService.getInstance().takesmsMMSQueue();
    		if(msg == null) 
    			continue;
    		
    		int action_status;
    		int data_status;
    		int next_status=SmsMMS_Info.ACTION_STATUS_PARSING;
    		int parse_result = PARSE_RESULT_NONE;
    		String [] analyze_text;
    		int length;
    		boolean start_search = false;
    		
    		while(next_status == SmsMMS_Info.ACTION_STATUS_PARSING){
    			synchronized(msg.smsMMS_getLock()){
    				action_status = msg.smsMMS_getActionStatus();
    			
    				if(action_status == SmsMMS_Info.ACTION_STATUS_PREP){
    					msg.smsMMS_setActionStatus(SmsMMS_Info.ACTION_STATUS_PARSING);
    					analyze_text = msg.Body.BodyArray;
    					length = msg.Body.curLength;
    				}else if(action_status == SmsMMS_Info.ACTION_STATUS_DROP){
    					break;
    				}else if(action_status == SmsMMS_Info.ACTION_STATUS_FINISHED){
    					System.out.println("ElacierThread run: never run here!!!!!");
    					break;
    				}else{
    					System.out.println("ElacierThread run: never run here!!!!!");
    					msg.smsMMS_setActionStatus(SmsMMS_Info.ACTION_STATUS_PARSING);
    					analyze_text = msg.Body.BodyArray;
    					length = msg.Body.curLength;
    				}
    			}
    			
    			/*do parse and get next_status*/
    			
    			try {
    				parm = analyzer.analyze(analyze_text,length);
    				parse_result = parm.result;
    			}catch(Exception e){
    				e.printStackTrace();
    				next_status = SmsMMS_Info.ACTION_STATUS_PARSING;
    				parse_result = PARSE_RESULT_EXCEPTION;
    			} 
    			/**/
    			synchronized(msg.smsMMS_getLock()){
    				action_status = msg.smsMMS_getActionStatus();
    			
    				if(action_status == SmsMMS_Info.ACTION_STATUS_PREP){
    					msg.smsMMS_setActionStatus(SmsMMS_Info.ACTION_STATUS_PARSING);
    					next_status = SmsMMS_Info.ACTION_STATUS_PARSING;
    					continue;
    				}else if(action_status == SmsMMS_Info.ACTION_STATUS_DROP){
    					break;
    				}else{
    					if(parse_result == PARSE_RESULT_MORE){
    						msg.smsMMS_setActionStatus(SmsMMS_Info.ACTION_STATUS_NEEDMORE);
    						next_status = SmsMMS_Info.ACTION_STATUS_NEEDMORE;
    						break;
    					}else if(parse_result == PARSE_RESULT_FINISH){
    						msg.smsMMS_setActionStatus(SmsMMS_Info.ACTION_STATUS_FINISHED);
    						start_search = true;
    						break;
    					}else if(parse_result == PARSE_RESULT_EXCEPTION){
    						msg.smsMMS_setActionStatus(SmsMMS_Info.ACTION_STATUS_PREP);
    						next_status = SmsMMS_Info.ACTION_STATUS_PARSING;
    						continue;
    					}
    					
    				}
    			}
    			
    		}
    		
    		/*parameter is complete , send confirm msg to restaurant*/
			if(start_search && parm!=null){
				ElacierService.getInstance().removeSpecifidMsg(msg.From,msg);
				List<Restaurant> shop_list = IRService.queryRestaurant(parm);
				if(shop_list!=null && shop_list.size()>0){
					Transaction_Info trans_info = new Transaction_Info();
					trans_info.From = msg.From;
					trans_info.persons = parm.persons;
					trans_info.shop_list = shop_list;

					
					/*ask confirm thread to contact with restaurant*/
					//ElacierService.getInstance().putConfirmQueue(trans_info);
					int result = ElacierService.getInstance().restaurant_query.queryRestaurantAvailable(shop_list, trans_info);

					
					if(result != IRestaurantCommunication.RESTAURANT_COMM_OK){
						/*send msg back to user for no search result*/
						ElacierService.getInstance().sendTwilioSMS(msg.From, "no restaurant is available now");
					}else{
						/*query cmd send to restaurant and restaurant receiver should receive answer*/
						System.out.println(LOG_TAG + ":" + "query info send to socket server successfully");
					}



					
					
				}else{
					/*send msg back to user for no search result*/
					ElacierService.getInstance().sendTwilioSMS(msg.From, "no restaurant is available now");
				}
			}else if(next_status == SmsMMS_Info.ACTION_STATUS_NEEDMORE){
				/*send need more sms to user*/
				switch(msg.smsMMS_getDataStatus()){
					case SmsMMS_Info.DATA_STATUS_MORE_PLACE:
						ElacierService.getInstance().sendTwilioSMS(msg.From, "Give More Info: where you want to have lunch ??");
						break;

					case SmsMMS_Info.DATA_STATUS_MORE_PERSON:
						ElacierService.getInstance().sendTwilioSMS(msg.From, "Give More Info: how many people for lunch ??");
						break;

					case SmsMMS_Info.DATA_STATUS_MORE_RESTAURANT_NAME:
					case SmsMMS_Info.DATA_STATUS_MORE_FOOD:
						ElacierService.getInstance().sendTwilioSMS(msg.From, "Give More Info: which restaurant or  what kind of food for lunch??");
						break;
						
					default:
						System.out.println(LOG_TAG + ":" + "Never run here !!!!");
						break;

				}
				
			}
    		
    		
    		
    		
    	}
    	
    }


	
    
}