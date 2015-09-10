package com.eshopping.model.vo;

import java.util.ArrayList;
import java.util.List;

import com.eshopping.model.po.ESCategory;



public class Category extends ESCategory {
	
	protected Category parent;
	
	protected List<Category> subCategory;

	public Category() {
		subCategory = new ArrayList<Category>();
	}
	
	
	public Category(ESCategory esCate) {
		subCategory = new ArrayList<Category>();
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

	
	
	public ESCategory getParent() {
		return parent;
	}

	public List<Category> getSubCategory() {
		return subCategory;
	}

	
	public void setSubCategory(List<Category> subCategory) {
		this.subCategory = subCategory;
	}


}
