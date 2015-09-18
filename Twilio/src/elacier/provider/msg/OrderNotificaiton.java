package elacier.provider.msg;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Guest make order notificaiton.
 * @author 28851274
 *
 */
public class OrderNotificaiton extends OrderMessage {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2856898265258768584L;
	List<MenuWrapper> menus;
	
	public OrderNotificaiton(Token token, Terminal terminal,
			long transactionId, long orderId) {
		super(MessageVersion.V01, MessageType.NOTIFICATION, new Date(), token,
				terminal, UUID.randomUUID().toString(),
				OrderMessage.ORDER_OPT_GUEST_CONFIRM_NOTIFCAITON, transactionId,
				orderId);
		menus = new ArrayList<MenuWrapper>();
	}
	
	
	public void addSelectionMenu(long id, String name) {
		menus.add(new MenuWrapper(id, name));
	}
	
	
	public void removeMenu(long id) {
		for (int i = 0; i < menus.size(); i++) {
			MenuWrapper mw = menus.get(i);
			if(mw.id == id) {
				menus.remove(i);
				return;
			}
		}
	}
	
	
	class MenuWrapper {
		long id;
		String name;

		public MenuWrapper(long id, String name) {
			super();
			this.id = id;
			this.name = name;
		}
		
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject transform(JSONObject json) {
		if (json == null) {
			return null;
		}
		super.transform(json);
		JSONObject body = new JSONObject();
		body.put("opt", this.opt);
		body.put("trans-id", this.transactionId);
		body.put("order-id", this.orderId);
		body.put("order-state", this.state);
		JSONArray menusJson = new JSONArray();
		
		for (MenuWrapper m : menus) {
			JSONObject mu = new JSONObject();
			mu.put("menu-id", m.id);
			mu.put("mame", m.name);
			menusJson.add(mu);
		}
		
		body.put("menu", menusJson);
		json.put("body", body);
		return json;
	}

}
