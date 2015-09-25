package com.eshopping.model.vo;

import com.eshopping.model.po.ESItem;



public class SingleShoppingItem extends AbsShoppingItem {
	

	private String bannerUrl;
	
	private String shopShowUrl;

	public SingleShoppingItem() {
		super(TYPE_SINGLE);
	}

	
	public SingleShoppingItem(ESItem item) {
		super(TYPE_SINGLE);
		if (item == null) {
			throw new NullPointerException(" ESItem can not be null");
		}
		this.id = item.getId();
		this.name = item.getName();
		this.price = item.getPrice();
		this.stuff = item.getStuff();
		this.bannerUrl = item.getBannerUrl();
		this.shopShowUrl = item.getShopShowUrl();
		this.description = item.getDescription();
	}


	public String getBannerUrl() {
		return bannerUrl;
	}


	public void setBannerUrl(String bannerUrl) {
		this.bannerUrl = bannerUrl;
	}


	public String getShopShowUrl() {
		return shopShowUrl;
	}


	public void setShopShowUrl(String shopShowUrl) {
		this.shopShowUrl = shopShowUrl;
	}


	
	
}
