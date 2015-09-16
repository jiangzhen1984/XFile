package com.eshopping.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.eshopping.model.vo.AbsShoppingItem;
import com.eshopping.service.GlobalCache;

@ManagedBean(name = "indexDataBean", eager = false)
@SessionScoped
public class IndexDataBean {

	private List<AbsShoppingItem> mRecommandList;
	
	private List<AbsShoppingItem> mNewList;
	
	public List<AbsShoppingItem> getRecommandList() {
		
		if (mRecommandList == null) {
			mRecommandList = GlobalCache.getInstance().getRecommandationList();
		}
		if (mRecommandList == null || mRecommandList.size() <= 0) {
			List<AbsShoppingItem> all = GlobalCache.getInstance().getItemList();
			if (all.size() > 0) {
				if (all.size() >= 15) {
					mRecommandList = all.subList(0, 15);
				} else {
					mRecommandList = all.subList(0, all.size());
				}
			}
		}
		return mRecommandList;
	}
	
	
	public List<AbsShoppingItem> getNewList() {
		if (mNewList == null) {
			mNewList = GlobalCache.getInstance().getNewList();
		}
		if (mNewList == null || mNewList.size() <= 0) {
			List<AbsShoppingItem> all = GlobalCache.getInstance().getItemList();
			if (all.size() > 0) {
				if (all.size() >= 15) {
					mNewList = all.subList(0, 15);
				} else {
					mNewList = all.subList(0, all.size());
				}
			}
		}
		return mNewList;
	}
}
