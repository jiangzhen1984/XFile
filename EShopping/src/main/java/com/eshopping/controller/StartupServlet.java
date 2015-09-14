package com.eshopping.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.eshopping.service.CacheLoader;


public class StartupServlet extends HttpServlet {

	
	
	
	public StartupServlet() {
		super();
	}

	@Override
	public void destroy() {
		super.destroy();
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	@Override
	public void init() throws ServletException {
		super.init();
		InputStream in = null;
		
		in = this.getClass().getResourceAsStream("../../../env.prop");
		Properties prop = new Properties();
		try {
			if (in != null) {
				prop.load(in);
			}
			String  ctx = (String)prop.get("context");
			if (ctx != null) {
				GlobalBean.GLOBAL_CONTEXT = ctx;
			}
			String  host = (String)prop.get("host");
			if (host != null) {
				GlobalBean.GLOBAL_HOST = host;
			}
			String  imageHost = (String)prop.get("image_host");
			if (imageHost != null) {
				GlobalBean.GLOBAL_IMAGE_HOST = imageHost;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}  finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		new CacheLoader().init();
	}
	
	
	
}
