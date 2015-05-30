package com.todaybreakfast.model.vo;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Cart {
	
	private int count;
	
	private float totalPrice;
	
	private Map<Long, InnerBox> cache;

	
	public Cart() {
		cache = new HashMap<Long, InnerBox>();
		count = 0;
		totalPrice = 0F;
	}
	
	
	public void addBreakfast(BreakfastWrapper bw) {
		if (bw == null) {
			return;
		}
		InnerBox box = cache.get(Long.valueOf(bw.getId()));
		if (box == null) {
			box = new InnerBox();
			box.wr = bw;
			cache.put(Long.valueOf(bw.getId()), box);
		}
		box.count ++; 
		count ++;
		totalPrice += bw.getPrice();
	}
	
	public void minusBreakfast(BreakfastWrapper bw) {
		if (bw == null) {
			return;
		}
		InnerBox box = cache.get(Long.valueOf(bw.getId()));
		if (box == null) {
			return;
		}
		box.count --; 
		count --;
		totalPrice -= bw.getPrice();
		if (box.count == 0) {
			cache.remove(Long.valueOf(bw.getId()));
		}
	}
	
	
	public void removeBreakfastItem(BreakfastWrapper bw) {
		InnerBox box = cache.remove(Long.valueOf(bw.getId()));
		if (box != null) {
			count -= box.count;
			totalPrice -= (box.wr.getPrice() * box.count);
		}
	}
	
	
	public void clear() {
		cache.clear();
		count = 0;
	}
	
	public int getItemCount() {
		return count;
	}
	
	public float getTotalPrice() {
		return totalPrice;
	}
	
	public Set<Entry<Long, InnerBox>> getItems() {
		return cache.entrySet();
	}
	
	public class InnerBox {
		BreakfastWrapper wr;
		int count;
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((wr == null) ? 0 : wr.hashCode());
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
			InnerBox other = (InnerBox) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (wr == null) {
				if (other.wr != null)
					return false;
			} else if (!wr.equals(other.wr))
				return false;
			return true;
		}
		private Cart getOuterType() {
			return Cart.this;
		}
		
		
		public BreakfastWrapper getWr(){
			return wr;
		}
		
		public int getCount(){
			return count;
		}
	}
}
