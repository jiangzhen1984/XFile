package twilio;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.verbs.TwiMLException;
import com.twilio.sdk.verbs.TwiMLResponse;
import com.twilio.sdk.verbs.Message;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.concurrent.ExecutorService;  
import java.util.concurrent.Executors;  
import java.util.concurrent.TimeUnit;

import elacier.*;

/**
 * Servlet implementation class TwilioServlet
 */
@WebServlet("/TwilioServlet")
public class TwilioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String LOG_TAG = "TwilioServlet";
    
	private ServletContext mContext; 
	private int test_offset = 1;
	private long trans_offset = 1000;
	
	private ElacierService mElacierServer;
	private SmsMMS_Service mSmsMMS_Service; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TwilioServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		// TODO Auto-generated method stub
		/*
		if(true){
			responseTwilio(request,response);
			return;
		}*/
		/*Check if request is from Elacier account in Twilio*/
		
		//System.out.println(LOG_TAG + ":" + "isFromTwilio error");
		if(!isFromTwilio(request)){
			System.out.println(LOG_TAG + ":" + "isFromTwilio error");
			//sendErrorBack(response,HttpServletResponse.SC_FORBIDDEN);
			responseTwilioEmpty(request,response);
			return;
		}
		
		/*Check if From number is valid*/
		if(!isFromNumValid(request)){
			System.out.println(LOG_TAG + ":" + "isFromNumValid error");
			//sendErrorBack(response,HttpServletResponse.SC_FORBIDDEN);
			responseTwilioEmpty(request,response);
			return;
		}
		
		/*Check if request is Send to Elacier twilio number*/
		if(!isToElacierNumber(request)){
			System.out.println(LOG_TAG + ":" + "isToElacierNumber error");
			//sendErrorBack(response,HttpServletResponse.SC_FORBIDDEN);
			responseTwilioEmpty(request,response);
			return;
		}

		if(mElacierServer.isTestMode()){
			responseTwilio(request,response);
			return;
		}
		
		SmsMMS_Info msg;
		String from_phone = request.getParameter("From");
		smsLock lock;


		/*make sure one user send smsMMS one by one to our server*/
		if((lock = mElacierServer.getLock(from_phone))==null){
			synchronized(smsLock.class){
				if((lock = mElacierServer.getLock(from_phone))==null){
					lock = new smsLock();
					mElacierServer.putLock(from_phone,lock);
				}
			}
		}

		synchronized(lock){
			msg = mElacierServer.getMsg(from_phone);
			if(msg == null){
				msg = new SmsMMS_Info();
	
				int error = mSmsMMS_Service.smsMMS_new(request,msg);

				if(error == SmsMMS_Service.ERROR_TWILIO){
					System.out.println(LOG_TAG + ":" + "smsMMS_parser error");
					responseTwilioEmpty(request,response);
					return;
				}else if(error == SmsMMS_Service.ERROR_USER){
					System.out.println(LOG_TAG + ":" + "smsMMS_parser user error");
					responseTwilioEmpty(request,response);
					return;
				}
				mElacierServer.putMsg(from_phone,msg);
				mElacierServer.handleMsg(msg);

			}else{
				int ret = mSmsMMS_Service.smsMMS_handle(request,msg);
				if(ret == SmsMMS_Service.ERROR_USER){
					System.out.println(LOG_TAG + ":" + "smsMMS_parser user error");
					mElacierServer.removeSpecifidMsg(from_phone,msg);
					responseTwilioEmpty(request,response);
					return;
				}else if(ret == SmsMMS_Service.ERROR_RENEW ){
					System.out.println(LOG_TAG + ":" + "Msg has been finished, renew one");
					mElacierServer.removeSpecifidMsg(from_phone,msg);
					msg = new SmsMMS_Info();
					int error = mSmsMMS_Service.smsMMS_new(request,msg);
			
					if(error == SmsMMS_Service.ERROR_TWILIO){
						System.out.println(LOG_TAG + ":" + "smsMMS_parser error");
						responseTwilioEmpty(request,response);
						return;
					}else if(error == SmsMMS_Service.ERROR_USER){
						System.out.println(LOG_TAG + ":" + "smsMMS_parser user error");
						responseTwilioEmpty(request,response);
						return;
					}
				
					mElacierServer.putMsg(from_phone,msg);
					mElacierServer.handleMsg(msg);
				}
			}
		}
		
		responseTwilioEmpty(request,response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	
	
	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);
		mContext = config.getServletContext();
		System.out.println("Twilio Server started");
		//System.out.println("Observation ServletConfig:");
		//System.out.println(config);
		//System.out.println("Observation ServletInfo:");
		//System.out.println(getServletInfo());
		
		mElacierServer = new ElacierService();
		mElacierServer.onCreate();
		
		mSmsMMS_Service = new SmsMMS_Service();
		
		Thread gc = new GCThread("gc thread");
		gc.start();
	}
	
	public void destroy()
	{
		super.destroy();
		System.out.println("TwilioServlet destroyed");
	}
	//for security, need to check if the request is from twilio
	private boolean isFromTwilio(HttpServletRequest request)
	{
		String AccountSid = request.getParameter("AccountSid");
		
		if(AccountSid!=null && AccountSid.equals(ElacierService.AccountSid))
			return true;
		else
			return false;
	}
	
	//for security, need to check it the reqeust is from Elacier Twilio PhoneNumber
	private boolean isToElacierNumber(HttpServletRequest request)
	{
		String PhoneNumber = request.getParameter("To");
		if(PhoneNumber!=null && PhoneNumber.equalsIgnoreCase(ElacierService.ElacierNumber))
			return true;
		else
			return false;
	}
	
	//for security, check is from number is valid
	private boolean isFromNumValid(HttpServletRequest request)
	{
		String PhoneNumber = request.getParameter("From");
		if(PhoneNumber!=null)
			return true;
		else
			return false;
	}
	
	private void sendErrorBack(HttpServletResponse response,int errCode) throws java.io.IOException
	{
		response.sendError(errCode);
		PrintWriter out = response.getWriter();
		out.flush();
		out.close();
	}
	
	private String getUserPhoneNum(HttpServletRequest request, HttpServletResponse response)
	{
		String phoneNum = null;
		
		phoneNum = "13911791236" + (test_offset++);
		
		return phoneNum;
	}
	
	private String newTransactionId()
	{
		String newTrans;
		newTrans = ""+ System.currentTimeMillis() + "-" + (trans_offset++);
		return newTrans;
	}
	
	private boolean isOrderRequest(HttpServletRequest request, HttpServletResponse response)
	{
		if(request.getQueryString()!=null){
			return false;
		}else{
			return true;
		}
	}
	
	private String getTransactionIdFromURL(HttpServletRequest request)
	{
		String query = request.getQueryString();
		int start;
		start = query.indexOf("id=") + 3;
		String tran_id = query.substring(start);
		
		String prams = request.getParameter("id");
		System.out.println("Params = " + prams);
		System.out.println(query);
		System.out.println(tran_id);
		return tran_id;
	}
	
	private boolean isTransValid(HttpServletRequest request,String tran_id)
	{
		if(mContext.getAttribute(tran_id)==null)
		{
			return false;
		}else{
			return true;
		}
		
	}
	
	private boolean responseTwilio(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
	 TwiMLResponse twiml = new TwiMLResponse();
        Message message = new Message("kevin test received");
        try {
            twiml.append(message);
        } catch (TwiMLException e) {
            e.printStackTrace();
        }
 
        response.setContentType("application/xml");
        response.getWriter().print(twiml.toXML());
        
        return true;
	}

	private boolean responseTwilioEmpty(HttpServletRequest request, HttpServletResponse response) throws IOException{
		int errCode = HttpServletResponse.SC_OK;
		response.sendError(errCode);
		PrintWriter out = response.getWriter();
		out.flush();
		out.close();
		
		return true;
	}
	
	
	

}
