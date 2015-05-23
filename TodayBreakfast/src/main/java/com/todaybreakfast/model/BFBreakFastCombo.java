package com.todaybreakfast.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "BF_BREAKFAST_COMBO")
public class BFBreakFastCombo {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(name = "IS_TODAY", columnDefinition =  "bool default TRUE", nullable=false)
	private boolean isToday;

	@Column(name = "PRICE", columnDefinition =  "NUMERIC(6,2)")
	private Float price;

	@Column(name = "NAME", columnDefinition =  "VARCHAR(40)")
	private String name;
	
	 @OneToMany
	    @JoinTable(
	            name="BF_COMBO_ITEM",
	            joinColumns = @JoinColumn( name="COMBO_ID"),
	            inverseJoinColumns = @JoinColumn( name="BF_ID")
	    )
	private Set<BFBreakfast> list;
	
	
	 
	 
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isToday() {
		return isToday;
	}

	public void setToday(boolean isToday) {
		this.isToday = isToday;
	}

	public float getPrice() {
		return  price == null? 0 : price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public void addBreakfast(BFBreakfast bf) {
		if (list == null) {
			list = new HashSet<BFBreakfast>();
		}
		list.add(bf);
	}
	
	public void removeBreakfast(BFBreakfast bf) {
		if (list == null) {
			return;
		}
		list.remove(bf);
	}
	
	public List<BFBreakfast> getItems() {
		 return new ArrayList<BFBreakfast>(list);
	}

}
