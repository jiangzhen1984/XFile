package elacier.provider.msg;

import java.util.Date;

import elacier.transaction.Token;

/**
 * Inquiry message
 * @author 28851274
 *
 */
public abstract class InquiryMessage extends BaseMessage {
	
	/**
	 * Mark for opt property
	 */
	public static final int OPT_SERVER_SEND_NOTIFICATION = 1;
	public static final int OPT_CLIENT_RESPOND_NOTIFICATION = 2;
	public static final int OPT_SERVER_RESPOND_CLIENT= 3;
	
	
	public static final int INQUIRY_OPT_TYPE_TAKE_AWAY = 1;
	
	public static final int INQUIRY_OPT_TYPE_ON_SITE = 2;
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4267978564318606669L;


	/**
	 * Mark for transaction unique id
	 */
	protected long transactionId;
	
	/**
	 * Inquire message state
	 */
	protected int opt;

	
	
	
	


	public InquiryMessage(MessageVersion version, MessageType type,
			Date timestamp, Token token, Terminal terminal, String uuid,
			long transactionId, int opt) {
		super(version, type, timestamp, token, terminal, uuid);
		this.transactionId = transactionId;
		this.opt = opt;
	}



	public long getTransactionId() {
		return transactionId;
	}



	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}



	public int getOpt() {
		return opt;
	}
	
	
}
