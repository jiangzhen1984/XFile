package elacier;

import javax.servlet.http.HttpServletRequest;

public class SmsMMS_Service {

	public static final int ERROR_TWILIO = -1;
	public static final int ERROR_USER = -2;
	public static final int ERROR_NONE = 0;
	public static final int ERROR_RENEW = 1;
	public static final String LOG_TAG = "SmsMMS_Service";
	public SmsMMS_Service() {
		// TODO Auto-generated constructor stub
	}
	
	
	public int smsMMS_new(HttpServletRequest request,SmsMMS_Info newSms)
	{
		newSms.From = request.getParameter("From");
		newSms.To = request.getParameter("To");
		newSms.MessageSid = request.getParameter("MessageSid");
		newSms.AccountSid = request.getParameter("AccountSid");
		if(!newSms.Body.append(request.getParameter("Body"))){
			return ERROR_USER;
		}
		
		newSms.FromCity = request.getParameter("FromCity");
		newSms.FromState = request.getParameter("FromState");
		newSms.FromZip = request.getParameter("FromZip");
		newSms.FromCountry = request.getParameter("FromCountry");
		newSms.ToCity = request.getParameter("FromCity");
		newSms.ToState = request.getParameter("ToState");
		newSms.ToZip = request.getParameter("ToZip");
		newSms.ToCountry = request.getParameter("ToCountry");

		System.out.println(LOG_TAG + ":" + "From-"+newSms.From
						+"To-"+newSms.To
						+"MessageSid-"+newSms.MessageSid
						+"AccountSid-"+newSms.AccountSid
						+"Body-"+newSms.Body.toString()
						+"FromCity-"+newSms.FromCity
						+"FromState-"+newSms.FromState
						+"FromZip-"+newSms.FromZip
						+"FromCountry-"+newSms.FromCountry
						+"ToCity-"+newSms.ToCity
						+"ToState-"+newSms.ToState
						+"ToZip-"+newSms.ToZip
						+"ToCountry-"+newSms.ToCountry);
		return ERROR_NONE;
	}
	
	public int smsMMS_handle(HttpServletRequest request,SmsMMS_Info SmsMMS){
		boolean status = true;
		boolean reInQueue = false;
		int ret = 0;
		synchronized(SmsMMS.smsMMS_getLock()){
			
			switch(SmsMMS.smsMMS_getActionStatus()){
				case SmsMMS_Info.ACTION_STATUS_PREP:
					SmsMMS.smsMMS_setTime(System.currentTimeMillis());
					status = SmsMMS.Body.append(request.getParameter("Body"));
					if(!status){
						ret = ERROR_USER;
						SmsMMS.smsMMS_setActionStatus(SmsMMS_Info.ACTION_STATUS_DROP);
					}else{
						ret = 0;
					}
				break;
				
				case SmsMMS_Info.ACTION_STATUS_PARSING:
					SmsMMS.smsMMS_setTime(System.currentTimeMillis());
					status = SmsMMS.Body.append(request.getParameter("Body"));
					if(!status){
						ret = ERROR_USER;
						SmsMMS.smsMMS_setActionStatus(SmsMMS_Info.ACTION_STATUS_DROP);
					}else{
						SmsMMS.smsMMS_setActionStatus(SmsMMS_Info.ACTION_STATUS_PREP);
						ret = 0;
					}
				break;
				
				case SmsMMS_Info.ACTION_STATUS_NEEDMORE:
					SmsMMS.smsMMS_setTime(System.currentTimeMillis());
					status = SmsMMS.Body.append(request.getParameter("Body"));
					if(!status){
						ret = ERROR_USER;
						SmsMMS.smsMMS_setActionStatus(SmsMMS_Info.ACTION_STATUS_DROP);
					}else{
						SmsMMS.smsMMS_setActionStatus(SmsMMS_Info.ACTION_STATUS_PREP);
						reInQueue = true;
						ret = 0;
					}
				break;
				
				case SmsMMS_Info.ACTION_STATUS_FINISHED:
					ret = ERROR_RENEW;
				break;
				
				case SmsMMS_Info.ACTION_STATUS_DROP:
					ret = 0;
				break;
			}
		}
		
		if(reInQueue){
			ElacierService.getInstance().handleMsg(SmsMMS);
		}
		
		return ret;
		
		
	}

}
