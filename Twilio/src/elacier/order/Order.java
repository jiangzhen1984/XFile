package elacier.order;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "E_ORDER")
public class Order {
	
	public static final int ORDER_STATE_REQUESTING = 1;
	public static final int ORDER_STATE_PAIED = 2;
	public static final int ORDER_STATE_NOT_PAID = 3;
	public static final int ORDER_STATE_RESTAURANT_CONFIRMED = 4;
	public static final int ORDER_STATE_RESTAURANT_REJECTED = 5;
	
	
	public static final int ORDER_PAY_TYPE_ONLINE= 1;
	public static final int ORDER_PAY_TYPE_ON_SITE= 2;
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "TRANSACTION_ID", columnDefinition = "NUMERIC(64)")
	private long transaction;

	@Column(name = "ORDER_STATE", columnDefinition = "INT(2)")
	private int state;
	
	
	@Column(name = "PAYMENT_TYPE", columnDefinition = "INT(1)")
	private int payType;

	@Column(name = "PRICE", columnDefinition = "NUMERIC(6,2)")
	private float price;
	
	@Column(name = "RETRIEVE_PLACE", columnDefinition = "VARCHAR(1000)")
	private String retrievePlace;
	
	@Column(name = "PAID_DATE", columnDefinition = "DATETIME")
	private Date paidDate;
	
	
	@Column(name = "FINISH_DATE", columnDefinition = "DATETIME")
	private Date finishDate;
	
	@Column(name = "CANCEL_DATE", columnDefinition = "DATETIME")
	private Date cancelDate;
	
	@Column(name = "DELIVERY_DATE", columnDefinition = "DATETIME")
	private Date deliverDate;
	
	@Column(name = "LAST_UPDATE_DATE", columnDefinition = "DATETIME")
	private Date lastUpdateDate;
	
	@Column(name = "GUEST_NAME", columnDefinition = "VARCHAR(200)")
	private String guestName;
	
	@Column(name = "GUEST_PHNE", columnDefinition = "VARCHAR(50)")
	private String guestPhone;
	
	@Column(name = "FAV", columnDefinition = "VARCHAR(200)")
	private String fav;
	
	@Column(name = "GUEST_NUM", columnDefinition = "int(2)")
	private int guestNum;
	
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "order")
	private Set<OrderItem> items;
	
	
	public Order() {
		
	}
	
	public Order(Order order) {
		this.id = order.getId();
		this.price = order.price;
		this.transaction = order.transaction;
		this.retrievePlace = order.retrievePlace;
		this.paidDate = order.paidDate;
		this.payType = order.payType;
		Set<OrderItem>  items = order.items;
		for(OrderItem item : items) {
			this.addItem(new OrderItem(item));
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

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

	public Set<OrderItem> getItems() {
		return items;
	}

	public void setItems(Set<OrderItem> items) {
		this.items = items;
	}
	
	

	public void addItem(OrderItem item) {
		if (items == null) {
			items = new HashSet<OrderItem>();
		}
		items.add(item);
	}

	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	public String getGuestPhone() {
		return guestPhone;
	}

	public void setGuestPhone(String guestPhone) {
		this.guestPhone = guestPhone;
	}

	public int getGuestNum() {
		return guestNum;
	}

	public void setGuestNum(int guestNum) {
		this.guestNum = guestNum;
	}

	
	

}
