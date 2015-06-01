package com.todaybreakfast.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.todaybreakfast.model.vo.BreakfastWrapper;
import com.todaybreakfast.model.vo.Cart;
import com.todaybreakfast.service.BreakfastBasicService;

@ManagedBean(name = "cartBean", eager = false)
@SessionScoped
public class CartBean {

	
	@ManagedProperty(value="#{userBean}")
	private UserBean userBean;
	
	private Cart cart;
	private BreakfastBasicService service;
	private String retrieveDate;
	

	public CartBean() {
		cart = new Cart();
		service = new BreakfastBasicService();
	}

	public int getTotalCount() {
		return cart.getItemCount();
	}

	public Cart getCart() {
		return this.cart;
	}
	
	

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	public void removeItem(long id, int itemType) {
		BreakfastWrapper bw = new BreakfastWrapper();
		bw.setType(itemType == BreakfastWrapper.Type.SINGLE
						.ordinal() ? BreakfastWrapper.Type.SINGLE
						: BreakfastWrapper.Type.COMBO);
		bw.setId(id);
		cart.removeBreakfastItem(bw);
	}

	public void updateCart(long id, int type, int itemType) {
		BreakfastWrapper wrp = service
				.findBreakfast(id, itemType == BreakfastWrapper.Type.SINGLE
						.ordinal() ? BreakfastWrapper.Type.SINGLE
						: BreakfastWrapper.Type.COMBO);
		if (type == 1) {
			cart.minusBreakfast(wrp);
		} else {
			
			cart.addBreakfast(wrp);
		}
	}

	public String getRetrieveDate() {
		return retrieveDate;
	}

	public void setRetrieveDate(String retrieveDate) {
		this.retrieveDate = retrieveDate;
	}
	


	public String checkout() {
		if (userBean.getLoggedInUser() != null) {
			if (cart.getItemCount() <= 0 || cart.getTotalPrice() <= 0) {
				return "bflist";
			} else {
				return "order";
			}
		} else {
			return "login";
		}
	}
	
}
