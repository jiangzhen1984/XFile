package com.eshopping.model.po;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ES_COMBO_ITEM")
public class ESComboItem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "PRICE", columnDefinition = "NUMERIC(6,2)")
	private float price;

	@Column(name = "PIC_PATH", columnDefinition = "VARCHAR(200)")
	private String picUrl;

	@Column(name = "NAME", columnDefinition = "VARCHAR(40)")
	private String name;

	@Column(name = "STUFF", columnDefinition = "VARCHAR(100)")
	private String stuff;

	@Column(name = "DESCRIPTION", columnDefinition = "VARCHAR(100)")
	private String description;

	@ManyToMany(targetEntity = ESItem.class, cascade = {
			CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "ES_COMBO_ITEM_M", joinColumns = { @JoinColumn(name = "ES_COMBO_ITEM_ID") }, inverseJoinColumns = @JoinColumn(name = "ES_ITEM_ID"))
	private Set<ESItem> list;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public float getPrice() {
		return price;
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

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getStuff() {
		return stuff;
	}

	public void setStuff(String stuff) {
		this.stuff = stuff;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void addItem(ESItem bf) {
		if (list == null) {
			list = new HashSet<ESItem>();
		}
		list.add(bf);
	}

	public void removeBreakfast(ESItem bf) {
		if (list == null) {
			return;
		}
		list.remove(bf);
	}

	public List<ESItem> getItems() {
		return new ArrayList<ESItem>(list);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ESComboItem other = (ESComboItem) obj;
		if (id != other.id)
			return false;
		return true;
	}

	
	
}
