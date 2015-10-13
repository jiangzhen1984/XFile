package elacier.process;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import elacier.cache.GlobalCacheHolder;
import elacier.order.Order;
import elacier.order.OrderItem;
import elacier.provider.ProviderUtil;
import elacier.provider.msg.InquiryNotification;
import elacier.provider.msg.InquiryRespondNotification;
import elacier.provider.msg.OrderNotificaiton;
import elacier.provider.msg.OrderRespondNotification;
import elacier.provider.msg.ServerTerminal;
import elacier.provider.msg.Terminal;
import elacier.restaurant.Menu;
import elacier.restaurant.Restaurant;
import elacier.service.ServiceFactory;
import elacier.transaction.LinearTransaction;
import elacier.transaction.LongToken;
import elacier.transaction.State;
import elacier.transaction.Token;
import elacier.transaction.Transaction;

public class GuestTransaction extends LinearTransaction {
	
	private GuestInformation information;
	
	private List<RestarurantStateHolder> restaurants;
	
	private Restaurant orderRestaurant;
	
	private Order guestOrder;
	
	private List<Menu> selectionMenus;
	
	private int orderConfirmStatus;
	
	
	public GuestTransaction(Token token) {
		super(token, new Date());
		restaurants = new ArrayList<RestarurantStateHolder>();
		selectionMenus = new ArrayList<Menu>();
	}

	@Override
	public boolean begin() {
		this.addState(new InquiryState());
		this.addState(new NotifyGuestRestaurantRespondState());
		this.addState(new GuestRequestOrderState());
		this.addState(new RestaurantConfirmOrderState());
		return super.begin();
	}
	
	@Override
	public boolean finish() {
		return true;
	}
	
	
	
	
	

	
	public GuestInformation getInformation() {
		return information;
	}

	public void setInformation(GuestInformation information) {
		this.information = information;
	}



	
	


	
	public List<Restaurant> getAllRespondedRestaurants() {
		if (restaurants == null || restaurants.size() <= 0) {
			return null;
		}
		List<Restaurant> responed = new ArrayList<Restaurant>();
		
		for (RestarurantStateHolder holder : restaurants) {
			if (holder.queryState == InquiryRespondNotification.INQUIRY_RESPOND_NOTIFICAITON_RET_OK) {
				responed.add(holder.rest);
			}
		}
		return responed;
	}



	public List<Restaurant> getAllConfirmedOrderRestaurants() {
		if (restaurants == null || restaurants.size() <= 0) {
			return null;
		}
		List<Restaurant> confirmed = new ArrayList<Restaurant>();
		
		for (RestarurantStateHolder holder : restaurants) {
			if (holder.orderState == OrderRespondNotification.RESTAURANT_RESPONSE_RET_OK) {
				confirmed.add(holder.rest);
			}
		}
		return confirmed;
	}

	/**
	 * Update restaurant respond inquiry notification. If restaurant respond ok, then turn to next state.<br />
	 * Notice: only turn to next state once.
	 * @param restaurantId
	 * @param status
	 */
	public void updateRestaurantResponse(long restaurantId, int status) {
		
		if (status == InquiryRespondNotification.INQUIRY_RESPOND_NOTIFICAITON_RET_OK) {
			//TODO only turn once
			boolean ret = this.turnToNextState();
			//FIXME if turn to next state failed notification
		}
	}
	
	
	/**
	 * Update guest order confirmation and turn to next state
	 * @param orderId
	 * @param orderRestaurant
	 * @param menus
	 */
	public void updateOrderInformation(Restaurant orderRestaurant, List<Menu> menus) {
		if (orderRestaurant == null) {
			throw new RuntimeException(" restaurant is null");
		}
		
		//FIXME need to check menu?
		if (menus != null && menus.size() > 0) {
			selectionMenus.addAll(menus);
		}
		
		//TODO check current state
		
		this.orderRestaurant = orderRestaurant;
		boolean ret = this.turnToNextState();
		//FIXME if turn to next state failed notification
	}
	
	/**
	 * Update restaurant respond order and turn to next state.
	 * @param restaurantId
	 * @param status  OrderResponse#RESPONSE_RET_OK  or  OrderResponse#RESPONSE_RET_BUSY  
	 */
	public void updateRestaurantConfirmationOrder(long restaurantId, int status) {
		//FIXME check restaurantId is same origin?
		//Because user maybe confirm order few times
		orderConfirmStatus = status;
		boolean ret = this.turnToNextState();
		//FIXME if turn to next state failed notification
	}
	

	public void addAvailableRestaurant(Restaurant rest) {
		restaurants.add(new RestarurantStateHolder(rest));
	}
	
	public void addAvailableRestaurant(List<Restaurant> rests) {
		for (Restaurant rest : rests) {
			restaurants.add(new RestarurantStateHolder(rest));
		}
	}


	public class InquiryState extends State {

		@Override
		public boolean actvie(Transaction trans) {
			if (information == null) {
				//FIXME logging information is staticist
				return false;
			}
			List<RestarurantStateHolder> listRes = restaurants;
			if (listRes == null || listRes.size() <= 0) {
				//FIXME logging no available restaurant
				return false;
			}
				
			Token messageToken = new LongToken(System.currentTimeMillis());
			Token deviceId = new LongToken(1);
			Terminal serverTerminal = new ServerTerminal(deviceId);
			long transId = 0;
			if (trans.getToken() instanceof LongToken) {
				transId = trans.getToken().toLongValue();
			} else {
				//FIXME maybe transaction is same should avoid this issue
				transId = trans.getToken().hashCode();
			}
			InquiryNotification notifcation = new InquiryNotification(messageToken, serverTerminal, transId, information.getType());
			notifcation.setGuestPhone(information.getGuestPhone());
			notifcation.setWord(information.getFav());
			notifcation.setNumOfPeople(information.getNums());
			
			List<Terminal> terminals = new ArrayList<Terminal>();
			for (RestarurantStateHolder res : listRes) {
				Terminal term = GlobalCacheHolder.getInstance().getRestaurantTerminal(res.rest.getRestId());
				if (term  != null) {
					terminals.add(term);
				} else {
					//FIXME logging no terminal
				}
						
			}
			if (terminals.size() <= 0) {
				//Means restaurant never connect to server
				return false;
			}
			try {
				ProviderUtil.pushNotification(notifcation, terminals);
			} catch (IOException e) {
				e.printStackTrace();
				//FIXME logging
				return false;
			}
			return true;
		}

		@Override
		public boolean deactive(Transaction trans) {
			return true;
		}
		
	}
	
	
	
	public class NotifyGuestRestaurantRespondState extends State {

		@Override
		public boolean actvie(Transaction trans) {
			//FIXME assumpe token is long token
			LongToken tok = (LongToken) trans.getToken();
			//ElacierService.getInstance().sendTwilioSMS("", "http://localhost:8080/transaction/"+ tok.longValue());
			return true;
			
		}

		@Override
		public boolean deactive(Transaction trans) {
			return true;
		}
		
	}
	
	
	public class GuestRequestOrderState extends State {

		@Override
		public boolean actvie(Transaction trans) {
			Token messageToken = new LongToken(System.currentTimeMillis());
			Token deviceId = new LongToken(1);
			Terminal serverTerminal = new ServerTerminal(deviceId);
			long transId;
			if (trans.getToken() instanceof LongToken) {
				transId = trans.getToken().toLongValue();
			} else {
				//FIXME maybe transaction is same should avoid this issue
				transId = trans.getToken().hashCode();
			}
			
			
			//generate order information and save to database
			Order order = new Order();
			java.sql.Date cd = new java.sql.Date(System.currentTimeMillis());
			order.setLastUpdateDate(cd);
			order.setTransaction(transId);
			order.setPayType(Order.ORDER_PAY_TYPE_ON_SITE);
			order.setState(Order.ORDER_STATE_REQUESTING);
			order.setGuestName(information.getGuestName());
			order.setGuestPhone(information.getGuestPhone());
			order.setGuestNum(information.getNums());
			for (Menu m : selectionMenus) {
				OrderItem oi = new OrderItem();
				oi.setName(m.getName());
				oi.setPrice(m.getPrice());
				oi.setOrder(order);
				order.addItem(oi);
			}
			
			ServiceFactory.getOrderService().addOrder(order);
			guestOrder = order;
			
			OrderNotificaiton notification = new OrderNotificaiton(messageToken, serverTerminal, transId, guestOrder.getId());
			//Fill selection menu
			for (Menu m : selectionMenus) {
				notification.addSelectionMenu(m.getMenuId(), m.getName());
			}
			Terminal term = GlobalCacheHolder.getInstance().getRestaurantTerminal(orderRestaurant.getRestId());
			
			try {
				ProviderUtil.pushNotification(notification, term);
			} catch (IOException e) {
				e.printStackTrace();
				//FIXME logging
				return false;
			}
			
			
			return false;
			
		}

		@Override
		public boolean deactive(Transaction trans) {
			return true;
		}
		
	}
	
	public class RestaurantConfirmOrderState extends State {

		@Override
		public boolean actvie(Transaction trans) {
			//FIXME assumpe token is long token
			LongToken tok = (LongToken) trans.getToken();
			//ElacierService.getInstance().sendTwilioSMS("", "http://localhost:8080/transaction/"+ tok.longValue());
			// TODO send confirmation sms to guest
			System.out.println(this +" active");
			return false;
			
		}

		@Override
		public boolean deactive(Transaction trans) {
			return true;
		}
		
	}
	
	
	
	class RestarurantStateHolder {
		Restaurant rest;
		int queryState;
		int orderState;
		public RestarurantStateHolder(Restaurant rest, int queryState,
				int orderState) {
			super();
			this.rest = rest;
			this.queryState = queryState;
			this.orderState = orderState;
		}
		public RestarurantStateHolder(Restaurant rest) {
			super();
			this.rest = rest;
		}
		
		
	}
	
	
	
	
}
