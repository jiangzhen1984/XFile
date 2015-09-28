package elacier;

import java.util.List;

import elacier.process.GuestInformation;
import elacier.process.GuestTransaction;
import elacier.provider.msg.InquiryMessage;
import elacier.restaurant.Restaurant;
import elacier.service.IRestaurantService;
import elacier.service.RestaurantQueryParameters;
import elacier.service.RestaurantServiceDBImpl;
import elacier.service.ServiceFactory;
import elacier.transaction.TransactionManager;

public class ElacierRunnable implements Runnable{

	static final String LOG_TAG = "ElacierRunnable";
	static final int PARSE_RESULT_FINISH = 0;
	static final int PARSE_RESULT_MORE = 1;
	static final int PARSE_RESULT_EXCEPTION = 2;
	static final int PARSE_RESULT_NONE = 3;

	
	SmsMMS_Info msg;
	QueryParametersAnalyzer analyzer;
	IRestaurantService IRService;
	
	public ElacierRunnable(SmsMMS_Info smsMMS) {
		// TODO Auto-generated constructor stub
		msg = smsMMS;
		analyzer = new DefaultQueryParametersAnalyzer();
        IRService = ServiceFactory.getRestaurantService();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		int action_status;
    		int data_status;
    		int next_status=SmsMMS_Info.ACTION_STATUS_PARSING;
    		int parse_result = PARSE_RESULT_NONE;
    		String [] analyze_text;
    		int length;
    		boolean start_search = false;
    		RestaurantQueryParameters parm = null;
    		
    		while(next_status == SmsMMS_Info.ACTION_STATUS_PARSING){
    			synchronized(msg.smsMMS_getLock()){
    				action_status = msg.smsMMS_getActionStatus();
    			
    				if(action_status == SmsMMS_Info.ACTION_STATUS_PREP){
    					msg.smsMMS_setActionStatus(SmsMMS_Info.ACTION_STATUS_PARSING);
    					analyze_text = msg.Body.BodyArray;
    					length = msg.Body.curLength;
    				}else if(action_status == SmsMMS_Info.ACTION_STATUS_DROP){
    					next_status=SmsMMS_Info.ACTION_STATUS_DROP;
    					break;
    				}else if(action_status == SmsMMS_Info.ACTION_STATUS_FINISHED){
    					System.out.println(LOG_TAG+" run: never run here!!!!!");
    					break;
    				}else{
    					System.out.println(LOG_TAG+" run: never run here!!!!!");
    					break;
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

    			synchronized(msg.smsMMS_getLock()){
    				action_status = msg.smsMMS_getActionStatus();
    			
    				if(action_status == SmsMMS_Info.ACTION_STATUS_PREP){
    					msg.smsMMS_setActionStatus(SmsMMS_Info.ACTION_STATUS_PARSING);
    					next_status = SmsMMS_Info.ACTION_STATUS_PARSING;
    					continue;
    				}else if(action_status == SmsMMS_Info.ACTION_STATUS_DROP){
    					next_status = SmsMMS_Info.ACTION_STATUS_DROP;
    					break;
    				}else{
    					if(parse_result == PARSE_RESULT_MORE){
    						msg.smsMMS_setActionStatus(SmsMMS_Info.ACTION_STATUS_NEEDMORE);
    						next_status = SmsMMS_Info.ACTION_STATUS_NEEDMORE;
    						break;
    					}else if(parse_result == PARSE_RESULT_FINISH){
    						msg.smsMMS_setActionStatus(SmsMMS_Info.ACTION_STATUS_FINISHED);
    						next_status = SmsMMS_Info.ACTION_STATUS_FINISHED;
    						start_search = true;
    						break;
    					}else if(parse_result == PARSE_RESULT_EXCEPTION){
    						msg.smsMMS_setActionStatus(SmsMMS_Info.ACTION_STATUS_DROP);
    						next_status = SmsMMS_Info.ACTION_STATUS_DROP;
    					}
    					
    				}
    			}
    			
    		}
    		
    		/*parameter is complete , send confirm msg to restaurant*/
			if(start_search){
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
						//Start order transaction
						GuestTransaction trans = (GuestTransaction)TransactionManager.getInstance().createTransaction(GuestTransaction.class);
						GuestInformation gi = new GuestInformation();
						gi.setFav(parm.favKey);
						gi.setGuestPhone(msg.From);
						//gi.setGuestName(guestName);
						gi.setNums(parm.persons);
						//Default for always on site
						gi.setType(InquiryMessage.INQUIRY_OPT_TYPE_ON_SITE);
						trans.setInformation(gi);
						//Add restaurants
						trans.addAvailableRestaurant(shop_list);
						
						boolean ret = TransactionManager.getInstance().beginTransaction(trans);
						if (!ret) {
							ElacierService.getInstance().sendTwilioSMS(msg.From, "Internal trnsaction failed!");
						}
						
						/*query cmd send to restaurant and restaurant receiver should receive answer*/
						System.out.println(LOG_TAG + ":" + "query info send to socket server successfully");
					}
				}else{
					/*send msg back to user for no search result*/
					ElacierService.getInstance().sendTwilioSMS(msg.From, "no restaurant is available now");
				}
			}else if(next_status == SmsMMS_Info.ACTION_STATUS_NEEDMORE){
				/*send need more sms to user*/
				String reply = "Give More Info:";
				int status = msg.smsMMS_getDataStatus();
				
				if((status & SmsMMS_Info.DATA_STATUS_MORE_PLACE) > 0){
					reply = reply + "where?";
				}
				
				if((status & SmsMMS_Info.DATA_STATUS_MORE_PERSON) > 0){
					reply = reply + "how many people?";
				}
				
				if((status & SmsMMS_Info.DATA_STATUS_MORE_RESTAURANT_NAME)>0){
					if((status & SmsMMS_Info.DATA_STATUS_MORE_FOOD)>0){
						reply = reply + "which restaurant or  what kind of food?";
					}
				}
				
				ElacierService.getInstance().sendTwilioSMS(msg.From, reply);
				
				
				
			}else if(parse_result == PARSE_RESULT_EXCEPTION){
				ElacierService.getInstance().removeSpecifidMsg(msg.From,msg);
				ElacierService.getInstance().sendTwilioSMS(msg.From, "Elaicer server is down now, please try later");
			} else if(parse_result == PARSE_RESULT_NONE){
				ElacierService.getInstance().removeSpecifidMsg(msg.From,msg);
				ElacierService.getInstance().sendTwilioSMS(msg.From, "Give More Info for lunch??");
			}
	}
	
	

}
