package com.eshopping.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.eshopping.model.po.User;
import com.eshopping.service.ESUserService;

@ManagedBean(name = "loginDataBean", eager = true)
@RequestScoped
public class LoginDataBean {
	
	private String userName;
	
	private String password;
	
	private String userNameErr;
	
	private String passwordErr;
	
	private String commonErr;

	public LoginDataBean() {
	}

	
	
	
	public String getUserName() {
		return userName;
	}




	public void setUserName(String userName) {
		this.userName = userName;
	}




	public String getPassword() {
		return password;
	}




	public void setPassword(String password) {
		this.password = password;
	}


	


	public String getUserNameErr() {
		return userNameErr;
	}




	public String getPasswordErr() {
		return passwordErr;
	}




	public String getCommonErr() {
		return commonErr;
	}




	public String login() {
		if (userName == null || userName.trim().isEmpty()) {
			userNameErr = "请输入用户名";
			return "failed";
		}
		if (password == null || password.isEmpty()) {
			passwordErr = "请输入密码";
			return "failed";
		}
		
		ESUserService service = new ESUserService();
		
		User user = service.selectUser(userName, userName, password);
		if (user == null) {
			commonErr = "用户名或密码不正确";
			return "fail";
		} else {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			SessionConfigBean configBean = (SessionConfigBean)facesContext.getExternalContext().getSessionMap().get("sessionConfigBean");
			configBean.setLogin(true);
			//TODO add User to config
			return "personel";
		}
	}
}
