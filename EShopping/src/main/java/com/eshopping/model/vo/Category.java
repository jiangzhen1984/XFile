package com.eshopping.model.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.eshopping.model.po.ESCategory;



public class Category extends ESCategory {
	
	protected Category parent;
	
	protected List<Category> subCategory;
	
	protected List<AbsShoppingItem> items;
	
	protected List<CategoryItemSpecialTypeGroup> typeGroups;
	
	private boolean isLoadCategorySpecificalType;
	
	public Category() {
		super();
		subCategory = new ArrayList<Category>();
		items = new ArrayList<AbsShoppingItem>();
		typeGroups = new ArrayList<CategoryItemSpecialTypeGroup>();
	}
	
	
	public Category(ESCategory esCate) {
		this();
		this.id = esCate.getId();
		this.name = esCate.getName();
		this.parentId = esCate.getParentId();
		this.seq = esCate.getSeq();
	}
	
	
	public void addSubCategory(Category cate) {
		if (cate == null) {
			throw new RuntimeException(" category is null");
		}
		cate.updateParent(this);
		subCategory.add(cate);
	}
	
	public boolean removeSubCategory(ESCategory subCate) {
		return this.subCategory.remove(subCate);
	}
	
	public void updateParent(Category parent) {
		this.parent = parent;
		if (parent != null) {
			this.parentId = parent.getId();
		} else {
			this.parentId = 0;
		}
	}

	
	
	public Category getParent() {
		return parent;
	}

	public List<Category> getSubCategory() {
		return subCategory;
	}

	
	public void setSubCategory(List<Category> subCategory) {
		this.subCategory = subCategory;
	}

	
	public boolean getIsExistSubCategory() {
		return !(this.subCategory == null || this.subCategory.size() <= 0);
	}
	
	public Category getFirstSubCategory() {
		if (getIsExistSubCategory()) {
			return this.subCategory.get(0);
		} else {
			return null;
		}
	}
	
	
	public void addItem(AbsShoppingItem item) {
		this.items.add(item);
	}
	
	public void removeItem(AbsShoppingItem item) {
		this.items.remove(item);
	}
	
	
	public List<AbsShoppingItem> getItems() {
		return this.items;
	}
	
	
	public  List<AbsShoppingItem> getItemsIncludeSubCategory(int start, int fetchCount) {
		List<AbsShoppingItem> newList = new ArrayList<AbsShoppingItem>(fetchCount);
		int size = this.items.size();
		if (size > start) {
			int co = size - start;
			newList.addAll(items.subList(size - co, size));
			fetchCount -= co;
		} 
		
		Stack<Category> st = new Stack<Category>();
		populateStack(st, this);
		
		do {
			
			if (st.isEmpty()) {
				break;
			}
			Category subCategory = st.pop();
			if (subCategory == this) {
				break;
			}
			if (subCategory != null) {
				int itemSize = subCategory.getItems().size();
				if (itemSize < fetchCount) {
					newList.addAll(subCategory.getItems().subList(0, itemSize));
					fetchCount -= itemSize;
				} else {
					newList.addAll(subCategory.getItems().subList(0, fetchCount));
					break;
				}
			}
			
			
		} while (fetchCount > 0);
		
		return newList;
		
	}
	
	
	private void populateStack(Stack<Category> st, Category cate) {
		st.push(cate);
		for(int i = 0; i <cate.getSubCategory().size(); i++) {
			populateStack(st, cate.getSubCategory().get(i));
		}
	}
	
	public void addType(CategoryItemSpecialType type) {
		if (type == null) {
			return;
		}
		CategoryItemSpecialTypeGroup g = null;
		for (CategoryItemSpecialTypeGroup group : typeGroups) {
			if (type.getGroup().getId() == group.getId()) {
				g = group;
				break;
			}
		}
		if (g == null) {
			g = new CategoryItemSpecialTypeGroup();
			g.setId(type.getGroup().getId());
			g.setName(type.getGroupName());
			g.setShow(type.isShow());
			typeGroups.add(g);
		}
		
		g.addType(type);
	}
	
	public void removeType(CategoryItemSpecialType type) {
		if (type == null) {
			return;
		}
		
		CategoryItemSpecialTypeGroup g = null;
		
		for (CategoryItemSpecialTypeGroup group : typeGroups) {
			if (type.getGroup().getId() == group.getId()) {
				g = group;
				break;
			}
		}
		if (g == null) {
			return;
		}
		g.removeType(type);
	}
	
	
	public List<CategoryItemSpecialTypeGroup> getTypeGroups() {
		return this.typeGroups;
	}


	public boolean isExistSubCategory() {
		return !(this.subCategory == null || this.subCategory.size() == 0); 
	}


	public boolean isLoadCategorySpecificalType() {
		return isLoadCategorySpecificalType;
	}


	public void setLoadCategorySpecificalType(boolean isLoadCategorySpecificalType) {
		this.isLoadCategorySpecificalType = isLoadCategorySpecificalType;
	}
	
	
	

}
