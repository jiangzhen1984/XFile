package com.eshopping.controller.groupfilter;

import com.commons.collections.Criteria;
import com.eshopping.model.vo.AbsShoppingItem;

public class PriceFilter<E extends AbsShoppingItem> implements Criteria<AbsShoppingItem> {

	private float low;
	private float high;
	
	
	
	public PriceFilter(float low, float high) {
		super();
		this.low = low;
		this.high = high;
	}



	@Override
	public boolean evaluate(AbsShoppingItem t) {
		float price = t.getPrice();
		return  high > price && price > low;
	}



	public float getLow() {
		return low;
	}



	public void setLow(float low) {
		this.low = low;
	}



	public float getHigh() {
		return high;
	}



	public void setHigh(float high) {
		this.high = high;
	}

	
	
	


}
