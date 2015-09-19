package elacier.process;

import elacier.provider.msg.BaseMessage;
import elacier.provider.msg.InquiryRespondNotification;
import elacier.provider.msg.InquiryResponse;
import elacier.provider.msg.MessageType;
import elacier.provider.msg.OrderRespondNotification;
import elacier.provider.msg.OrderResponse;
import elacier.provider.msg.ServerTerminal;
import elacier.transaction.LongToken;
import elacier.transaction.StringToken;
import elacier.transaction.TransactionManager;

public class MessageHandler {
	
	
	/**
	 * Handle client message and return new message of response for client.
	 * @param msg
	 * @return null for failed  otherwise return message
	 */
	public BaseMessage handleMessage(BaseMessage msg) {
		if (msg == null) {
			return null;
		}
		
		if (msg.getType() != MessageType.REQUEST) {
			return null;
		}
		
		if (msg instanceof InquiryRespondNotification) {
			return handleInquiryRespondNotification((InquiryRespondNotification) msg);
		} else if (msg instanceof OrderRespondNotification) {
			return handleOrderRespondNotification((OrderRespondNotification) msg);
		} else {
			return null;
		}
	}
	
	
	private BaseMessage handleInquiryRespondNotification(InquiryRespondNotification irn) {
		long transaction = irn.getTransactionId();
		TransactionManager manager = TransactionManager.getInstance();
		GuestTransaction tr = (GuestTransaction) manager.getTransaction(new LongToken(transaction));
		if (tr == null) {
			//FIXME parameter is incorrect
			return null;
		}
		
		//TODO get restaurant information
		long restaurantId = irn.getRestaurantId();
		
		//Update restaurant respond status
		tr.updateRestaurantResponse(restaurantId, irn.getResult());
		
		
		InquiryResponse response = new InquiryResponse(new LongToken(
				System.currentTimeMillis()), new ServerTerminal(
				new StringToken("server")), transaction,
				InquiryResponse.INQUIRY_RESPOND_CLIENT_RESPONSE_OK);
		return response;
	}
	
	
	private BaseMessage handleOrderRespondNotification(OrderRespondNotification orn) {
		long transaction = orn.getTransactionId();
		TransactionManager manager = TransactionManager.getInstance();
		GuestTransaction tr = (GuestTransaction) manager.getTransaction(new LongToken(transaction));
		//Update transaction status and turn to next
		tr.updateRestaurantConfirmationOrder(orn.getTransactionId(), orn.getRet());
		
		OrderResponse response = new OrderResponse(new LongToken(
				System.currentTimeMillis()), new ServerTerminal(
				new StringToken("server")), transaction, orn.getOrderId(), 
				OrderResponse.RESPONSE_RET_OK);
		//TODO update database for this request
		return response;
	}

}
