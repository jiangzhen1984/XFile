package com.todaybreakfast.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.todaybreakfast.model.vo.Cart;

@ManagedBean(name="cartBean", eager = true)
@SessionScoped
public class CartBean {

	private Cart cart;
	
	public CartBean() {
		cart = new Cart();
	}
	
	public int getTotalCount() {
		return cart.getItemCount();
	}
	

	public Cart getCart() {
		return this.cart;
	}
}
