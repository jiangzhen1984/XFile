package elacier.provider.msg;

import java.util.Date;
import java.util.UUID;

import elacier.transaction.Token;

/**
 * For restaurant respond guest order notification
 * @author 28851274
 *
 */
public class OrderResponse extends OrderMessage {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 792594623355649698L;

	public static final int RESPONSE_RET_OK = 0;
	
	public static final int RESPONSE_RET_BUSY = 1;
	
	private int ret;
	
	public OrderResponse(Token token, Terminal terminal,
			long transactionId, long orderId, int ret) {
		super(MessageVersion.V01, MessageType.RESPONSE, new Date(), token,
				terminal, UUID.randomUUID().toString(),
				OrderMessage.ORDER_OPT_SERVER_RESPOND_RESTAURANT, transactionId,
				orderId);
		this.ret = ret;
	}

	public int getRet() {
		return ret;
	}

	public void setRet(int ret) {
		this.ret = ret;
	}
	
}
