package com.eshopping.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.eshopping.model.vo.AbsShoppingItem;
import com.eshopping.service.GlobalCache;

@ManagedBean(name = "indexDataBean", eager = false)
@SessionScoped
public class IndexDataBean {

	
	public List<AbsShoppingItem> getRecommandList() {
		return GlobalCache.getInstance().getRecommandationList();
	}
	
	
	public List<AbsShoppingItem> getNewList() {
		return GlobalCache.getInstance().getNewList();
	}
}
