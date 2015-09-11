package com.eshopping.service;

import java.util.List;

import com.eshopping.model.vo.Category;

public class CacheLoader {

	
	
	public CacheLoader() {
	}

	
	public void init() {
		GlobalCache cacheManger = GlobalCache.getInstance();
		EShoppingService service = new EShoppingService();
		List<Category> list = service.queryCategory(0, 200);
		
		//update category
		if (list != null) {
			for (Category c : list) {
				cacheManger.putCategoryToCache(c);
				if (c.getParentId() > 0) {
					Category parent = cacheManger.getCategory(c.getParentId());
					if (parent == null) {
						//FIXME log
					} else {
						c.updateParent(parent);
					}
					
				} else {
					cacheManger.addTopLevelCategory(c);
				}
			}
		}
		
		
	}
}
