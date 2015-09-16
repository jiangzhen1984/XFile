package com.eshopping.controller.order;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.eshopping.controller.SessionConfigBean;
import com.eshopping.model.vo.Address;
import com.eshopping.model.vo.User;
import com.eshopping.service.ESUserService;
import com.eshopping.service.ServiceFactory;

@ManagedBean(name = "addressSelectionDataBean")
@RequestScoped
public class AddressSelectionDataBean {

	@ManagedProperty(value = "#{sessionConfigBean}")
	private SessionConfigBean sessionConfigBean;

	private User user;
	
	private List<Address> addressList;
	
	private int addressCount;
	
	private boolean isLoad;

	public AddressSelectionDataBean() {
	}

	public void setSessionConfigBean(SessionConfigBean sessionConfigBean) {
		this.sessionConfigBean = sessionConfigBean;
		this.user = sessionConfigBean.getUser();
	}

	
	
	public int getAddressCount() {
		return addressCount;
	}

	public List<Address>  getAddressList() {
		if (!isLoad) {
			ESUserService service = ServiceFactory.getESUserService();
			addressList = service.queryUserAddress(user);
			isLoad = true;
			addressCount = addressList == null ? 0 : addressList.size();
		} 
		return addressList;
	}
	
	
	
	public void addAddress() {
		
	}
	
	public void removeAddress() {
		
	}
	
	public String confirmAddress() {
		String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("useAddress");
		return "orderconfirm";
	}
}
