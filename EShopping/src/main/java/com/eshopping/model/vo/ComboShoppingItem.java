package com.eshopping.model.vo;

import java.util.ArrayList;
import java.util.List;

import com.eshopping.model.po.ESComboItem;
import com.eshopping.model.po.ESItem;

public class ComboShoppingItem extends AbsShoppingItem {

	private List<SingleShoppingItem> items;
	
	public ComboShoppingItem() {
		super(TYPE_COMBO);
		items = new ArrayList<SingleShoppingItem>();
	}

	
	public ComboShoppingItem(ESComboItem combo) {
		super(TYPE_COMBO);
		if (combo == null) {
			throw new NullPointerException(" ESItem can not be null");
		}
		this.id = combo.getId();
		this.name = combo.getName();
		this.price = combo.getPrice();
		this.stuff = combo.getStuff();
		this.description = combo.getDescription();
		items = new ArrayList<SingleShoppingItem>();
		List<ESItem>  list = combo.getItems();
		for (ESItem esi : list) {
			items.add(new SingleShoppingItem(esi));
		}
	}
	
	
	public void addItem(SingleShoppingItem item) {
		if (item == null) {
			throw new NullPointerException(" SingleShoppingItem can not be null");
		}
		items.add(item);
	}
	
	public boolean removeItem(SingleShoppingItem item) {
		return items.remove(item);
	}
	
	
	public SingleShoppingItem removeItem(int index) {
		return items.remove(index);
	}
	
	public List<SingleShoppingItem> getItemList() {
		return items;
	}
}
