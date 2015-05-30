package com.todaybreakfast.model.vo;

import java.util.ArrayList;
import java.util.List;

import com.todaybreakfast.model.BFBreakFastCombo;
import com.todaybreakfast.model.BFBreakfast;

public class BreakfastComboWrapper extends BreakfastWrapper {

	private List<BreakfastSingleWrapper> list;
	
	
	public BreakfastComboWrapper(long id) {
		this.id = id;
		this.type = BreakfastWrapper.Type.COMBO;
		list = new ArrayList<BreakfastSingleWrapper>();
	}
	
	
	public BreakfastComboWrapper(float price, String name,String url,  String stuff, String desc) {
		this.type = BreakfastWrapper.Type.COMBO;
		this.price = price;
		this.name = name;
		this.stuff = stuff;
		this.url = url;
		this.description = desc;
		list = new ArrayList<BreakfastSingleWrapper>();
	}
	
	public BreakfastComboWrapper(BFBreakFastCombo bfc) {
		this(bfc.getPrice(), bfc.getName(), bfc.getPicUrl(), bfc.getStuff(), bfc.getDescription());
		this.id = bfc.getId();
		List<BFBreakfast> l = bfc.getItems();
		for (BFBreakfast bf : l) {
			list.add(new BreakfastSingleWrapper(bf));
		}
	}
	
	
	public List<BreakfastSingleWrapper>  getItems() {
		return list;
	}
	
	public void removeItem(BreakfastSingleWrapper bw) {
		list.remove(bw);
	}
	
	public void addItem(BreakfastSingleWrapper bw) {
		list.add(bw);
	}
	
	
	public void addItems(List<BreakfastSingleWrapper> items) {
		list.addAll(items);
	}
	
	public BreakfastSingleWrapper findItem(long id) {
		for (int i = 0; i < list.size(); i++) {
			BreakfastSingleWrapper item = list.get(i);
			if (item.getId() == id) {
				return item; 
			}
		}
		return null;
	}
}
