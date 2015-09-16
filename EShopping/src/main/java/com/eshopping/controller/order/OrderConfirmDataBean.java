package com.eshopping.controller.order;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.eshopping.controller.SessionConfigBean;
import com.eshopping.model.vo.Address;
import com.eshopping.model.vo.User;
import com.eshopping.model.vo.Cart.InnerBox;
import com.eshopping.service.ServiceFactory;

@ManagedBean(name = "orderConfirmDataBean")
@RequestScoped
public class OrderConfirmDataBean {

	@ManagedProperty(value = "#{sessionConfigBean}")
	private SessionConfigBean sessionConfigBean;
	
	@ManagedProperty(value = "#{checkoutDataBean}")
	private CheckoutDataBean cartDataBean;

	private User user;
	private String address;

	public OrderConfirmDataBean() {
	}

	public void setSessionConfigBean(SessionConfigBean sessionConfigBean) {
		this.sessionConfigBean = sessionConfigBean;
		this.user = sessionConfigBean.getUser();
	}
	
	


	public void setCartDataBean(CheckoutDataBean cartDataBean) {
		this.cartDataBean = cartDataBean;
	}

	public Collection<InnerBox> getCartItems() {
		return cartDataBean.getCart().getItemValues();
	}
	
	
	public float getTotalPrice() {
		return cartDataBean.getCart().getTotalPrice();
	}
	
	public String getShipAddress() {
		return address;
	}
	
	public int getTotalCount() {
		return cartDataBean.getTotalCount();
	}
	
	public String confirmOrder() {
		String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("useAddress");
		return "orderconfirm";
	}
}
