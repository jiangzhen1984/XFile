package com.eshopping.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.eshopping.model.vo.Address;
import com.eshopping.service.ESUserService;


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
		} else if ("addAddress".equals(action)) {
			handleAddAddress(req, resp);
		} 
		
	}
	
	
	private void handleAddCart(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		JSONObject ret = new JSONObject();
		HttpSession session = req.getSession();
		CheckoutDataBean cartBean = (CheckoutDataBean)session.getAttribute("cartBean");
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
	
	
	private void handleAddAddress(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String Country = (String)req.getParameter("Country");
		String State = (String)req.getParameter("State");
		String City = (String)req.getParameter("City");
		String Address = (String)req.getParameter("Address");
		String Receipt = (String)req.getParameter("Receipt");
		String PhoneNumber = (String)req.getParameter("PhoneNumber");
		
		int errcode = 0;
		if (Country == null || Country.isEmpty()) {
			errcode =1;
		} else if (State == null || State.isEmpty()) {
			errcode =2;
		} else if (City == null || City.isEmpty()) {
			errcode =3;
		} else if (Address == null || Address.isEmpty()) {
			errcode =4;
		} else if (Receipt == null || Receipt.isEmpty()) {
			errcode =5;
		} else if (PhoneNumber == null || PhoneNumber.isEmpty()) {
			errcode =6;
		}
		
		HttpSession session = req.getSession();
		SessionConfigBean sessionConfigBean = (SessionConfigBean)session.getAttribute("sessionConfigBean");
		if (sessionConfigBean == null || !sessionConfigBean.isLogin()) {
			errcode = -1;
		}
		long addrId = -1;
		if (errcode == 0) {
			ESUserService servcie = new ESUserService();
			Address addr = new Address();
			addr.setAddress(Address);
			addr.setCity(City);
			addr.setCountry(Country);
			addr.setName(Receipt);
			addr.setPhoneNumber(PhoneNumber);
			addr.setState(State);
			addr.setUser(sessionConfigBean.getUser());
			servcie.addAddress(addr);
			addrId = addr.getId();
		}
		
		JSONObject ret = new JSONObject();
		ret.put("errcode", errcode);
		ret.put("addrId", addrId);
		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();
		out.print(ret.toString());
		out.flush();
		
	}
	
}
