package com.skyworld.api;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.skyworld.cache.CacheManager;
import com.skyworld.cache.Token;
import com.skyworld.cache.TokenFactory;
import com.skyworld.push.event.ConnectionCloseEvent;
import com.skyworld.service.ServiceFactory;
import com.skyworld.service.dsf.Customer;
import com.skyworld.service.dsf.SKServicer;
import com.skyworld.service.dsf.User;
import com.skyworld.service.dsf.UserType;
import com.skyworld.utils.JSONFormat;

public class UserApi extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		checkJson(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		checkJson(req, resp);
	}
	
	
	private void checkJson(HttpServletRequest req, HttpServletResponse resp) {
		String data = req.getParameter("data");
		StringBuffer response = new StringBuffer();
		if (data == null) {
			response.append("{ret: -1}"); 
		}  else {
			Map<String, JSONObject> map = JSONFormat.parse(data);
			String action = "";
			try {
				action= (String)map.get("header").get("action");
			} catch(Exception e) {
				e.printStackTrace();
			}
			if ("register".equalsIgnoreCase(action)) {
				boolean error = false;
				JSONObject body = map.get("body");
				if (body == null) {
					error = true;
					response.append("{ret: -3}");
				}
				if (!error) {
					if (!body.has("username") || !body.has("cellphone") || !body.has("pwd")|| !body.has("confirm_pwd")) {
						response.append("{ret: -3}");
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
						response.append("{ret: -3}");
						error = true;
					}
				}
				if (!error && !pwd.equals(confirmPwd)) {
					response.append("{ret: -102}");
					error = true;
				}
				
				if (!error){
					User user = ServiceFactory.getESUserService().selectUser(cellphone, uname);
					if (user != null) {
						response.append("{ret: -101}");
					} else {
						user = new User();
						user.setCellPhone(cellphone);
						user.setMail(uname);
						user.setPassword(pwd);
						int ret = ServiceFactory.getESUserService().addUser(user);
						if (ret == 0) {
							Token token = CacheManager.getIntance().saveUser(new Customer(user));
							response.append("{ret: 0, token:\""
									+ token.getValue()
									+ "\",  user: {\"name\" : \""
									+ user.getName() + "\", \"cellphone\", :\""
									+ user.getAddress() + "\", \"mail\":\""
									+ user.getMail() + "\", \"type\":"
									+ user.getUserType().ordinal() + " }}");

							ServiceFactory.getEaseMobService().register(user.getMail(), user.getPassword());
						} else {
							response.append("{ret: -103}");
						}
					}
				}
				
			} else if ("login".equalsIgnoreCase(action)) {
				boolean error = false;
				JSONObject body = map.get("body");
				String uname = (String)body.get("username");
				String pwd = (String)body.get("pwd");
				if (uname == null || uname.isEmpty()||pwd == null || pwd.isEmpty()) {
					response.append("{ret: -3}");
					error = true;
				}
				if (!error){
					User user = ServiceFactory.getESUserService().selectUser(uname, uname, pwd);
					if (user == null) {
						response.append("{ret: -201}");
					} else {
						if (user.getUserType() == UserType.CUSTOMER) {
							user = new Customer(user);
						} else if (user.getUserType() == UserType.SERVICER) {
							user = new SKServicer(user);
						}
						Token token = CacheManager.getIntance().saveUser(user);
						response.append("{ret: 0, token:\"" + token.getValue()
								+ "\",  user: {\"name\" : \"" + user.getName()
								+ "\", \"cellphone\", :\"" + user.getAddress()
								+ "\", \"mail\":\"" + user.getMail()
								+ "\" , \"type\":"
								+ user.getUserType().ordinal() + " }}");
					}
				}
			}else if ("logout".equalsIgnoreCase(action)) {
				boolean error = false;
				String tokenId = null;
				JSONObject header = map.get("header");
				if (header == null) {
					response.append("{ret: -3}");
					error = true;
				}
				
				if (!error) {
					try {
						tokenId = header.getString("token");
						if (tokenId == null || tokenId.trim().isEmpty()) {
							response.append("{ret: 401}");
							error = true;
						}
					} catch (Exception e) {
						e.printStackTrace();
						response.append("{ret: -3}");
						error = true;
					}
				}
				
				if (!error) {
					Token token = TokenFactory.valueOf(tokenId);
					User user = CacheManager.getIntance().removeUser(token);
					if (user != null && user.getPushTerminal() != null) {
						user.getPushTerminal().postEvents(new ConnectionCloseEvent());
					}
				}
				
				response.append("{ret: 0}"); 
			}
			else {
				response.append("{ret: -2}"); 
			}
		}
		try {
			resp.setContentType("application/json");
			resp.getWriter().write(response.toString());
			req.getSession().invalidate();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	

}
