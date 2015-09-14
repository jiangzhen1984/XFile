package com.eshopping.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.eshopping.model.vo.Cart;



@ManagedBean(name = "cartBean", eager = false)
@SessionScoped
public class CartBean {

	
	private Cart cart;
	private String retrieveDate;
	

	public CartBean() {
		cart = new Cart();
	}

	public int getTotalCount() {
		return cart.getItemCount();
	}

	public Cart getCart() {
		return this.cart;
	}
	
	



	public void removeItem(long id, int itemType) {
		
//		bw.setId(id);
//		cart.removeBreakfastItem(bw);
	}

	public void updateCart(long id, int type, int itemType) {
//		
//		if (type == 1) {
//			cart.minusBreakfast(wrp);
//		} else {
//			
//			cart.addBreakfast(wrp);
//		}
	}

	public String getRetrieveDate() {
		return retrieveDate;
	}

	public void setRetrieveDate(String retrieveDate) {
		this.retrieveDate = retrieveDate;
	}
	
	
}
