package elacier.provider.msg;

import java.util.Date;

import elacier.transaction.Token;

public abstract class OrderMessage extends BaseMessage {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5058917362515464668L;

	public static final int ORDER_OPT_GUEST_CONFIRM_NOTIFCAITON = 4;
	
	public static final int ORDER_OPT_RESTAURANT_RESPOND_GUEST_NOTIFCAITON = 5;
	
	public static final int ORDER_OPT_SERVER_RESPOND_RESTAURANT = 6;
	
	/**
	 * Inquire message state
	 */
	protected int opt;
	

	/**
	 * Mark for transaction unique id
	 */
	protected long transactionId;
	
	
	/**
	 * Mark for order id
	 */
	protected long orderId;
	
	
	protected OrderState state;


	public OrderMessage(MessageVersion version, MessageType type,
			Date timestamp, Token token, Terminal terminal, String uuid,
			int opt, long transactionId, long orderId) {
		super(version, type, timestamp, token, terminal, uuid);
		this.opt = opt;
		this.transactionId = transactionId;
		this.orderId = orderId;
	}


	public int getOpt() {
		return opt;
	}


	public void setOpt(int opt) {
		this.opt = opt;
	}


	public long getTransactionId() {
		return transactionId;
	}


	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}


	public long getOrderId() {
		return orderId;
	}


	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	


	
	public OrderState getState() {
		return state;
	}


	public void setState(OrderState state) {
		this.state = state;
	}




	public enum OrderState {
		UNKNOW, PAY_ON_SITE, ONLINE_PAY_NOT_YET, ONLINE_PAID;
		
		public static OrderState fromInt(int st) {
			switch (st) {
			case 1:
				return PAY_ON_SITE;
			case 2:
				return ONLINE_PAY_NOT_YET;
			case 3:
				return ONLINE_PAID;
			default:
				return UNKNOW;
			}
		}
	}
	
}
