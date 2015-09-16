package com.eshopping.model.po;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
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
public class ESOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "TRANSACTION_ID", columnDefinition = "NUMERIC(30)")
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
	
	
	@ManyToOne
	@JoinColumn(name = "USER_ID", referencedColumnName = "ID", unique = false)
	private ESUser user;

	@OneToMany
	@JoinColumn(name = "ORDER_ID",  unique = false)
	private Set<ESOrderItem> items;
	
	
	public ESOrder() {
		
	}
	
	public ESOrder(ESOrder order) {
		this.id = order.getId();
		this.price = order.price;
		this.transaction = order.transaction;
		this.retrievePlace = order.retrievePlace;
		this.user = order.user;
		this.paidDate = order.paidDate;
		this.payType = order.payType;
		Set<ESOrderItem>  items = order.items;
		for(ESOrderItem item : items) {
			this.addItem(new ESOrderItem(item));
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

	public void setUser(ESUser user) {
		this.user = user;
	}

	public Set<ESOrderItem> getItems() {
		return items;
	}

	public void setItems(Set<ESOrderItem> items) {
		this.items = items;
	}
	
	
	public ESUser getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void addItem(ESOrderItem item) {
		if (items == null) {
			items = new HashSet<ESOrderItem>();
		}
		items.add(item);
	}


}
