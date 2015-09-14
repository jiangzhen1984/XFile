package com.eshopping.controller;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.eshopping.model.po.Order;
import com.eshopping.service.OrderService;



@ManagedBean(name="orderBean")
@RequestScoped
public class OrderBean {

	@ManagedProperty(value="#{cartBean}")
	private CartBean cartBean;
	
	
	@ManagedProperty(value="#{sessionConfigBean}")
	private SessionConfigBean serssionConfigBean;
	
	private String retrievePlace;
	
	
	private OrderService service;
	public OrderBean() {
		service = new OrderService();
		
	}
	
	public void setCartBean(CartBean cartBean) {
		this.cartBean = cartBean;
	}



	



	public void setSerssionConfigBean(SessionConfigBean serssionConfigBean) {
		this.serssionConfigBean = serssionConfigBean;
	}

	public String getRetrievePlace() {
		return retrievePlace;
	}

	public void setRetrievePlace(String retrievePlace) {
		this.retrievePlace = retrievePlace;
	}

	public String pay() {
		if (cartBean == null) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.addMessage("cellphone", new FacesMessage("没有找到订单信息，请重新下单"));
			return "failed";
		}
		if (cartBean.getTotalCount() <= 0) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.addMessage("cellphone", new FacesMessage("购物车空空的。。"));
			return "failed";

		}
		
		if (serssionConfigBean == null || !serssionConfigBean.isLogin()) {
			return "login";
		}
		
//		Order order= service.checkoutCart(cartBean.getCart(), userBean.getLoggedInUser(), retrievePlace);
//		if (order != null) {
//			//TODO redirect pay page
//			return "";
//		} else {
//			FacesContext facesContext = FacesContext.getCurrentInstance();
//			facesContext.addMessage("order", new FacesMessage("创建订单信息失败。。"));
//			return "failed";
//		}
		return "";
	}
	
	
	private List<Order> unPaidList;
	private List<Order> paidList;
	private List<Order> finishedList;
	
	
	public List<Order> getUnPaidOrderList() {
		if (!checkLogin()) {
			return null;
		}
		
//		if (unPaidList == null) {
//			unPaidList = service.getUserOrderList(userBean.getLoggedInUser(), Order.OrderState.NOT_PAIED);
//		}
		return unPaidList;
	}
	
	
	public List<Order> getPaidOrderList() {
		if (!checkLogin()) {
			return null;
		}
		
//		if (paidList == null) {
//			paidList = service.getUserOrderList(userBean.getLoggedInUser(), Order.OrderState.PAIED);
//		}
		return paidList;
	}
	
	
	public List<Order> getFinishedOrderList() {
		if (!checkLogin()) {
			return null;
		}
		
//		if (finishedList == null) {
//			finishedList = service.getUserOrderList(userBean.getLoggedInUser(), Order.OrderState.COMPLETED);
//		}
		return finishedList;
	}
	
	
	private boolean checkLogin() {
		return !(serssionConfigBean == null || !serssionConfigBean.isLogin());
	}
}
