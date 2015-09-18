package elacier.provider.msg;

import java.util.Date;
import java.util.UUID;

import org.json.simple.JSONObject;

import elacier.transaction.Token;

/**
 * This is used to client respond inquiry notification.
 * @author 28851274
 *
 */
public class InquiryRespondNotification extends InquiryMessage {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4450687327386261500L;

	/**
	 * Mark for result of respond notification
	 */
	public static final int INQUIRY_RESPOND_NOTIFICAITON_RET_OK = 0;
	
	public static final int INQUIRY_RESPOND_NOTIFICAITON_RET_OK_BUSY = 1;
	
	public static final int INQUIRY_RESPOND_NOTIFICAITON_RET_REJECT = -1;
	
	
	private int result;
	

	public InquiryRespondNotification(Token token, Terminal terminal,
			long transactionId, int ret) {
		super(MessageVersion.V01, MessageType.REQUEST, new Date(), token,
				terminal, UUID.randomUUID().toString(), transactionId,
				InquiryMessage.OPT_CLIENT_RESPOND_NOTIFICATION);
		this.result = ret;
	}


	public int getResult() {
		return result;
	}


	public void setResult(int result) {
		this.result = result;
	}



}
