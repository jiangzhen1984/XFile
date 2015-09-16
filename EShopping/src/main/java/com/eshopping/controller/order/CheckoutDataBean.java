package com.eshopping.controller.order;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.eshopping.model.vo.AbsShoppingItem;
import com.eshopping.model.vo.Cart;
import com.eshopping.service.GlobalCache;



@ManagedBean(name = "checkoutDataBean", eager = false)
@SessionScoped
public class CheckoutDataBean {

	
	private Cart cart;
	

	public CheckoutDataBean() {
		cart = new Cart();
	}

	public int getTotalCount() {
		return cart.getItemCount();
	}

	public Cart getCart() {
		return this.cart;
	}
	
	



	public void removeItem(long id, int itemType) {
		updateCart(id, 1, itemType);
	}

	public void updateCart(long id, int opt, int itemType) {
		AbsShoppingItem item = GlobalCache.getInstance().getShoppingItem(itemType, id);
		if (opt == 1) {
			cart.minusItem(item);
		} else {
			cart.addItem(item);
		}
	}


	public String checkout() {
		return "addressselection";
	}
	
}
