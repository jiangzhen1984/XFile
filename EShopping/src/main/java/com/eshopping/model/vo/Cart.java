package com.eshopping.model.vo;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.eshopping.model.Key;

public class Cart {
	
	private int count;
	
	private float totalPrice;
	
	private Map<Key, InnerBox> cache;

	
	public Cart() {
		cache = new HashMap<Key, InnerBox>();
		count = 0;
		totalPrice = 0F;
		for (int i = 1; i < 10; i++) {
			SingleShoppingItem s = new SingleShoppingItem();
			s.setId(i);
			addItem(s);
		}
	}
	
	
	public void addItem(AbsShoppingItem bw) {
		if (bw == null) {
			return;
		}
		Key key = Key.getKey(bw.getType(), bw.getId());
		InnerBox box = cache.get(key);
		if (box == null) {
			box = new InnerBox();
			box.wr = bw;
			cache.put(key, box);
		}
		box.count ++; 
		count ++;
		totalPrice += bw.getPrice();
	}
	
	public void minusItem(AbsShoppingItem bw) {
		if (bw == null) {
			return;
		}
		Key key = Key.getKey(bw.getType(), bw.getId());
		InnerBox box = cache.get(key);
		if (box == null) {
			return;
		}
		box.count --; 
		count --;
		totalPrice -= bw.getPrice();
		if (box.count == 0) {
			cache.remove(key);
		}
	}
	
	
	public void removeItem(AbsShoppingItem bw) {
		if (bw == null) {
			return;
		}
		Key key = Key.getKey(bw.getType(), bw.getId());
		InnerBox box = cache.remove(key);
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
	
	public Set<Entry<Key, InnerBox>> getItems() {
		return cache.entrySet();
	}
	

	public class InnerBox {
		AbsShoppingItem wr;
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
		
		
		public AbsShoppingItem getWr(){
			return wr;
		}
		
		public int getCount(){
			return count;
		}
		
		public void setCount(int count) {
			
		}
	}
}
