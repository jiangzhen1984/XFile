package com.eshopping.controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.eshopping.model.vo.User;
import com.eshopping.service.ESUserService;


@ManagedBean(name = "registerDataBean")
@RequestScoped
public class RegisterDataBean {
	
	
	private static String NAME_DEFAULT = "User Name";
	private static String MAIL_DEFAULT = "Email";
	private static String PASS_DEFAULT = "Password";
	private static String CITY_DEFAULT = "User Name";
	
	
	private String name;
	private String mail;
	private String passwd;
	private String passwdConfirm;
	private String country;
	private String city;
	private String countryCode;
	private String phone;

	public RegisterDataBean() {
		name = "User Name";
		mail = "Email";
		passwd = "Password";
		city = "City";
		passwdConfirm = "Password Confirm";
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPasswdConfirm() {
		return passwdConfirm;
	}

	public void setPasswdConfirm(String passwdConfirm) {
		this.passwdConfirm = passwdConfirm;
	}
	
	
	String nameError;
	String phoneError;
	String passError;
	String mailError;
	
	public String getNameErrorMessage() {
		return nameError;
	}
	
	public String getPhoneErrorMessage() {
		return phoneError;
	}
	

	public String getPasswordErrorMessage() {
		return passError;
	}

	public String getMailErrorMessage() {
		return mailError;
	}
	
	public String register() {
		FacesContext facesContext = FacesContext.getCurrentInstance();

		if (name == null || name.isEmpty() || NAME_DEFAULT.equals(name)) {
			nameError = "请确认姓名";
			facesContext.addMessage("name", new FacesMessage("请确认姓名"));
			return "failed";
		}
		
		if (this.phone == null || this.phone.isEmpty()) {
			phoneError = "请输入手机号";
			facesContext.addMessage("phone", new FacesMessage("请输入手机号"));
			return "failed";
			
		}
		if (passwd == null || passwd.isEmpty() || PASS_DEFAULT.equals(passwd)) {
			passError = "请输入密码";
			facesContext.addMessage("password", new FacesMessage("请输入密码"));
			return "failed";
		}
		if (passwdConfirm == null || passwdConfirm.isEmpty()) {
			passError = "请输入确认密码";
			facesContext.addMessage("confirmPassword", new FacesMessage("请输入确认密码"));
			return "failed";
		}
		
		if (!passwd.equals(passwdConfirm)) {
			passError = "两次密码输入的不一致";
			facesContext.addMessage("confirmPassword", new FacesMessage("两次密码输入的不一致"));
			return "failed";
		}

		if (this.mail == null || this.mail.isEmpty() || MAIL_DEFAULT.equals(mail)) {
			mailError = "请输入邮箱";
			facesContext.addMessage("mail", new FacesMessage("请输入邮箱"));
			return "failed";
			
		}
		
		User newUser = new User();
		newUser.setName(name);
		newUser.setCellPhone(phone);
		newUser.setPassword(passwd);
		newUser.setMail(mail);
		ESUserService userService = new ESUserService();
		int ret = userService.addUser(newUser);
		if (ret!= 0) {
			phoneError = "手机号或邮箱已经被注册";
			facesContext.addMessage("phone", new FacesMessage("手机号已经被注册"));
			return "failed";
		}
		SessionConfigBean configBean = (SessionConfigBean)facesContext.getExternalContext().getSessionMap().get("sessionConfigBean");
		configBean.setLogin(true);
		//TODO add user
		return "personel";
	}

	
	
	
}
