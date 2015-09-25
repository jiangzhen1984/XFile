package com.eshopping.controller.management;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;

import com.eshopping.model.vo.Category;
import com.eshopping.model.vo.Image;
import com.eshopping.model.vo.SingleShoppingItem;
import com.eshopping.service.GlobalCache;
import com.eshopping.service.ServiceFactory;


@ManagedBean(name = "managementDataBean")
@SessionScoped
public class ManagementDataBean {
	
	
	private List<Category> cateGoryList;

	private String categoryName;
	
	
	
	public String getCategoryName() {
		return categoryName;
	}



	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	public List<Category> getTopCategoryList() {
		if (cateGoryList == null) {
			cateGoryList = GlobalCache.getInstance().getTopLevelCategoryList();
		}
		return cateGoryList;
	}



	public void createCategory() {
		Category parent = null;
		if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pid") != null) {
			Long pid = Long.parseLong((String)FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pid"));
			parent = GlobalCache.getInstance().getCategory(pid);
		}
		Category newCate = new Category();
		if (parent != null) {
			parent.addSubCategory(newCate);
		}
		
		newCate.setName(categoryName);
		ServiceFactory.getEShoppingService().addCategory(newCate);
		if (newCate.getParent() != null) {
			
		} else {
			GlobalCache.getInstance().addTopLevelCategory(newCate);
		}
		 GlobalCache.getInstance().putCategoryToCache(newCate);
		 
		 cateGoryList = GlobalCache.getInstance().getTopLevelCategoryList();
	}
	
	
	public void updateCategoryParent() {
		Category cate = null;
		if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("cid") != null) {
			Long pid = Long.parseLong((String)FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("cid"));
			cate = GlobalCache.getInstance().getCategory(pid);
		}
		
		Category newParent = null;
		if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pid") != null) {
			Long pid = Long.parseLong((String)FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pid"));
			newParent = GlobalCache.getInstance().getCategory(pid);
		}
		
		if (cate.getParent() != null) {
			cate.getParent().removeSubCategory(cate);
			cate.updateParent(null);
			if (newParent == null) {
				GlobalCache.getInstance().addTopLevelCategory(cate);
			}
		} else {
			 if (newParent != null){
				GlobalCache.getInstance().removeTopLevelCategory(cate);
				newParent.addSubCategory(cate);
			 }
		}
		
		
		ServiceFactory.getEShoppingService().updateCategory(cate);
		
		cateGoryList = GlobalCache.getInstance().getTopLevelCategoryList();
	}
	
	
	public void removeCategory() {
		Category cate = null;
		if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("cid") != null) {
			Long pid = Long.parseLong((String)FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("cid"));
			cate = GlobalCache.getInstance().getCategory(pid);
		}
		
		if (cate.getItems().size() > 0) {
			return;
		}
		
		if (cate.getParent() == null) {
			GlobalCache.getInstance().removeTopLevelCategory(cate);
		} else {
			cate.getParent().removeSubCategory(cate);
			cate.updateParent(null);
		}
		
		ServiceFactory.getEShoppingService().deleteCategory(cate);
		cateGoryList = GlobalCache.getInstance().getTopLevelCategoryList();

	}
	
	
	private Part banner;
	private Part thubnil1;
	private Part thubnil2;
	private Part thubnil3;
	private Part thubnil4;
	private Part thubnil5;
	private Part thubnil6;
	
	private Part normal1;
	private Part normal2;
	private Part normal3;
	private Part normal4;
	private Part normal5;
	
	private Part shopUrl;
	
	
	
	
	public Part getShopUrl() {
		return shopUrl;
	}



	public void setShopUrl(Part shopUrl) {
		this.shopUrl = shopUrl;
	}



	public void setCateGoryList(List<Category> cateGoryList) {
		this.cateGoryList = cateGoryList;
	}



	public void setBanner(Part banner) {
		this.banner = banner;
	}



	public void setThubnil1(Part thubnil1) {
		this.thubnil1 = thubnil1;
	}



	public void setThubnil2(Part thubnil2) {
		this.thubnil2 = thubnil2;
	}



	public void setThubnil3(Part thubnil3) {
		this.thubnil3 = thubnil3;
	}



	public void setThubnil5(Part thubnil5) {
		this.thubnil5 = thubnil5;
	}



	public void setThubnil6(Part thubnil6) {
		this.thubnil6 = thubnil6;
	}



	public void setNormal1(Part normal1) {
		this.normal1 = normal1;
	}



	public void setNormal2(Part normal2) {
		this.normal2 = normal2;
	}



	public void setNormal3(Part normal3) {
		this.normal3 = normal3;
	}



	public void setNormal4(Part normal4) {
		this.normal4 = normal4;
	}



	public void setNormal5(Part normal5) {
		this.normal5 = normal5;
	}


	private String[] cates;
	
	

	public void setCates(String[] cates) {
		this.cates = cates;
	}



	public Part getThubnil4() {
		return thubnil4;
	}



	public void setThubnil4(Part thubnil4) {
		this.thubnil4 = thubnil4;
	}



	public Part getBanner() {
		return banner;
	}



	public Part getThubnil1() {
		return thubnil1;
	}



	public Part getThubnil2() {
		return thubnil2;
	}



	public Part getThubnil3() {
		return thubnil3;
	}



	public Part getThubnil5() {
		return thubnil5;
	}



	public Part getThubnil6() {
		return thubnil6;
	}



	public Part getNormal1() {
		return normal1;
	}



	public Part getNormal2() {
		return normal2;
	}



	public Part getNormal3() {
		return normal3;
	}



	public Part getNormal4() {
		return normal4;
	}



	public Part getNormal5() {
		return normal5;
	}



	public String[] getCates() {
		return cates;
	}

	
	
	public void createCategorySpecialType() {
		Map<String, String> map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		Category cate = null;
		if (map.get("cid") != null) {
			Long pid = Long.parseLong(map.get("cid"));
			cate = GlobalCache.getInstance().getCategory(pid);
		}
		String groupName = map.get("groupName");
		String[] typeNames =  FacesContext.getCurrentInstance().getExternalContext().getRequestParameterValuesMap().get("typeName");
		
	}


	public void createShopItem() {
		String strPrice = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("price");
		String name = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("name");
		String summary = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("summary");
		String description = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("description");
		String[] cts = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterValuesMap().get("cates");
		cates = cts;
		List<Category> list = new ArrayList<Category>();
		SingleShoppingItem item = new SingleShoppingItem();
		for (int i = 0; cates!= null && i < cates.length; i++) {
			long cateId = 0;
			try {
				cateId = Long.parseLong(cates[i]);
			} catch(NumberFormatException e) {
				continue;
			}
			Category ct = GlobalCache.getInstance().getCategory(cateId);
			if (ct == null) {
				continue;
			}
			list.add(ct);
			item.addBelongCategory(ct);
			ct.addItem(item);
		}
		
		float price = 0.0F;
		try {
			price = Float.parseFloat(strPrice);
		}catch(NumberFormatException e) {
			return;
		}
		item.setPrice(price);
		item.setName(name);
		item.setDescription(description);
		item.setSummary(summary);
		

		if (this.banner != null && this.banner.getSize() > 0) {
			Image im = moveFile(this.banner, Image.TYPE_BANNER, 1);
			item.addImage(im);
			item.setBannerUrl(im.getUrl());
		}
		if (this.thubnil1 != null && this.thubnil1.getSize() > 0) {
			Image im = moveFile(this.thubnil1, Image.TYPE_THUMBNIL, 1);
			item.addImage(im);
		}
		if (this.thubnil2 != null && this.thubnil2.getSize() > 0) {
			Image im = moveFile(this.thubnil2, Image.TYPE_THUMBNIL, 2);
			item.addImage(im);
		}
		if (this.thubnil3 != null && this.thubnil3.getSize() > 0) {
			Image im = moveFile(this.thubnil3, Image.TYPE_THUMBNIL, 3);
			item.addImage(im);
		}
		if (this.normal1 != null && this.normal1.getSize() > 0) {
			Image im = moveFile(this.normal1, Image.TYPE_NORMAL, 1);
			item.addImage(im);
		}
		if (this.normal2 != null && this.normal2.getSize() > 0) {
			Image im = moveFile(this.normal2, Image.TYPE_NORMAL, 2);
			item.addImage(im);
		}
		
		if (this.shopUrl != null && this.shopUrl.getSize() > 0) {
			Image im = moveFile(this.shopUrl, Image.TYPE_LIST, 0);
			item.setShopShowUrl(im.getUrl());
		}
		
		

		item.setLoadImages(true);
		ServiceFactory.getEShoppingService().addShoppingItem(item, list);
		 GlobalCache.getInstance().putShoppingItem(item.getType(), item.getId(), item);
	}
	
	
	private Image moveFile(Part p, int type, int index) {
		File newFile = null;
		File old = null;
		String uri = null;
		try {
			Field f = p.getClass().getDeclaredField("fileItem");
			f.setAccessible(true);
			DiskFileItem file = (DiskFileItem)f.get(p);
			old = file.getStoreLocation().getAbsoluteFile();
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		//banner
		if (type == Image.TYPE_BANNER) {
			uri = "/image/" + System.currentTimeMillis()+"_banner_"+index+".png";
			newFile = new File(FacesContext
					.getCurrentInstance().getExternalContext().getRealPath("/")
					+ uri);
		} else if (type == Image.TYPE_NORMAL) {
			uri = "/image/" + System.currentTimeMillis()+"_normal_"+index+".png";
			newFile = new File(FacesContext
					.getCurrentInstance().getExternalContext().getRealPath("/")
					+ uri);
		} else if (type == Image.TYPE_THUMBNIL) {
			uri = "/image/" + System.currentTimeMillis()+"_thumbnil_"+index+".png";
			newFile = new File(FacesContext
					.getCurrentInstance().getExternalContext().getRealPath("/")
					+ uri);
		} else if (type == Image.TYPE_LIST) {
			uri = "/image/" + System.currentTimeMillis()+"_list_"+index+".png";
			newFile = new File(FacesContext
					.getCurrentInstance().getExternalContext().getRealPath("/")
					+ uri);
		}
		
		old.renameTo(newFile);
		Image im = new Image(uri, type);
		return im;
		
	}
}
