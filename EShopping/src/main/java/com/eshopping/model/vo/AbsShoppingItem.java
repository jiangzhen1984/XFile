package com.eshopping.model.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public abstract class AbsShoppingItem {

	public static final int TYPE_SINGLE = 1;
	public static final int TYPE_COMBO = 2;
	
	protected long id;

	protected float price;

	protected String name;

	protected String stuff;

	protected String description;
	
	protected String summary;
	
	protected int type;
	
	protected List<Category> categorys;
	
	protected boolean isSale;
	
	protected boolean isNew;
	
	protected boolean isRecommand;
	
	protected boolean isHot;
	
	protected float rank;
	
	protected int counts;
	
	protected int saledCounts;
	
	protected Date stockDate;
	
	protected List<CategoryItemSpecialType> specialTypeList;
	
	protected boolean loadImages;
	
	protected List<Image> imageList;
	
	public AbsShoppingItem(int type) {
		super();
		this.type = type;
		categorys = new ArrayList<Category>();
		specialTypeList = new ArrayList<CategoryItemSpecialType>();
		imageList = new ArrayList<Image>();
	}
	
	
	public void addBelongCategory(Category cate) {
		if (cate == null) {
			throw new NullPointerException("Category can not be null");
		}
		this.categorys.add(cate);
	}
	
	public boolean removeBelongCategory(Category item) {
		return categorys.remove(item);
	}
	
	
	public Category removeBelongCategory(int index) {
		return categorys.remove(index);
	}
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public float getPrice() {
		return this.price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStuff() {
		return stuff;
	}

	public void setStuff(String stuff) {
		this.stuff = stuff;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	



	public String getSummary() {
		return summary;
	}


	public void setSummary(String summary) {
		this.summary = summary;
	}


	public int getType() {
		return type;
	}


	public boolean isSale() {
		return isSale;
	}


	public void setSale(boolean isSale) {
		this.isSale = isSale;
	}


	public boolean isNew() {
		return isNew;
	}


	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}


	public boolean isRecommand() {
		return isRecommand;
	}


	public void setRecommand(boolean isRecommand) {
		this.isRecommand = isRecommand;
	}


	public boolean isHot() {
		return isHot;
	}


	public void setHot(boolean isHot) {
		this.isHot = isHot;
	}


	public void setType(int type) {
		this.type = type;
	}
	
	
	public void addSpecialType(CategoryItemSpecialType sType) {
		this.specialTypeList.add(sType);
	}
	
	public void removeSpecialType(CategoryItemSpecialType sType) {
		this.specialTypeList.remove(sType);
	}
	
	public List<CategoryItemSpecialType> getSpecialTypeList() {
		return this.specialTypeList;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + type;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbsShoppingItem other = (AbsShoppingItem) obj;
		if (type != other.type)
			return false;
		return true;
	}


	public boolean isLoadImages() {
		return loadImages;
	}


	public void setLoadImages(boolean loadImages) {
		this.loadImages = loadImages;
	}


	public List<Image> getImageList() {
		return imageList;
	}


	public void addImage(Image image) {
		if (image == null) {
			return;
		}
		this.imageList.add(image);
	}
	
	public void removeImage(Image image) {
		this.imageList.remove(image);
	}

	
	

}
