package com.skyworld.api;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;

import com.skyworld.cache.CacheManager;
import com.skyworld.cache.Token;
import com.skyworld.cache.TokenFactory;
import com.skyworld.easemob.EasemobRegisterCallback;
import com.skyworld.push.event.ConnectionCloseEvent;
import com.skyworld.push.event.MessageEvent;
import com.skyworld.pushimpl.EasemobMessage;
import com.skyworld.service.ServiceFactory;
import com.skyworld.service.dsf.Customer;
import com.skyworld.service.dsf.SKServicer;
import com.skyworld.service.dsf.User;
import com.skyworld.service.dsf.UserType;
import com.skyworld.utils.JSONFormat;

public class UserApi extends HttpServlet {

	
	Log log = LogFactory.getLog(this.getClass());
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ServiceFactory.getAPIService().service(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ServiceFactory.getAPIService().service(req, resp);
	}
	
	
	private void checkJson(HttpServletRequest req, HttpServletResponse resp) {
		String data = req.getParameter("data");
		log.info("====> data: " + data);
		StringBuffer response = new StringBuffer();
		if (data == null) {
			response.append("{\"ret\": -1}"); 
		}  else {
			Map<String, JSONObject> map = JSONFormat.parse(data);
			String action = "";
			try {
				action= (String)map.get("header").get("action");
			} catch(Exception e) {
				e.printStackTrace();
			}
			if ("register".equalsIgnoreCase(action)) {
				response.append(doRegister(map.get("body")));
			} else if ("login".equalsIgnoreCase(action)) {
				response.append(doLogin(map.get("body")));
			} else if ("logout".equalsIgnoreCase(action)) {
				response.append(doLogout(map.get("header")));
			}else if ("upgrade".equalsIgnoreCase(action)) {
				response.append(doUpgrade(map.get("header"), map.get("body")));
			} else {
				response.append("{\"ret\": -2}");
			}
		}
		try {
			resp.setHeader("connection", "close");
			resp.setContentType("application/json");
			resp.setContentLength(response.length());
			resp.getWriter().write(response.toString());
			HttpSession sess = req.getSession(false);
			if (sess != null) {
				sess.invalidate();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	private String doLogin(JSONObject body) {
		StringBuffer response = new StringBuffer();
		boolean error = false;
		String uname = (String)body.get("username");
		String pwd = (String)body.get("pwd");
		if (uname == null || uname.isEmpty()||pwd == null || pwd.isEmpty()) {
			response.append("{\"ret\": -3}");
			error = true;
		}
		if (!error){
			User user = ServiceFactory.getESUserService().selectUser(uname, uname, pwd);
			if (user == null) {
				response.append("{\"ret\": -201}");
			} else {
				if (user.getUserType() == UserType.CUSTOMER) {
					user = new Customer(user);
				} else if (user.getUserType() == UserType.SERVICER) {
					user = new SKServicer(user);
				}
				Token token = CacheManager.getIntance().saveUser(user);
				response.append("{\"ret\": 0, token:\"" + token.getValue()
						+ "\",  \"user\": {\"name\" : \"" + user.getName()
						+ "\", \"cellphone\": \"" + user.getCellPhone()
						+ "\", \"mail\":\"" + user.getMail()
						+ "\" , \"type\":"
						+ user.getUserType().ordinal() + " }}");
			}
		}
		
		return response.toString();
	}
	
	
	private String doRegister(JSONObject body) {
		StringBuffer response = new StringBuffer();
		boolean error = false;
		if (body == null) {
			error = true;
			response.append("{\"ret\": -3}");
		}
		if (!error) {
			if (!body.has("username") || !body.has("cellphone") || !body.has("pwd")|| !body.has("confirm_pwd")) {
				response.append("{\"ret\": -3}");
				error = true;
			}
		}
		String uname = null;
		String cellphone = null;
		String pwd = null;
		String confirmPwd = null;
		
		if (!error) {
			 uname = (String)body.getString("username");
			 cellphone = (String)body.getString("cellphone");
			 pwd = (String)body.getString("pwd");
			 confirmPwd = (String)body.getString("confirm_pwd");
			if (uname == null || uname.isEmpty() || pwd == null
					|| pwd.isEmpty() || confirmPwd == null
					|| confirmPwd.isEmpty() || cellphone == null
					|| cellphone.isEmpty()) {
				response.append("{\"ret\": -3}");
				error = true;
			}
		}
		if (!error && !pwd.equals(confirmPwd)) {
			response.append("{\"ret\": -102}");
			error = true;
		}
		
		if (!error){
			User user = ServiceFactory.getESUserService().selectUser(cellphone, uname);
			if (user != null) {
				response.append("{\"ret\": -101}");
			} else {
				user = new User();
				user.setCellPhone(cellphone);
				user.setMail(uname);
				user.setPassword(pwd);
				int ret = ServiceFactory.getESUserService().addUser(user);
				if (ret == 0) {
					Token token = CacheManager.getIntance().saveUser(new Customer(user));
					response.append("{\"ret\": 0, \"token\" :\""
							+ token.getValue()
							+ "\",  \"user\": {\"name\" : \""
							+ user.getName() + "\", \"cellphone\": \""
							+ user.getCellPhone() + "\", \"mail\":\""
							+ user.getMail() + "\", \"type\":"
							+ user.getUserType().ordinal() + " }}");

					final User u = user;
					ServiceFactory.getEaseMobService().register(cellphone, user.getPassword(), new EasemobRegisterCallback() {

						@Override
						public void onRegistered() {
							u.getPushTerminal().postEvent(new MessageEvent(new EasemobMessage(u)));
						}

						@Override
						public void onFailed() {
							u.getPushTerminal().postEvent(new MessageEvent(new EasemobMessage(u)));
						}

						@Override
						public void onError() {
							u.getPushTerminal().postEvent(new MessageEvent(new EasemobMessage(u)));
						}
						
					});
				} else {
					response.append("{\"ret\": -103}");
				}
			}
		}
		return response.toString();
	}
	
	
	
	private String doLogout(JSONObject header) {
		StringBuffer response = new StringBuffer();
		boolean error = false;
		String tokenId = null;
		if (header == null) {
			response.append("{\"ret\": -3}");
			error = true;
		}
		
		if (!error) {
			try {
				tokenId = header.getString("token");
				if (tokenId == null || tokenId.trim().isEmpty()) {
					response.append("{\"ret\": -401}");
					error = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
				response.append("{\"ret\": -3}");
				error = true;
			}
		}
		
		if (!error) {
			Token token = TokenFactory.valueOf(tokenId);
			User user = CacheManager.getIntance().removeUser(token);
			if (user != null && user.getPushTerminal() != null) {
				user.getPushTerminal().postEvent(new ConnectionCloseEvent());
			}
		}
		
		response.append("{\"ret\": 0}"); 
		return response.toString();
	}
	
	
	
	private String doUpgrade(JSONObject header, JSONObject body) {
		StringBuffer response = new StringBuffer();
		boolean error = false;
		String tokenId = null;
		try {
			tokenId = header.getString("token");
			if (tokenId == null || tokenId.trim().isEmpty()) {
				response.append("{\"ret\": -5}");
				error = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.append("{\"ret\": -4}");
			error = true;
		}
		
		
		if (!error) {
			Customer cus = CacheManager.getIntance().getCustomer(TokenFactory.valueOf(tokenId));
			if (cus != null) {
				SKServicer servicer = new SKServicer((User)cus);
				boolean ret = ServiceFactory.getESUserService().updradeUserToSKServicer(servicer);
				if (ret) {
					Token newToken = CacheManager.getIntance().saveUser(servicer);
					response.append("{\"ret\": 0, \"token\":\""
							+ newToken
							+ "\",  \"user\": {\"name\" : \""
							+ servicer.getName() + "\", \"cellphone\": \""
							+ servicer.getCellPhone() + "\", \"mail\":\""
							+ servicer.getMail() + "\", \"type\":"
							+ servicer.getUserType().ordinal() + " }}");
				} else {
					response.append("{\"ret\": -501}");
				}
			}
			
		}
		return response.toString();
	}

}
