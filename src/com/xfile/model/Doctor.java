package com.xfile.model;

public class Doctor {
	
	private long id;
	
	private String name;
	
	private String department;
	
	private String description;
	
	

	public Doctor(long id, String name, String department) {
		super();
		this.id = id;
		this.name = name;
		this.department = department;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	

}
