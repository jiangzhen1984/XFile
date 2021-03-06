package com.todaybreakfast.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table( name = "TB_USER" )
public class User {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(name="CELL_PHONE", columnDefinition="VARCHAR(40)")
	private String cellPhone;
	
	@Column(name="USER_PWD", columnDefinition="VARCHAR(40)")
	private String password;
	
	@Column(name="NAME", columnDefinition="VARCHAR(40)")
	private String name;
	
	@Column(name="ADDRESS", columnDefinition="VARCHAR(200)")
	private String address;
	
	@Column(name="MAIL", columnDefinition="VARCHAR(100)")
	private String mail;
	
	
	public User() {
		
	}
	
	public User(User u) {
		this.setAddress(u.getAddress());
		this.setCellPhone(u.getCellPhone());
		this.setName(u.getName());
		this.setId(u.getId());
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	
	
	
	
	

}
