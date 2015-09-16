package com.eshopping.controller.personel;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import com.eshopping.controller.SessionConfigBean;
import com.eshopping.model.vo.Address;
import com.eshopping.model.vo.User;
import com.eshopping.service.ESUserService;
import com.eshopping.service.ServiceFactory;

@ManagedBean(name = "personelAddressDataBean")
@RequestScoped
public class PersonelAddressDataBean {

	@ManagedProperty(value = "#{sessionConfigBean}")
	private SessionConfigBean sessionConfigBean;

	private User user;
	
	private List<Address> addressList;
	
	private int addressCount;
	
	private boolean isLoad;

	public PersonelAddressDataBean() {
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
			ESUserService service = new ESUserService();
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
	
	public void updateDefaultAddress(long addressId) {
		boolean ldFlag =false;
		Address lastDefault = null;
		Address ar = null;
		boolean arFlag = false;
		for (int i = 0; i < addressList.size(); i++) {
			Address addr = addressList.get(i);
			if (addr.getId() == addressId) {
				ar = addr;
				arFlag = true;
			}
			if (addr.isDefault()) {
				lastDefault = addr;
				ldFlag = true;
				
			}
			if (ldFlag && arFlag) {
				break;
			}
		}
		if (lastDefault != null) {
			lastDefault.setDefault(false);
		}
		if (ar != null) {
			ServiceFactory.getESUserService().updateDefaultAddress(ar, true);
			ar.setDefault(true);
		} else {
			//TODO update notification
		}
	}

}
