package elacier.provider;

import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import elacier.provider.msg.AndroidTerminal;
import elacier.provider.msg.BaseMessage;
import elacier.provider.msg.IOSTerminal;
import elacier.provider.msg.InquiryMessage;
import elacier.provider.msg.InquiryNotification;
import elacier.provider.msg.InquiryRespondNotification;
import elacier.provider.msg.InquiryResponse;
import elacier.provider.msg.MessageType;
import elacier.provider.msg.OrderMessage;
import elacier.provider.msg.OrderMessage.OrderState;
import elacier.provider.msg.OrderNotificaiton;
import elacier.provider.msg.OrderRespondNotification;
import elacier.provider.msg.OrderResponse;
import elacier.provider.msg.ServerTerminal;
import elacier.provider.msg.StringToken;
import elacier.provider.msg.Terminal;
import elacier.provider.msg.TerminalType;

public class JsonTransformImpl implements JsonMessageTransformer {

	/**
	 * transform to json
	 * 
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
			packInquiryRespondNotificationBody(
					(InquiryRespondNotification) message, content);
		} else if (message instanceof InquiryResponse) {
			packInquiryResponseBody((InquiryResponse) message, content);
		} else if (message instanceof OrderNotificaiton) {
			packOrderNotificaitonBody((OrderNotificaiton) message, content);
		} else if (message instanceof OrderRespondNotification) {
			packOrderRespondNotificationBody(
					(OrderRespondNotification) message, content);
		} else if (message instanceof OrderResponse) {
			packOrderResponseBody((OrderResponse) message, content);
		}
		return content;
	}

	@SuppressWarnings("unchecked")
	private JSONObject packHeader(BaseMessage message, JSONObject wrapper) {
		JSONObject header = new JSONObject();
		header.put("version", message.getVersion().ordinal());
		header.put("type", message.getType().ordinal());
		header.put("timestamp", message.getTimestamp().getTime());
		header.put("token", String.valueOf(message.getToken()));
		header.put("device", message.getTerminal().getType().ordinal());
		header.put("device-id", String.valueOf(message.getTerminal().getDeviceId()));
		wrapper.put("header", header);
		return wrapper;
	}

	@SuppressWarnings("unchecked")
	private JSONObject packInquiryNotificationBody(BaseMessage message,
			JSONObject wrapper) {
		JSONObject body = new JSONObject();
		InquiryNotification in = (InquiryNotification) message;
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
	private JSONObject packInquiryRespondNotificationBody(
			InquiryRespondNotification message, JSONObject wrapper) {
		JSONObject body = new JSONObject();
		body.put("opt", message.getOpt());
		body.put("trans-id", message.getTransactionId());
		body.put("confirm-ret", message.getResult());
		wrapper.put("body", body);
		return wrapper;
	}

	@SuppressWarnings("unchecked")
	private JSONObject packInquiryResponseBody(InquiryResponse message,
			JSONObject wrapper) {
		JSONObject body = new JSONObject();
		body.put("opt", message.getOpt());
		body.put("trans-id", message.getTransactionId());
		body.put("ret", message.getResult());
		wrapper.put("body", body);
		return wrapper;
	}

	@SuppressWarnings("unchecked")
	private JSONObject packOrderNotificaitonBody(OrderNotificaiton message,
			JSONObject wrapper) {
		JSONObject body = new JSONObject();
		body.put("opt", message.getOpt());
		body.put("trans-id", message.getTransactionId());
		body.put("order-id", message.getOrderId());
		body.put("order-state", message.getState().ordinal());
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
	private JSONObject packOrderRespondNotificationBody(
			OrderRespondNotification message, JSONObject wrapper) {
		JSONObject body = new JSONObject();
		body.put("opt", message.getOpt());
		body.put("trans-id", message.getTransactionId());
		body.put("order-id", message.getOrderId());
		body.put("ret", message.getRet());
		wrapper.put("body", body);
		return wrapper;
	}

	@SuppressWarnings("unchecked")
	private JSONObject packOrderResponseBody(OrderResponse message,
			JSONObject wrapper) {
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
	 * 
	 * @param json
	 * @return
	 */
	public BaseMessage transform(String data) {
		if (data == null) {
			return null;
		}
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(data);
			if (!(obj instanceof JSONObject)) {
				return null;
			}
			
			JSONObject json =(JSONObject) obj;
			Object objHeader = json.get("header");
			Object objBody = (JSONObject)json.get("body");
			if (objHeader == null || !( objHeader instanceof JSONObject) ||  objBody == null || !( objBody instanceof JSONObject)) {
				return null;
			}
			JSONObject header = (JSONObject)objHeader;
			JSONObject body = (JSONObject)objBody;
			int type =((Long) header.get("type")).intValue();
			MessageType mt = MessageType.fromInt(type);
			if (mt == MessageType.UNKNOWN) {
				throw new ParseException(type , " type unknown " + type);
			}
			return parseBody(header, body,mt);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	private BaseMessage parseBody(JSONObject header, JSONObject body, MessageType mt) {
		int opt = ((Long)body.get("opt")).intValue();
		BaseMessage bm = null;
		switch (mt){
		case NOTIFICATION:
			if (opt == InquiryMessage.OPT_SERVER_SEND_NOTIFICATION) {
				return parseInquiryNotification(header, body);
			} else if (opt == OrderMessage.ORDER_OPT_GUEST_CONFIRM_NOTIFCAITON) {
				return parseOrderNotificaiton(header, body);
			}
			break;
		case REQUEST:
			if (opt == InquiryMessage.OPT_CLIENT_RESPOND_NOTIFICATION) {
				return parseInquiryRespondNotification(header, body);
			} else if (opt == OrderMessage.ORDER_OPT_RESTAURANT_RESPOND_GUEST_NOTIFCAITON) {
				return parseOrderRespondNotification(header, body);
			}
			break;
		case RESPONSE:
			if (opt == InquiryMessage.OPT_SERVER_RESPOND_CLIENT) {
				return parseInquiryResponse(header, body);
			} else if (opt == OrderMessage.ORDER_OPT_SERVER_RESPOND_RESTAURANT) {
				return parseOrderResponse(header, body);
			}
			break;
		case UNKNOWN:
			break;
		default:
			break;
		}
		return bm;
	}
	
	
	
	
	private InquiryNotification parseInquiryNotification(JSONObject header, JSONObject body) {
		long timestatmp = (Long) header.get("timestamp");
		String token = (String) header.get("token");
		int device = ((Long) header.get("device")).intValue();
		String deviceId = (String) header.get("device-id");
		
		
		long transactionId = (Long) body.get("trans-id");
		int inquiryType  = ((Long)  body.get("inquire-type")).intValue();
		String phone = (String) body.get("guest-phone");
		String gname = (String) body.get("guest-name");
		int num = ((Long)  body.get("num-of-guest")).intValue();
		String fav = (String) body.get("fav");
		
		//TODO add location parser
//		JSONObject location = new JSONObject();
//		location.put("lat", in.getLocationLat());
//		location.put("lng", in.getLocationLng());
//		body.put("guest-loc", location);
		
		Terminal terminal = getTerminal(device, deviceId);
		InquiryNotification inquiryNotification = new InquiryNotification(new StringToken(token),
				terminal, transactionId, inquiryType);
		inquiryNotification.setTimestamp(new Date(timestatmp));
		inquiryNotification.setGuestPhone(phone);
		inquiryNotification.setGuestName(gname);
		inquiryNotification.setNumOfPeople(num);
		inquiryNotification.setWord(fav);
		
		return inquiryNotification;
	}
	
	private InquiryRespondNotification parseInquiryRespondNotification(JSONObject header, JSONObject body) {
		long timestatmp = (Long) header.get("timestamp");
		String token = (String) header.get("token");
		int device = ((Long) header.get("device")).intValue();
		String deviceId = (String) header.get("device-id");
		
		
		long transactionId = (Long) body.get("trans-id");
		int ret  = ((Long)  body.get("confirm-ret")).intValue();
		

		Terminal terminal = getTerminal(device, deviceId);
		InquiryRespondNotification inquiryRespondNotification = new InquiryRespondNotification(new StringToken(token),
				terminal, transactionId, ret);
		inquiryRespondNotification.setTimestamp(new Date(timestatmp));
		
		return inquiryRespondNotification;
	}

	
	private InquiryResponse parseInquiryResponse(JSONObject header, JSONObject body) {
		long timestatmp = (Long) header.get("timestamp");
		String token = (String) header.get("token");
		int device = ((Long) header.get("device")).intValue();
		String deviceId = (String) header.get("device-id");
		
		
		long transactionId = (Long) body.get("trans-id");
		int ret  = ((Long)  body.get("ret")).intValue();
		

		Terminal terminal = getTerminal(device, deviceId);
		InquiryResponse inquiryResponse = new InquiryResponse(new StringToken(token),
				terminal, transactionId, ret);
		inquiryResponse.setTimestamp(new Date(timestatmp));
		
		return inquiryResponse;
	}

	
	private OrderNotificaiton parseOrderNotificaiton(JSONObject header, JSONObject body) {
		long timestatmp = (Long) header.get("timestamp");
		String token = (String) header.get("token");
		int device = ((Long) header.get("device")).intValue();
		String deviceId = (String) header.get("device-id");
		
		
		long transactionId = (Long) body.get("trans-id");
		long orderId = (Long)body.get("order-id");
		int orderState = ((Long)body.get("order-state")).intValue();
		//TODO add menu parser

		Terminal terminal = getTerminal(device, deviceId);
		OrderNotificaiton orderNotificaiton = new OrderNotificaiton(new StringToken(token),
				terminal, transactionId, orderId);
		orderNotificaiton.setTimestamp(new Date(timestatmp));
		orderNotificaiton.setState(OrderState.fromInt(orderState));
		
		return orderNotificaiton;
	}

	
	private OrderRespondNotification parseOrderRespondNotification(JSONObject header, JSONObject body) {
		long timestatmp = (Long) header.get("timestamp");
		String token = (String) header.get("token");
		int device = ((Long) header.get("device")).intValue();
		String deviceId = (String) header.get("device-id");
		
		
		long transactionId = (Long) body.get("trans-id");
		long orderId = (Long)body.get("order-id");
		int ret = ((Long)  body.get("ret")).intValue();

		Terminal terminal = getTerminal(device, deviceId);
		OrderRespondNotification orderRespondNotification = new OrderRespondNotification(new StringToken(token),
				terminal, transactionId, orderId, ret);
		orderRespondNotification.setTimestamp(new Date(timestatmp));
		
		return orderRespondNotification;
	}

	
	private OrderResponse parseOrderResponse(JSONObject header, JSONObject body) {
		long timestatmp = (Long) header.get("timestamp");
		String token = (String) header.get("token");
		int device = ((Long) header.get("device")).intValue();
		String deviceId = (String) header.get("device-id");
		
		
		long transactionId = (Long) body.get("trans-id");
		long orderId = (Long)body.get("order-id");
		int ret = ((Long)  body.get("ret")).intValue();

		Terminal terminal = getTerminal(device, deviceId);
		OrderResponse orderResponse = new OrderResponse(new StringToken(token),
				terminal, transactionId, orderId, ret);
		orderResponse.setTimestamp(new Date(timestatmp));
		
		return orderResponse;
	}


	
	private Terminal getTerminal(int deviceT, String deviceId) {
		Terminal terminal = null;
		TerminalType deviceType = TerminalType.fromInt(deviceT);
		if (deviceType == TerminalType.SERVER) {
			terminal = new ServerTerminal(new StringToken(deviceId));
		} else if (deviceType == TerminalType.ANDROID) {
			terminal = new AndroidTerminal(new StringToken(deviceId));
		} else if (deviceType == TerminalType.IOS) {
			terminal = new IOSTerminal(new StringToken(deviceId));
		}
		
		return terminal;
	}
}
