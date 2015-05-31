package com.todaybreakfast.model;

import java.sql.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "BF_ORDER")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "ORDER_DATE", columnDefinition = "DATETIME")
	private Date orderDate;

	@Column(name = "TRANSACTION_ID", columnDefinition = "NUMERIC(20)")
	private long transaction;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "ORDER_STATE", columnDefinition = "NUMERIC(2)")
	private OrderState state;

	@Column(name = "PRICE", columnDefinition = "NUMERIC(6,2)")
	private float price;

	@OneToMany
	@JoinColumn(name = "ORDER_ID", referencedColumnName = "ID", unique = false, foreignKey = @ForeignKey(foreignKeyDefinition = "ORDER_ID"))
	private Set<OrderItem> items;

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

	public void setPrice(float price) {
		this.price = price;
	}

	public Set<OrderItem> getItems() {
		return items;
	}

	public void setItems(Set<OrderItem> items) {
		this.items = items;
	}

	public enum OrderState {
		CANCELED, NOT_PAIED, PAIED, COMPLETED
	}

}
