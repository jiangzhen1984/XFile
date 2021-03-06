package elacier.provider.msg;

import java.util.Date;
import java.util.UUID;

import elacier.transaction.Token;


/**
 * For restaurant respond guest order notification
 * @author 28851274
 *
 */
public class OrderRespondNotification extends OrderMessage {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4277054415403537459L;

	public static final int RESTAURANT_RESPONSE_RET_OK = 0;
	
	public static final int RESTAURANT_RESPONSE_RET_BUSY = 1;
	
	public static final int RESTAURANT_RESPONSE_RET_MENU_NOT_AVAI = 2;
	
	private int ret;
	
	private long restaurantId;
	
	public OrderRespondNotification(Token token, Terminal terminal,
			long transactionId, long orderId, int ret, long restaurantId) {
		super(MessageVersion.V01, MessageType.REQUEST, new Date(), token,
				terminal, UUID.randomUUID().toString(),
				OrderMessage.ORDER_OPT_RESTAURANT_RESPOND_GUEST_NOTIFCAITON, transactionId,
				orderId);
		this.ret = ret;
		this.restaurantId = restaurantId;
	}

	public int getRet() {
		return ret;
	}

	public void setRet(int ret) {
		this.ret = ret;
	}
	
	
	public long getRestaurantId() {
		return restaurantId;
	}


	public void setRestaurantId(long restaurantId) {
		this.restaurantId = restaurantId;
	}

}
