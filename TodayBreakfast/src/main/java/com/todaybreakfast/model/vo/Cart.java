package com.todaybreakfast.model.vo;

import java.util.HashMap;
import java.util.Map;

public class Cart {
	
	private int count;
	
	private Map<BreakfastWrapper, InnerBox> cache;

	
	public Cart() {
		cache = new HashMap<BreakfastWrapper, InnerBox>();
		count = 0;
	}
	
	
	public void addBreakfast(BreakfastWrapper bf) {
		InnerBox box = cache.get(bf);
		if (box == null) {
			box = new InnerBox();
			box.wr = bf;
			cache.put(bf, box);
		}
		box.count ++; 
		count ++;
	}
	
	public void removeBreakfast(BreakfastWrapper bw) {
		InnerBox box = cache.get(bw);
		if (box == null) {
			return;
		}
		box.count --; 
		count --;
	}
	
	public void clear() {
		cache.clear();
		count = 0;
	}
	
	public int getItemCount() {
		return count;
	}
	
	
	class InnerBox {
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
		
	}
}
