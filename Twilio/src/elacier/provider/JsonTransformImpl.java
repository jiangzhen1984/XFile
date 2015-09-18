package elacier.provider;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import elacier.provider.msg.BaseMessage;
import elacier.provider.msg.InquiryNotification;
import elacier.provider.msg.InquiryRespondNotification;
import elacier.provider.msg.InquiryResponse;
import elacier.provider.msg.OrderNotificaiton;
import elacier.provider.msg.OrderRespondNotification;
import elacier.provider.msg.OrderResponse;

public class JsonTransformImpl implements JsonMessageTransformer {


	/**
	 * transform to json 
	 * @param message
	 * @return transformed json object
	 */
	public JSONObject transform(BaseMessage message) {
		if (message == null) {
			throw new NullPointerException(" message is null");
		}
		JSONObject content = new JSONObject();
		packHeader(message, content);
		if (message instanceof InquiryNotification) {
			packInquiryNotificationBody(message, content);
		} else if (message instanceof InquiryRespondNotification) {
			packInquiryRespondNotificationBody((InquiryRespondNotification)message, content);
		} else if (message instanceof InquiryResponse) {
			packInquiryResponseBody((InquiryResponse)message, content);
		}else if (message instanceof OrderNotificaiton) {
			packOrderNotificaitonBody((OrderNotificaiton)message, content);
		}else if (message instanceof OrderRespondNotification) {
			packOrderRespondNotificationBody((OrderRespondNotification)message, content);
		}else if (message instanceof OrderResponse) {
			packOrderResponseBody((OrderResponse)message, content);
		}
		return content;
	}
	
	
	@SuppressWarnings("unchecked")
	private JSONObject packHeader(BaseMessage message, JSONObject wrapper) {
		JSONObject header = new JSONObject();
		header.put("version", message.getVersion().ordinal());
		header.put("type",message.getType().ordinal());
		header.put("timestamp", message.getTimestamp().getTime());
		header.put("token", message.getToken());
		header.put("device", message.getTerminal().getType().ordinal());
		header.put("device-id", message.getTerminal().getDeviceId());
		wrapper.put("header", header);
		return wrapper;
	}
	
	@SuppressWarnings("unchecked")
	private  JSONObject packInquiryNotificationBody(BaseMessage message, JSONObject wrapper) {
		JSONObject body = new JSONObject();
		InquiryNotification in = (InquiryNotification)message;
		body.put("opt", in.getOpt());
		body.put("trans-id", in.getTransactionId());
		body.put("inquire-type", in.getInquiryType());
		body.put("guest-phone", in.getGuestPhone());
		body.put("guest-name", in.getGuestName());
		body.put("num-of-guest", in.getNumOfPeople());
		body.put("fav", in.getWord());
		JSONObject location = new JSONObject();
		location.put("lat", in.getLocationLat());
		location.put("lng", in.getLocationLng());
		body.put("guest-loc", location);
		
		wrapper.put("body", body);
		return wrapper;
	}
	
	
	
	@SuppressWarnings("unchecked")
	private JSONObject packInquiryRespondNotificationBody(InquiryRespondNotification message, JSONObject wrapper) {
		JSONObject body = new JSONObject();
		body.put("opt", message.getOpt());
		body.put("trans-id", message.getTransactionId());
		body.put("confirm-ret", message.getResult());
		wrapper.put("body", body);
		return wrapper;
	}
	
	

	@SuppressWarnings("unchecked")
	private JSONObject packInquiryResponseBody(InquiryResponse  message, JSONObject wrapper) {
		JSONObject body = new JSONObject();
		body.put("opt", message.getOpt());
		body.put("trans-id", message.getTransactionId());
		body.put("ret", message.getResult());
		wrapper.put("body", body);
		return wrapper;
	}


	
	

	@SuppressWarnings("unchecked")
	private JSONObject packOrderNotificaitonBody(OrderNotificaiton  message, JSONObject wrapper) {
		JSONObject body = new JSONObject();
		body.put("opt", message.getOpt());
		body.put("trans-id", message.getTransactionId());
		body.put("order-id", message.getOrderId());
		body.put("order-state", message.getState());
		JSONArray menusJson = new JSONArray();
		
		 List<Object[]> list = message.getMenus();
		for (Object[] menu : list) {
			JSONObject mu = new JSONObject();
			mu.put("menu-id", menu[0]);
			mu.put("mame", menu[1]);
			menusJson.add(mu);
		}
		
		body.put("menu", menusJson);
		wrapper.put("body", body);
		return wrapper;
	}
	
	
	
	@SuppressWarnings("unchecked")
	private JSONObject packOrderRespondNotificationBody(OrderRespondNotification  message, JSONObject wrapper) {
		JSONObject body = new JSONObject();
		body.put("opt", message.getOpt());
		body.put("trans-id", message.getTransactionId());
		body.put("order-id", message.getOrderId());
		body.put("ret", message.getRet());
		wrapper.put("body", body);
		return wrapper;
	}
	
	@SuppressWarnings("unchecked")
	private JSONObject packOrderResponseBody(OrderResponse  message, JSONObject wrapper) {
		JSONObject body = new JSONObject();
		body.put("opt", message.getOpt());
		body.put("trans-id", message.getTransactionId());
		body.put("order-id", message.getOrderId());
		body.put("ret", message.getRet());
		wrapper.put("body", body);
		return wrapper;
	}
	
	

	
	
	/**
	 * Transform json string to message object
	 * @param json
	 * @return
	 */
	public BaseMessage transform(String json) {
		return null;
	}

}
