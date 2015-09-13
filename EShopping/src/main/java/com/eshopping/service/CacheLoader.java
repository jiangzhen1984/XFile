package com.eshopping.service;

import java.util.List;

import com.eshopping.model.po.EShoppingItemConfigDetail;
import com.eshopping.model.po.EStemSpecialTypeMapping;
import com.eshopping.model.vo.AbsShoppingItem;
import com.eshopping.model.vo.Category;
import com.eshopping.model.vo.CategoryItemSpecialType;

public class CacheLoader {

	
	
	public CacheLoader() {
	}

	
	public void init() {
		GlobalCache cacheManager = GlobalCache.getInstance();
		EShoppingService service = new EShoppingService();
	
		
		//Initialize single item list
		int start = 0;
		int fetchCount = 500;
		List<AbsShoppingItem>  shoppingItemList = null;
		
		do {
			shoppingItemList = service.queryItemList(AbsShoppingItem.TYPE_SINGLE, start, fetchCount);
			start += fetchCount;
			if (shoppingItemList != null && shoppingItemList.size() > 0) {
				cacheManager.initAllItems(shoppingItemList);
			}
		} while (shoppingItemList != null && shoppingItemList.size() > 0);
		
		//Initialize combo item list
		start = 0;
		fetchCount = 500;
		shoppingItemList = null;
		do {
			shoppingItemList = service.queryItemList(AbsShoppingItem.TYPE_COMBO, start, fetchCount);
			start += fetchCount;
			if (shoppingItemList != null && shoppingItemList.size() > 0) {
				cacheManager.initAllItems(shoppingItemList);
			}
		} while (shoppingItemList != null && shoppingItemList.size() > 0);
		
		
		//Initialize recommendation list
		start = 0;
		fetchCount = 500;
		List<EShoppingItemConfigDetail>  itemConfigList = null;
		do {
			itemConfigList = service.getRecommandationList(start, fetchCount);
			start += fetchCount;
			if (itemConfigList != null && itemConfigList.size() > 0) {
				updateSpecialItemConfig(cacheManager, itemConfigList, GlobalCache.UPDATE_LIST_TYPE_RECOMMAND);
			}
		} while (itemConfigList != null && itemConfigList.size() > 0);
		
		//Initialize new list
		start = 0;
		fetchCount = 500;
		itemConfigList = null;
		do {
			itemConfigList = service.getNewList(start, fetchCount);
			start += fetchCount;
			if (itemConfigList != null && itemConfigList.size() > 0) {
				updateSpecialItemConfig(cacheManager, itemConfigList, GlobalCache.UPDATE_LIST_TYPE_NEW);
			}
		} while (itemConfigList != null && itemConfigList.size() > 0);
		
		
		
		List<Category> list = service.queryCategory(0, 500);
		loadAllCategory(service, cacheManager, list);
	}
	
	
	
	private void loadAllCategory(EShoppingService service, GlobalCache cacheManager, List<Category> list) {
		//update category
		if (list != null) {
			for (Category c : list) {
				cacheManager.putCategoryToCache(c);
				if (c.getParentId() > 0) {
					Category parent = cacheManager.getCategory(c.getParentId());
					if (parent == null) {
						//FIXME log
					} else {
						parent.addSubCategory(c);
					}
					
				} else {
					cacheManager.addTopLevelCategory(c);
				}
				
				loadCategorySpecialType(service, cacheManager, c);
				loadCategoryItem(service, cacheManager, c);
				loadItemSpecialType(service, cacheManager, c);
			}
		}
	}
	
	
	private void loadCategorySpecialType(EShoppingService service, GlobalCache cacheManager, Category cate) {
		 List<CategoryItemSpecialType>  list = service.getCategorySpecialType(cate);
		 if (list == null || list.size() <= 0) {
			 return;
		 }
		 for (CategoryItemSpecialType stype : list) {
			 cate.addType(stype);
			 GlobalCache.getInstance().addSpecialType(stype);
		 }
	}
	
	
	private void loadCategoryItem(EShoppingService service, GlobalCache cacheManager, Category cate) {
		//Initialize new list
		int start = 0;
		int fetchCount = 500;
		List<AbsShoppingItem> items = null;
		do {
			items = service.queryItemListLazy(cate.getId(), start, fetchCount);
			start += fetchCount;
			if (items == null) {
				break;
			}
			for (AbsShoppingItem item : items) {
				AbsShoppingItem cache = cacheManager.getShoppingItem(item.getType(), item.getId());
				cate.addItem(cache);
			}
		} while (items != null && items.size() > 0);
		
		//put cache
		cacheManager.initCategoryItems(cate, cate.getItems());
		
	}
	
	
	private void loadItemSpecialType(EShoppingService service, GlobalCache cacheManager, Category cate) {
		//Initialize recommendation list
			int start = 0;
			int fetchCount = 500;
			List<EStemSpecialTypeMapping>  itemTypeList = null;
			do {
				itemTypeList = service.querySpecialTypeMapping(cate, start, fetchCount);
				start += fetchCount;
				if (itemTypeList != null && itemTypeList.size() > 0) {
					for (EStemSpecialTypeMapping mapping : itemTypeList) {
						AbsShoppingItem cache = cacheManager.getShoppingItem(mapping.getItemType(), mapping.getItemId());
						CategoryItemSpecialType type = cacheManager.getSpecialType(mapping.getTypeId());
						cache.addSpecialType(type);
					}
				}
			} while (itemTypeList != null && itemTypeList.size() > 0);
	}
	
	
	
	private void updateSpecialItemConfig(GlobalCache cacheManager, List<EShoppingItemConfigDetail> configList, int type) {
		for (EShoppingItemConfigDetail config : configList) {
			AbsShoppingItem cache = cacheManager.getShoppingItem(config.getItemType(), config.getId());
			if (cache == null) {
				//FIXME logging
				continue;
			}
			cache.setHot(config.isHot());
			cache.setSale(config.isSale());
			cache.setRecommand(config.isRecommand());
			cache.setNew(config.isNew());
//			cache.setSaledCounts(config.getSaledCounts());
//			cache.setRank(config.getRank());
//			cache.setStockDate(config.getStockDate());
			cacheManager.updateCacheList(type, GlobalCache.OPT_ADD, cache);
		}
	}
	
}
