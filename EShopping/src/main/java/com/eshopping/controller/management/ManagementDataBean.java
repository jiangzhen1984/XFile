package com.eshopping.controller.management;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.eshopping.model.vo.Category;
import com.eshopping.service.GlobalCache;
import com.eshopping.service.ServiceFactory;


@ManagedBean(name = "managementDataBean")
@SessionScoped
public class ManagementDataBean {
	
	
	private List<Category> cateGoryList;

	private String categoryName;
	
	
	
	public String getCategoryName() {
		return categoryName;
	}



	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	public List<Category> getTopCategoryList() {
		if (cateGoryList == null) {
			cateGoryList = GlobalCache.getInstance().getTopLevelCategoryList();
		}
		return cateGoryList;
	}



	public void createCategory() {
		Category parent = null;
		if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pid") != null) {
			Long pid = Long.parseLong((String)FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pid"));
			parent = GlobalCache.getInstance().getCategory(pid);
		}
		Category newCate = new Category();
		if (parent != null) {
			parent.addSubCategory(newCate);
		}
		
		newCate.setName(categoryName);
		ServiceFactory.getEShoppingService().addCategory(newCate);
		if (newCate.getParent() != null) {
			
		} else {
			GlobalCache.getInstance().addTopLevelCategory(newCate);
		}
		 GlobalCache.getInstance().putCategoryToCache(newCate);
		 
		 cateGoryList = GlobalCache.getInstance().getTopLevelCategoryList();
	}
	
	
	public void updateCategoryParent() {
		Category cate = null;
		if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("cid") != null) {
			Long pid = Long.parseLong((String)FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("cid"));
			cate = GlobalCache.getInstance().getCategory(pid);
		}
		
		Category newParent = null;
		if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pid") != null) {
			Long pid = Long.parseLong((String)FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pid"));
			newParent = GlobalCache.getInstance().getCategory(pid);
		}
		
		if (cate.getParent() != null) {
			cate.getParent().removeSubCategory(cate);
			cate.updateParent(null);
			if (newParent == null) {
				GlobalCache.getInstance().addTopLevelCategory(cate);
			}
		} else {
			 if (newParent != null){
				GlobalCache.getInstance().removeTopLevelCategory(cate);
				newParent.addSubCategory(cate);
			 }
		}
		
		
		ServiceFactory.getEShoppingService().updateCategory(cate);
		
		cateGoryList = GlobalCache.getInstance().getTopLevelCategoryList();
	}
	
	
	public void removeCategory() {
		Category cate = null;
		if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("cid") != null) {
			Long pid = Long.parseLong((String)FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("cid"));
			cate = GlobalCache.getInstance().getCategory(pid);
		}
		
		if (cate.getItems().size() > 0) {
			return;
		}
		
		if (cate.getParent() == null) {
			GlobalCache.getInstance().removeTopLevelCategory(cate);
		} else {
			cate.getParent().removeSubCategory(cate);
			cate.updateParent(null);
		}
		
		ServiceFactory.getEShoppingService().deleteCategory(cate);
		cateGoryList = GlobalCache.getInstance().getTopLevelCategoryList();

	}
}
