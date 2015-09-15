package com.eshopping.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.eshopping.model.vo.AbsShoppingItem;
import com.eshopping.service.EShoppingService;
import com.eshopping.service.GlobalCache;

@ManagedBean(name="singleDataBean")
@RequestScoped
public class SingleDataBean {

	@ManagedProperty(value="#{checkoutDataBean}")
	private CheckoutDataBean checkDataBean;
	private AbsShoppingItem item;
	private String errMsgAddtoCart;
	
	public SingleDataBean() {
		String strItemid = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("itemid");
		String strItemType = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("itemtype");
		Long itemId = 0L;
		if (strItemid != null && !strItemid.isEmpty()) {
			try {
				itemId = Long.parseLong(strItemid);
			} catch (NumberFormatException e) {
			}
		}
		int type = 1;
		if (strItemType != null && !strItemType.isEmpty()) {
			try {
				type = Integer.parseInt(strItemType);
			} catch (NumberFormatException e) {
			}
		}
		
		item = GlobalCache.getInstance().getShoppingItem(type, itemId);
		
		if (item != null && !item.isLoadImages()) {
			EShoppingService service = new EShoppingService();
			service.queryImageList(item);
		}
	}
	
	
	
	
	
	
	public void setCheckDataBean(CheckoutDataBean checkDataBean) {
		this.checkDataBean = checkDataBean;
	}






	public String getErrMsgAddtoCart() {
		return errMsgAddtoCart;
	}




	public AbsShoppingItem getViewedItem() {
		return item;
	}
	
	
	public String addToCart() {
		if (checkDataBean == null || item == null) {
			errMsgAddtoCart = "Add to cart failed";
			return "fail";
		}
		checkDataBean.updateCart(item.getId(), 2, item.getType());
		return "checkout";
	}
	
}
