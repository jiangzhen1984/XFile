package com.eshopping.model.vo;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.eshopping.model.po.ESOrder;
import com.eshopping.model.po.ESOrderItem;
import com.eshopping.model.po.ESUser;

public class Order {
	
	private long id;

	private long transaction;

	private OrderState state;
	
	
	private PaymentType payType;

	private float price;
	
	private String retrievePlace;
	
	private Date paidDate;
	
	
	private Date finishDate;
	
	private Date cancelDate;
	
	private Date lastUpdateDate;
	
	private Date deliverDate;

	
	private ESUser user;

	private List<OrderItem> items;

	public Order() {
	}
	
	
	public Order(ESOrder order) {
		this.id = order.getId();
		this.price = order.getPrice();
		this.transaction = order.getTransaction();
		this.retrievePlace = order.getRetrievePlace();
		this.paidDate = order.getPaidDate();
		this.state = OrderState.fromInt(order.getState());
		this.payType = PaymentType.fromInt(order.getPayType());
		this.lastUpdateDate = order.getLastUpdateDate();
		this.paidDate = order.getPaidDate();
		this.cancelDate = order.getCancelDate();
		this.finishDate = order.getFinishDate();
		this.deliverDate = order.getDeliverDate();
		Set<ESOrderItem>  items = order.getItems();
		if (items != null) {
			for(ESOrderItem item : items) {
				addItem(new OrderItem(item));
			}
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public long getTransaction() {
		return transaction;
	}

	public void setTransaction(long transaction) {
		this.transaction = transaction;
	}



	public float getPrice() {
		return price;
	}

	
	
	public Date getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public String getRetrievePlace() {
		return retrievePlace;
	}

	public void setRetrievePlace(String retrievePlace) {
		this.retrievePlace = retrievePlace;
	}

	public Date getPaidDate() {
		return paidDate;
	}

	public void setPaidDate(Date paidDate) {
		this.paidDate = paidDate;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
	
	
	

	public Date getDeliverDate() {
		return deliverDate;
	}


	public void setDeliverDate(Date deliverDate) {
		this.deliverDate = deliverDate;
	}


	public OrderState getState() {
		return state;
	}


	public void setState(OrderState state) {
		this.state = state;
	}


	public PaymentType getPayType() {
		return payType;
	}


	public void setPayType(PaymentType payType) {
		this.payType = payType;
	}


	public void setUser(ESUser user) {
		this.user = user;
	}

	public List<OrderItem> getItems() {
		return items;
	}

	
	public ESUser getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void addItem(OrderItem item) {
		if (items == null) {
			items = new ArrayList<OrderItem>();
		}
		items.add(item);
	}
	
	

	
	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}


	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}





	public enum OrderState {
		CANCELED, NOT_PAIED_YET, PAIED, DELIVERING, APLLY_REFUND, REJECT_REFUND, AGREE_REFUND, REFUND_COMPLETED, COMPLETED;
		
		
		public static OrderState fromInt(int st) {
			switch (st) {
			case 0:
				return CANCELED;
			case 1:
				return NOT_PAIED_YET;
			case 2:
				return PAIED;
			case 3:
				return DELIVERING;
			case 4:
				return APLLY_REFUND;
			case 5:
				return REJECT_REFUND;
			case 6:
				return REJECT_REFUND;
			case 7:
				return AGREE_REFUND;
			case 8:
				return REFUND_COMPLETED;
			case 9:
				return COMPLETED;
			default:
				return null;
			}
		}
	}
	
	public enum PaymentType {
		PAY_ON_DELIVERY,
		ONLINE_PAY;
		
		public static PaymentType fromInt(int st) {
			switch (st) {
			case 0:
				return PAY_ON_DELIVERY;
			case 1:
				return ONLINE_PAY;
			default:
				return null;
			}
		}
	}


}
