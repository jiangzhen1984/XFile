package com.todaybreakfast.controller.admin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

import com.todaybreakfast.model.Place;
import com.todaybreakfast.model.vo.BreakfastComboWrapper;
import com.todaybreakfast.model.vo.BreakfastSingleWrapper;
import com.todaybreakfast.model.vo.BreakfastWrapper;
import com.todaybreakfast.service.BreakfastBasicService;
import com.todaybreakfast.service.PlaceService;

@ManagedBean(name = "breakfastManagementBean", eager = false)
@ViewScoped
public class BreakfastManagement {

	private BreakfastBasicService service;

	private List<BreakfastWrapper> breakfastSingleList;
	private List<BreakfastWrapper> breakfastSingleCombo;

	private int type;
	private String name;
	private String desc;
	private float price;
	private String stuff;
	private int optType;
	private long[] comboFBList;
	
	//////
	private PlaceService plService;
	private String address;
	private String district;

	public BreakfastManagement() {
		service = new BreakfastBasicService();
		plService = new PlaceService();
	}

	public List<BreakfastWrapper> getBFList() {
		return service.getBreakfastList();
	}

	public List<BreakfastWrapper> getBFSingleList() {
		if (breakfastSingleList == null) {
			List<BreakfastWrapper> allList = getBFList();
			breakfastSingleList = new ArrayList<BreakfastWrapper>();
			for (int i = 0; i < allList.size(); i++) {
				BreakfastWrapper wr = allList.get(i);
				if (wr.getType() == BreakfastWrapper.Type.SINGLE) {
					breakfastSingleList.add(wr);
				}
			}
		}
		return breakfastSingleList;
	}

	public List<BreakfastWrapper> getBFComoboList() {
		if (breakfastSingleCombo == null) {
			List<BreakfastWrapper> allList = getBFList();
			breakfastSingleCombo = new ArrayList<BreakfastWrapper>();
			for (int i = 0; i < allList.size(); i++) {
				BreakfastWrapper wr = allList.get(i);
				if (wr.getType() == BreakfastWrapper.Type.COMBO) {
					breakfastSingleCombo.add(wr);
				}
			}
		}
		return breakfastSingleCombo;
	}

	public void forward(int type) {
		this.optType = type;
	}

	public int getOptType() {
		return optType;
	}

	public void setOptType(int optType) {
		this.optType = optType;
	}

	// ///==============for create new breakfast
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getStuff() {
		return stuff;
	}

	public void setStuff(String stuff) {
		this.stuff = stuff;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	private Part file;

	public Part getPicture() {
		return file;
	}

	public void setPicture(Part p) {
		this.file = p;
	}

	public long[] getComboFBList() {
		return comboFBList;
	}

	public void setComboFBList(long[] comboFBList) {
		this.comboFBList = comboFBList;
	}

	
	
	
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public void create() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		if (name == null || name.isEmpty()) {
			facesContext.addMessage("createBFForm:name", new FacesMessage(
					"请输入早餐名称"));
			return;
		}
		if (file != null && file.getSize() > 0) {
			String imgUrl = writeImage();
			if (imgUrl == null) {
				facesContext.addMessage("createBFForm:picture", new FacesMessage(
						"上传图片失败"));
				return;
			}
			BreakfastWrapper bw = new BreakfastSingleWrapper(price, name,
					imgUrl, stuff, desc);
			service.addBreakfastWrapper(bw);
			if (breakfastSingleList != null) {
				breakfastSingleList.add(bw);
			}
		} else {
			facesContext.addMessage("createBFForm:picture", new FacesMessage("请选择早餐图片!"));
		}
	}

	public void createCombo() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		if (comboFBList == null || comboFBList.length <= 0) {
			facesContext.addMessage("createComboBFForm:name", new FacesMessage(
					"请选择套餐组合"));
			return;
		}
		if (name == null || name.isEmpty()) {
			facesContext.addMessage("createComboBFForm:name", new FacesMessage(
					"请输入套餐餐名称"));
			return;
		}

		if (file != null && file.getSize() > 0) {
			String imgUrl = writeImage();
			if (imgUrl == null) {
				facesContext.addMessage("createComboBFForm:file", new FacesMessage(
						"上传图片失败"));
				return;
			}
			BreakfastComboWrapper bw = new BreakfastComboWrapper(price, name,
					imgUrl, stuff, desc);
			bw.addItems(getSelectedList());
			service.addBreakfastWrapper(bw);
			if (breakfastSingleCombo != null) {
				breakfastSingleCombo.add(bw);
			}
		} else {
			facesContext.addMessage("createComboBFForm:file", new FacesMessage("请选择早餐图片!"));
		}
	}
	
	private List<BreakfastSingleWrapper> getSelectedList() {
		List<BreakfastSingleWrapper> list = new ArrayList<BreakfastSingleWrapper>();
		for (int i = 0; i < comboFBList.length; i++) {
			List<BreakfastWrapper> singleList= getBFSingleList();
			for (int j = 0; j < singleList.size(); j++) {
				BreakfastWrapper  bw = singleList.get(j);
				if (bw.getId() == comboFBList[i]) {
					list.add((BreakfastSingleWrapper)bw);
				}
			}
		}
		
		return list;
	}
	
	
	public void deleteBF(long id) {
		List<BreakfastWrapper> comboList = getBFComoboList();
		for (int i = 0; i < comboList.size(); i++) {
			BreakfastComboWrapper bw = (BreakfastComboWrapper)comboList.get(i);
			if (bw.findItem(id) != null) {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				facesContext.addMessage("form_delete", new FacesMessage("请先删除 套餐:" + bw.getName()));
				return;
			}
		}
		
		BreakfastWrapper single = null;
		List<BreakfastWrapper> singleList= getBFSingleList();
		for (int i = 0; i < singleList.size(); i++) {
			BreakfastWrapper  bw = singleList.get(i);
			if (bw.getId() == id) {
				single = bw;
				break;
			}
		}
		if (single != null) {
			if (breakfastSingleList != null) {
				breakfastSingleList.remove(single);
			}
			service.removeBreakfastWrapper(single);
		} else {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.addMessage("form_delete", new FacesMessage("没有找到早餐"));
		}
		
	}
	
	public void deleteComboBF(long id) {
		BreakfastWrapper combo = null;
		List<BreakfastWrapper> comboList = getBFComoboList();
		for (int i = 0; i < comboList.size(); i++) {
			BreakfastComboWrapper bw = (BreakfastComboWrapper)comboList.get(i);
			if (bw.getId() == id) {
				combo = bw;
				break;
			}
		}
		
		if (combo != null) {
			if (breakfastSingleCombo != null) {
				breakfastSingleCombo.remove(combo);
			}
			
			service.removeBreakfastWrapper(combo);
		} else {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.addMessage("form_delete", new FacesMessage("没有找到套餐"));
		}
	}

	private String writeImage() {
		String imgUrl = null;
		String imgName = System.currentTimeMillis() + ".png";
		OutputStream out = null;
		InputStream in = null;
		try {
			out = new FileOutputStream(new File(FacesContext
					.getCurrentInstance().getExternalContext().getRealPath("/")
					+ "/image/" + imgName));
			byte[] buf = new byte[4096];
			int len = 0;

			in = file.getInputStream();
			while ((len = in.read(buf, 0, 4096)) > 0) {
				out.write(buf, 0, len);
			}

			imgUrl = "image/" + imgName;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return imgUrl;

	}
	
	
	
	public void createPlace() {
		if (address == null || address.isEmpty()) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.addMessage("address", new FacesMessage("没有输入地址"));
			return;
		}
		if (district == null || district.isEmpty()) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.addMessage("address", new FacesMessage("没有输入地区"));
			return;
		}
		
		Place newPlace = new Place();
		newPlace.setAddress(address);
		newPlace.setDistrict(district);
		plService.addPlace(newPlace);
	}
	
	
	public void deletePlace(long id) {
		plService.removePlace(id);
	}

}
