package com.eshopping.model.po;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.eshopping.model.vo.User;

@Entity
@Table(name = "ES_ORDER")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "ORDER_DATE", columnDefinition = "DATETIME")
	private Date orderDate;

	@Column(name = "TRANSACTION_ID", columnDefinition = "NUMERIC(30)")
	private long transaction;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "ORDER_STATE", columnDefinition = "NUMERIC(2)")
	private OrderState state;

	@Column(name = "PRICE", columnDefinition = "NUMERIC(6,2)")
	private float price;
	
	@Column(name = "RETRIEVE_PLACE", columnDefinition = "VARCHAR(200)")
	private String retrievePlace;
	
	@Column(name = "PAID_DATE", columnDefinition = "DATETIME")
	private Date paidDate;
	
	
	@Column(name = "FINISH_DATE", columnDefinition = "DATETIME")
	private Date finishDate;
	
	@Column(name = "CANCEL_DATE", columnDefinition = "DATETIME")
	private Date cancelDate;
	
	@ManyToOne
	@JoinColumn(name = "USER_ID", referencedColumnName = "ID", unique = false)
	private ESUser user;

	@OneToMany
	@JoinColumn(name = "ORDER_ID",  unique = false)
	private Set<OrderItem> items;
	
	
	public Order() {
		
	}
	
	public Order(Order order) {
		this.id = order.getId();
		this.orderDate = order.orderDate;
		this.price = order.price;
		this.transaction = order.transaction;
		this.retrievePlace = order.retrievePlace;
		this.user = order.user;
		this.paidDate = order.paidDate;
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

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public long getTransaction() {
		return transaction;
	}

	public void setTransaction(long transaction) {
		this.transaction = transaction;
	}

	public OrderState getState() {
		return state;
	}

	public void setState(OrderState state) {
		this.state = state;
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

	public Set<OrderItem> getItems() {
		return items;
	}

	public void setItems(Set<OrderItem> items) {
		this.items = items;
	}
	
	
	public ESUser getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void addItem(OrderItem item) {
		if (items == null) {
			items = new HashSet<OrderItem>();
		}
		items.add(item);
	}

	public enum OrderState {
		CANCELED, NOT_PAIED, PAIED, COMPLETED
	}

}
