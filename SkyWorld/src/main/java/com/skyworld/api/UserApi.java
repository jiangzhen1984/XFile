package com.skyworld.api;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.skyworld.service.ServiceFactory;
import com.skyworld.service.dsf.User;
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
							response.append("{ret: 0, token:\""+System.currentTimeMillis()+"\"}");
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
						response.append("{ret: 0, token:\""+System.currentTimeMillis()+"\"}");
					}
				}
			} else {
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
