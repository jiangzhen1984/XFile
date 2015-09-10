package com.eshopping.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.eshopping.model.po.ESCategory;
import com.eshopping.model.vo.AbsShoppingItem;
import com.eshopping.model.vo.Category;

public class GlobalCache {

	public static final String KEY_ITEM = "item";
	public static final String KEY_COMBO_ITEM = "combo_item";
	public static final String KEY_CATEGORY = "category";

	public static final int OPT_ADD = 1;
	public static final int OPT_REMOVE = 2;

	public static final int UPDATE_LIST_TYPE_HOT = 1;
	public static final int UPDATE_LIST_TYPE_NEW = 2;
	public static final int UPDATE_LIST_TYPE_RECOMMAND = 3;

	private Map<String, Object> mLocalCategoryCache;
	private Map<Category, List<AbsShoppingItem>> mLocalCategoryItemsCache;

	private List<AbsShoppingItem> mRecommandList;
	private List<AbsShoppingItem> mHotList;
	private List<AbsShoppingItem> mNewList;

	private static GlobalCache instance;

	private GlobalCache() {
		mLocalCategoryCache = new ConcurrentHashMap<String, Object>();
		mLocalCategoryItemsCache = new ConcurrentHashMap<Category, List<AbsShoppingItem>>();

		mRecommandList = new ArrayList<AbsShoppingItem>();
		mHotList = new ArrayList<AbsShoppingItem>();
		mNewList = new ArrayList<AbsShoppingItem>();
	}

	public synchronized GlobalCache getInstance() {
		if (instance == null) {
			instance = new GlobalCache();
		}
		return instance;
	}

	public List<ESCategory> getTopLiveCategoryList() {
		return (List<ESCategory>) mLocalCategoryCache.get(KEY_CATEGORY);
	}

	public List<AbsShoppingItem> getCategoryItemList(Category cate, int start,
			int fetchCont) {
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
