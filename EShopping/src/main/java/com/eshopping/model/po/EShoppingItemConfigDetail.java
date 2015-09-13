package com.eshopping.model.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ESHOPPING_ITEM_CONFIG_DETAIL")
public class EShoppingItemConfigDetail {
	
	@Id
	private long id;
	
	@Column(name="ITEM_TYPE", columnDefinition = "INT(1)")
	private int itemType;
	
	@Column(name="IS_SALE", columnDefinition = "BOOL")
	private boolean isSale;
	
	@Column(name="IS_NEW", columnDefinition = "BOOL")
	private boolean isNew;
	
	@Column(name="IS_RECOMMAND", columnDefinition = "BOOL")
	private boolean isRecommand;
	
	@Column(name="IS_HOT", columnDefinition = "BOOL")
	private boolean isHot;
	
	@Column(name="ITEM_RANK", columnDefinition = "NUMBERIC(3,1)")
	private float rank;
	
	@Column(name="ITEM_STOCK_COUNTS", columnDefinition = "INT(6)")
	private int counts;
	
	@Column(name="SALED_COUNTS", columnDefinition = "INT(6)")
	private int saledCounts;
	
	@Column(name="STOCK_DATE", columnDefinition = "DATETIME")
	private Date stockDate;
	
	
	

	public EShoppingItemConfigDetail() {
	}
	
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isSale() {
		return isSale;
	}

	public void setSale(boolean isSale) {
		this.isSale = isSale;
	}

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	public boolean isRecommand() {
		return isRecommand;
	}

	public void setRecommand(boolean isRecommand) {
		this.isRecommand = isRecommand;
	}

	public boolean isHot() {
		return isHot;
	}

	public void setHot(boolean isHot) {
		this.isHot = isHot;
	}

	public float getRank() {
		return rank;
	}

	public void setRank(float rank) {
		this.rank = rank;
	}

	public int getCounts() {
		return counts;
	}

	public void setCounts(int counts) {
		this.counts = counts;
	}

	public int getSaledCounts() {
		return saledCounts;
	}

	public void setSaledCounts(int saledCounts) {
		this.saledCounts = saledCounts;
	}

	public Date getStockDate() {
		return stockDate;
	}

	public void setStockDate(Date stockDate) {
		this.stockDate = stockDate;
	}

	public int getItemType() {
		return itemType;
	}

	public void setItemType(int itemType) {
		this.itemType = itemType;
	}
	

}
