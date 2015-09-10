package com.eshopping.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;


public class AJAXHandler extends HttpServlet {

	
	
	
	public AJAXHandler() {
		super();
		//service = new BreakfastBasicService(); 
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		handleRequest(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		handleRequest(req, resp);
	}

	
	
	private void handleRequest(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String action = req.getParameter("action");
		if ("addCart".equalsIgnoreCase(action)) {
			handleAddCart(req, resp);
		} else if ("sendValidationCode".equals(action)) {
			handleSendValidationCode(req, resp);
		}
		
	}
	
	
	private void handleAddCart(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		JSONObject ret = new JSONObject();
		HttpSession session = req.getSession();
		CartBean cartBean = (CartBean)session.getAttribute("cartBean");
		if (cartBean == null) {
			ret.put("errcode", 2);
			ret.put("msg", "No Bean");
		} else {
			try {
				long id = Long.parseLong((String)req.getParameter("bf_id"));
				int type = Integer.parseInt((String)req.getParameter("type"));
//				BreakfastWrapper wrp = service.findBreakfast(id, type == BreakfastWrapper.Type.SINGLE.ordinal()? BreakfastWrapper.Type.SINGLE : BreakfastWrapper.Type.COMBO);
//				cartBean.getCart().addBreakfast(wrp);
//				ret.put("errcode", 0);
				ret.put("total", cartBean.getTotalCount());
				
			} catch (NumberFormatException e) {
				e.printStackTrace();
				ret.put("errcode", 1);
				ret.put("msg", "id incorrect");
			}
		}
		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();
		out.print(ret.toString());
		out.flush();
		
	}
	
	
	private void handleSendValidationCode(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		JSONObject ret = new JSONObject();
		
		String cellphone = (String)req.getParameter("cellphone");
		//TODO send cell phone validation code;
		req.getSession().setAttribute("code", 1234);
		ret.put("errcode", 0);
		
		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();
		out.print(ret.toString());
		out.flush();
		
	}
	
}
