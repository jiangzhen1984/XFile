package com.eshopping.controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.eshopping.model.po.Order.OrderState;
import com.eshopping.model.po.User;
import com.eshopping.service.OrderService;
import com.eshopping.service.UserService;



@ManagedBean(name="userBean", eager=true)
@SessionScoped
public class UserBean {
	
	private String cellphone;
	private String password;
	private String cellphoneCode;
	private String confirmPassword;
	private String name;
	private String mail;
	
	private User user;
	private UserService userService;
	private String route;
	
	
	boolean queryed;
	private int unPaidOrderCount;
	private int paidOrderCount;
	private int finishedOrderCount;
	
	public UserBean() {
		userService = new UserService();
	}
	
	public String getCellphone() {
		return cellphone;
	}
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCellphoneCode() {
		return cellphoneCode;
	}

	public void setCellphoneCode(String cellphoneCode) {
		this.cellphoneCode = cellphoneCode;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	
	
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public User getLoggedInUser() {
		return this.user;
	}
	
	
	
	public int getUnPaidOrderCount() {
		if (!queryed) {
			queryCount();
		}
		return unPaidOrderCount;
	}

	public int getPaidOrderCount() {
		if (!queryed) {
			queryCount();
		}
		return paidOrderCount;
	}

	public int getFinishedOrderCount() {
		if (!queryed) {
			queryCount();
		}
		return finishedOrderCount;
	}
	
	
	private void queryCount() {
		OrderService service = new OrderService();
		unPaidOrderCount = service.getUserOrderCount(getLoggedInUser(), OrderState.NOT_PAIED);
		paidOrderCount = service.getUserOrderCount(getLoggedInUser(), OrderState.PAIED);
		finishedOrderCount = service.getUserOrderCount(getLoggedInUser(), OrderState.COMPLETED);
		
		queryed = true;
	}

	public String getRoute() {
		String httpGetRoute = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("route");
		if (httpGetRoute != null) {
			return httpGetRoute;
		} else {
			return route;
		}
	}

	public void setRoute(String route) {
		this.route = route;
	}
	
	public String login() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
		if (cellphone == null || cellphone.isEmpty()) {
			facesContext.addMessage("cellphone", new FacesMessage("请输入手机号"));
			return "failed";
		}
		if (password == null || password.isEmpty()) {
			facesContext.addMessage("password", new FacesMessage("请输入密码"));
			return "failed";
		}
		user = userService.selectUser(cellphone, password);
		if (user == null) {
			facesContext.addMessage("cellphone", new FacesMessage("用户名或密码错误"));
			return "failed";
		}
		if (route == null || route.isEmpty()) {
			return "list";
		} else {
			return route;
		}
	}
	
	
	public String register() {
		FacesContext facesContext = FacesContext.getCurrentInstance();

		if (name == null || name.isEmpty()) {
			facesContext.addMessage("name", new FacesMessage("请确认姓名"));
			return "failed";
		}
		
		if (this.cellphone == null || this.cellphone.isEmpty()) {
			facesContext.addMessage("cellphone", new FacesMessage("请输入手机号"));
			return "failed";
			
		}
		if (password == null || password.isEmpty()) {
			facesContext.addMessage("password", new FacesMessage("请输入密码"));
			return "failed";
		}
		if (confirmPassword == null || confirmPassword.isEmpty()) {
			facesContext.addMessage("confirmPassword", new FacesMessage("请确认密码"));
			return "failed";
		}

		if (cellphoneCode == null || cellphoneCode.isEmpty()) {
			facesContext.addMessage("cellphoneCode", new FacesMessage("请输入验证码"));
			return "failed";
		}
		if (!password.equals(confirmPassword)) {
			facesContext.addMessage("confirmPassword", new FacesMessage("两次密码输入的不一致"));
			return "failed";
		}
		Integer code = (Integer) facesContext.getExternalContext().getSessionMap().get("code");
		if (!cellphoneCode.equals(code+"")) {
			facesContext.addMessage("cellphoneCode", new FacesMessage("验证码不匹配"));
			return "failed";
		}
		
		User newUser = new User();
		newUser.setName(name);
		newUser.setCellPhone(cellphone);
		newUser.setPassword(password);
		newUser.setMail(mail);
		
		int ret = userService.addUser(newUser);
		if (ret == 0) {
			this.user = newUser;
		} else {
			facesContext.addMessage("cellphoneCode", new FacesMessage("手机号已经被注册"));
			return "failed";
		}
		return "personel";
	}
	
	
	public String logout() {
		user = null;
		return "logout";
	}
	

}
