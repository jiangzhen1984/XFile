package com.eshopping.model.vo;

public class Image {
	
	public static final int TYPE_THUMBNIL = 1;
	public static final int TYPE_NORMAL = 2;
	public static final int TYPE_BANNER = 3;
	
	private long id;
	
	private String url;
	
	private int type;


	public Image(long id, String url, int type) {
		super();
		this.id = id;
		this.url = url;
		this.type = type;
	}

	public Image(String url, int type) {
		super();
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



	public int getType() {
		return type;
	}



	public void setType(int type) {
		this.type = type;
	}
	
	
	
	

}
