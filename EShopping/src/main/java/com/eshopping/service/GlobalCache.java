package com.eshopping.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.eshopping.model.Key;
import com.eshopping.model.vo.AbsShoppingItem;
import com.eshopping.model.vo.Category;
import com.eshopping.model.vo.CategoryItemSpecialType;

public class GlobalCache {

	public static final String KEY_ITEM = "item";
	public static final String KEY_COMBO_ITEM = "combo_item";
	public static final String KEY_CATEGORY = "category";

	public static final int OPT_ADD = 1;
	public static final int OPT_REMOVE = 2;

	public static final int UPDATE_LIST_TYPE_HOT = 1;
	public static final int UPDATE_LIST_TYPE_NEW = 2;
	public static final int UPDATE_LIST_TYPE_RECOMMAND = 3;
	

	private Map<Long, Category> mLocalCategoryMap;
	private Map<Long, CategoryItemSpecialType> mLocalSpecialTypeMap;
	private Map<Category, List<AbsShoppingItem>> mLocalCategoryItemsCache;
	private Map<Key, AbsShoppingItem> mAllItemMap;

	private List<Category> mTopLevelCategoryList;
	private List<AbsShoppingItem> mRecommandList;
	private List<AbsShoppingItem> mHotList;
	private List<AbsShoppingItem> mNewList;
	private List<AbsShoppingItem> mAllItemList;

	private static GlobalCache instance;

	private GlobalCache() {
		mLocalCategoryMap = new ConcurrentHashMap<Long, Category>();
		mLocalCategoryItemsCache = new ConcurrentHashMap<Category, List<AbsShoppingItem>>();
		mAllItemMap = new ConcurrentHashMap<Key, AbsShoppingItem>();
		mLocalSpecialTypeMap = new ConcurrentHashMap<Long, CategoryItemSpecialType>();

		mTopLevelCategoryList = new ArrayList<Category>();
		mRecommandList = new ArrayList<AbsShoppingItem>();
		mHotList = new ArrayList<AbsShoppingItem>();
		mNewList = new ArrayList<AbsShoppingItem>();
		mAllItemList = new ArrayList<AbsShoppingItem>();
		
	}

	public static synchronized GlobalCache getInstance() {
		if (instance == null) {
			instance = new GlobalCache();
		}
		return instance;
	}

	public List<Category> getTopLevelCategoryList() {
		return mTopLevelCategoryList;
	}
	
	public void addTopLevelCategory(Category cate) {
		if (cate == null) {
			return;
		}
		this.mTopLevelCategoryList.add(cate);
	}
	public void removeTopLevelCategory(Category cate) {
		if (cate == null) {
			return;
		}
		mTopLevelCategoryList.remove(cate);
	}
	
	public Category getCategory(long id) {
		return mLocalCategoryMap.get(id);
	}

	
	public void putCategoryToCache(Category cate) {
		if (cate == null) {
			return;
		}
		mLocalCategoryMap.put(Long.valueOf(cate.getId()), cate);
	}
	
	public void removeCategoryFromCache(Category cate) {
		if (cate == null) {
			return;
		}
		mLocalCategoryMap.remove(Long.valueOf(cate.getId()));
		mLocalCategoryItemsCache.remove(cate);
	}
	
	
	public List<AbsShoppingItem> getCategoryItemList(Category cate, int start,
			int fetchCont) {
		if (cate == null || start < 0 || fetchCont < 0) {
			return null;
		}
		List<AbsShoppingItem> list = mLocalCategoryItemsCache.get(cate);
		if (list != null) {
			int size = list.size();
			if (size < start) {
				return null;
			} else if (size >= start && size >= (start + fetchCont)) {
				return list.subList(start, start + fetchCont);
			} else {
				return list.subList(start, size);
			}
		} else {
			return null;
		}
	}
	
	public List<AbsShoppingItem> getItemList() {
		return mAllItemList;
	}
	
	public void initCategoryItems(Category cate, List<AbsShoppingItem> list) {
		mLocalCategoryItemsCache.put(cate, list);
	}
	
	
	public void initAllItems(List<AbsShoppingItem> list) {
		mAllItemList.addAll(list);
		for(AbsShoppingItem item : list) {
			mAllItemMap.put(Key.getKey(item.getType(), item.getId()), item);
		}
	}
	
	
	public void addSpecialType(CategoryItemSpecialType sType) {
		if (sType == null) {
			return;
		}
		mLocalSpecialTypeMap.put(sType.getId(), sType);
	}
	
	public CategoryItemSpecialType getSpecialType(long id) {
		return mLocalSpecialTypeMap.get(id);
	}
	
	public AbsShoppingItem getShoppingItem(int type, long id) {
		return this.mAllItemMap.get(Key.getKey(type, id));
	}

	public List<AbsShoppingItem> getHotList() {
		return mHotList;
	}

	public List<AbsShoppingItem> getNewList() {
		return mNewList;
	}

	public List<AbsShoppingItem> getRecommandationList() {
		return mRecommandList;
	}

	public void updateCacheList(int type, int opt, AbsShoppingItem item) {
		switch (type) {
		case UPDATE_LIST_TYPE_HOT:
			synchronized (mHotList) {
				if (opt == OPT_ADD) {
					mHotList.add(item);
				} else if (opt == OPT_REMOVE) {
					mHotList.remove(item);
				}
			}

			break;
		case UPDATE_LIST_TYPE_NEW:
			synchronized (mNewList) {
				if (opt == OPT_ADD) {
					mNewList.add(item);
				} else if (opt == OPT_REMOVE) {
					mNewList.remove(item);
				}
			}
			break;
		case UPDATE_LIST_TYPE_RECOMMAND:
			synchronized (mRecommandList) {
				if (opt == OPT_ADD) {
					mRecommandList.add(item);
				} else if (opt == OPT_REMOVE) {
					mRecommandList.remove(item);
				}
			}
			break;
		default:
			throw new RuntimeException("Can not support such type: " + type);
		}
	}

}
