package com.eshopping.model.vo;

public class Image {
	
	public static final int TYPE_THUMBNIL = 1;
	public static final int TYPE_SOURCE = 2;
	
	private long id;
	
	private String url;
	
	private int type;

	public Image(long id, String url) {
		super();
		this.id = id;
		this.url = url;
	}
	
	

	public Image(long id, String url, int type) {
		super();
		this.id = id;
		this.url = url;
		this.type = type;
	}



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
	

}
