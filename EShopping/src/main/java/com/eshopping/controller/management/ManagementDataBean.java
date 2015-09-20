package com.eshopping.controller.management;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.eshopping.model.vo.Category;
import com.eshopping.service.GlobalCache;
import com.eshopping.service.ServiceFactory;


@ManagedBean(name = "managementDataBean")
@SessionScoped
public class ManagementDataBean {

	private String categoryName;
	
	
	
	public String getCategoryName() {
		return categoryName;
	}



	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}



	public void createCategory() {
		Category newCate = new Category();
		newCate.setName(categoryName);
		ServiceFactory.getEShoppingService().addCategory(newCate);
		if (newCate.getParent() != null) {
			
		} else {
			GlobalCache.getInstance().addTopLevelCategory(newCate);
		}
	}
}
