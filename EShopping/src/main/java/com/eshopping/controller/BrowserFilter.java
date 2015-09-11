package com.eshopping.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter(urlPatterns = { "/index.xhtml" })
public class BrowserFilter implements Filter {


	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest hreq = (HttpServletRequest) request;
		SessionConfigBean configBean = (SessionConfigBean) hreq.getSession().getAttribute(
				"sessionConfigBean");
		if (configBean == null) {
			configBean = new SessionConfigBean();
			 hreq.getSession().setAttribute(
						"sessionConfigBean", configBean);
		}
		String userAgent = hreq.getHeader("User-Agent");
		if (userAgent.indexOf("Android") != -1 || userAgent.indexOf("Iphone") != -1) {
			configBean.setBrowserConfig(true);
		} else {
			configBean.setBrowserConfig(false);
		}
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}
	
	@Override
	public void destroy() {

	}

}
