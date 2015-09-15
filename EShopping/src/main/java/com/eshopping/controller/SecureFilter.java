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
import javax.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = {"/personel/addressselection.xhtml", "/checkout.xhtml" })
public class SecureFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest hreq = (HttpServletRequest) request;
		SessionConfigBean userBean = (SessionConfigBean) hreq.getSession().getAttribute(
				"sessionConfigBean");
		if (userBean == null || !userBean.isLogin()) {
			((HttpServletResponse) response).sendRedirect(hreq.getContextPath()

			+ "/login.xhtml?route=" + hreq.getServletPath());
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
