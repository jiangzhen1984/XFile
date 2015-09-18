package elacier.provider.msg;

import java.util.Date;
import java.util.UUID;

import org.json.simple.JSONObject;

/**
 * This is used to server respond client request result.
 * @author 28851274
 *
 */
public class InquiryResponse extends InquiryMessage {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -364519801698918917L;

	/**
	 * Mark for server respond result
	 */
	public static final int INQUIRY_RESPOND_CLIENT_RESPONSE_OK = 0;
	
	public static final int INQUIRY_RESPOND_CLIENT_RESPONSE_FAILED = 1;
	
	public static final int INQUIRY_RESPOND_CLIENT_RESPONSE_TIME_OUT = 2;
	
	
	private int result;
	

	public InquiryResponse(Token token, Terminal terminal,
			long transactionId, int ret) {
		super(MessageVersion.V01, MessageType.RESPONSE, new Date(), token,
				terminal, UUID.randomUUID().toString(), transactionId,
				InquiryMessage.OPT_SERVER_RESPOND_CLIENT);
		this.result = ret;
	}


	public int getResult() {
		return result;
	}


	public void setResult(int result) {
		this.result = result;
	}

	

}
