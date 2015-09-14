package com.eshopping.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import com.commons.collections.CollectionsUtils;
import com.eshopping.controller.groupfilter.SpecialTypeFilter;
import com.eshopping.model.vo.AbsShoppingItem;
import com.eshopping.model.vo.Category;
import com.eshopping.model.vo.CategoryItemSpecialTypeGroup;
import com.eshopping.service.EShoppingService;
import com.eshopping.service.GlobalCache;

@ManagedBean(name = "shopDataBean", eager = false)
@RequestScoped
public class ShopDataBean {

	private Category mCurrentCategory;
	private int start;
	private int fetchCount = 15;
	private List<AbsShoppingItem> itemList;
	private long filterIdList[];
	
	public ShopDataBean() {
		String strCategory = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("category");
		Long cateId = 0L;
		if (strCategory != null && !strCategory.isEmpty()) {
			try {
				cateId = Long.parseLong(strCategory);
			} catch (NumberFormatException e) {
			}
		}
		mCurrentCategory  = GlobalCache.getInstance().getCategory(cateId);
		if (mCurrentCategory == null) {
			itemList = null;
		}
		
		String[] strFilters = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterValuesMap().get("filterid");
		if (strFilters != null && strFilters.length > 0) {
			filterIdList = new long[strFilters.length];
			for (int i = 0; i < strFilters.length; i++) {
				filterIdList[i] = Long.parseLong(strFilters[i]);
			}
		}
		updateList();
	}
	
	public List<AbsShoppingItem> getRecommandList() {
		return GlobalCache.getInstance().getRecommandationList();
	}
	
	
	public List<AbsShoppingItem> getCategoryItemList() {
		return itemList;
	}
	
	
	public List<CategoryItemSpecialTypeGroup> getCategorySpecialTypeGroupList() {
		Category cate = mCurrentCategory;
		if (cate == null) {
			//TODO return default
			return null;
		}
		
		if (!cate.isLoadTypes()) {
			EShoppingService service = new EShoppingService();
			service.getCategorySpecialType(cate);
		}
		return cate.getTypeGroups();
	}
	
	
	public Category getSelectedCategory() {
		return mCurrentCategory;
	}
	
	
	public int getFetchCount() {
		return this.fetchCount;
	}
	
	public void setFetchCount(int count) {
		this.fetchCount = count;
	}
	
	public void updateFetchCount(ValueChangeEvent event) {
		this.fetchCount = (Integer)event.getNewValue();
		updateList();
	}


	public long[] getFilterIdList() {
		return filterIdList;
	}


	public void setFilterIdList(long[] filterIdList) {
		this.filterIdList = filterIdList;
		updateList();
	}
	
	private void updateList() {
		int fcount = fetchCount;
		List<AbsShoppingItem> tmp = null;
		if (this.mCurrentCategory == null) {
			tmp = GlobalCache.getInstance().getItemList();
		} else {
			tmp = mCurrentCategory.getItems();
		}
		if (filterIdList != null && filterIdList.length > 0) {
			SpecialTypeFilter filter = new SpecialTypeFilter();
			filter.addFilterTypeId(filterIdList);
			List<AbsShoppingItem>  filtered = new ArrayList<AbsShoppingItem>();
			CollectionsUtils.filter(tmp, filtered, filter);
			tmp = filtered;
		}
		
		if (tmp == null || tmp.size() <= 0) {
			itemList = null;
			return;
		}
		int size = tmp.size();

		if (start < 0) {
			start = 0;
		} else if (start > size) {
			start = size -1;
		} else if (start + fetchCount >= size && start < size) {
			fcount = size - start -1;
		} 
		itemList = tmp.subList(start, start + fcount);
	}
	
}
