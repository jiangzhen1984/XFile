package com.eshopping.controller;

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
		new CacheLoader().init();
	}
	
	
	
}
