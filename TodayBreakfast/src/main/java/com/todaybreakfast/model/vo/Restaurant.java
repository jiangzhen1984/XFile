package com.todaybreakfast.model.vo;

import java.util.ArrayList;
import java.util.List;



public class Restaurant {
	
	
	private int mRestId;
	
	private String mName;
	
	private Location mLoct;
	
	private float mRate;
	
	private List<Menu> mMenuList;

	public Restaurant(int mRestId, String mName, Location mLoct, float mRate) {
		super();
		this.mRestId = mRestId;
		this.mName = mName;
		this.mLoct = mLoct;
		this.mRate = mRate;
		mMenuList = new ArrayList<Menu>();
	}

	public Restaurant(int mRestId, String mName, Location mLoct) {
		this(mRestId, mName, mLoct, 0.0F);
	}

	public int getRestId() {
		return mRestId;
	}

	public void setRestId(int mRestId) {
		this.mRestId = mRestId;
	}

	public String getName() {
		return mName;
	}

	public void setName(String mName) {
		this.mName = mName;
	}

	public Location getLoct() {
		return mLoct;
	}

	public void setLoct(Location mLoct) {
		this.mLoct = mLoct;
	}

	public float getRate() {
		return mRate;
	}

	public void setRate(float mRate) {
		this.mRate = mRate;
	}
	

	
	public void  addMenu(Menu m) {
		if (m == null) {
			throw new RuntimeException(" Menu can not be null");
		}
		mMenuList.add(m);
	}
	
	public void removeMenu(Menu m) {
		mMenuList.remove(m);
	}
	
}
