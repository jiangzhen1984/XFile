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

import com.todaybreakfast.model.vo.BreakfastComboWrapper;
import com.todaybreakfast.model.vo.BreakfastSingleWrapper;
import com.todaybreakfast.model.vo.BreakfastWrapper;
import com.todaybreakfast.service.BreakfastBasicService;

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

	public BreakfastManagement() {
		service = new BreakfastBasicService();
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
				facesContext.addMessage("createBFForm:file", new FacesMessage(
						"上传图片失败"));
				return;
			}
			BreakfastWrapper bw = new BreakfastSingleWrapper(price, name,
					imgUrl, stuff, desc);
			service.addBreakfastWrapper(bw);
		} else {
			facesContext.addMessage("file", new FacesMessage("请选择早餐图片!"));
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
			for (int i = 0; i < comboFBList.length; i++) {
				bw.addItem(new BreakfastSingleWrapper(comboFBList[i]));
			}
			service.addBreakfastWrapper(bw);
		} else {
			facesContext.addMessage("file", new FacesMessage("请选择早餐图片!"));
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

}
