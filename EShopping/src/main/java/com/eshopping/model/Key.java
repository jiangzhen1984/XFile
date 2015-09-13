package com.eshopping.model;

import java.io.Serializable;

public class Key implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5951564677549078335L;

	public static final int KEY_TYPE_ITEM = 1;
	public static final int KEY_TYPE_COMBO_ITEM = 2;
	
	private static final long KEY_TYPE_ITEM_PRIME = 0x10000000000000L;
	
	private static final long KEY_TYPE_COMBO_ITEM_PRIME = 0x20000000000000L;

	private long key;

	private Key(long key) {
		this.key = key;
	}

	public static Key getKey(int type, long id) {
		switch (type) {
		case KEY_TYPE_ITEM:
			return new Key(KEY_TYPE_ITEM_PRIME | id);
		case KEY_TYPE_COMBO_ITEM:
			return new Key(KEY_TYPE_COMBO_ITEM_PRIME | id);
		default:
			return new Key(id);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (key ^ (key >>> 32));
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
		Key other = (Key) obj;
		if (key != other.key)
			return false;
		return true;
	}

}
