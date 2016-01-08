package com.skyworld.service;

import org.json.JSONObject;

import com.skyworld.cache.CacheManager;
import com.skyworld.cache.Token;
import com.skyworld.easemob.EasemobRegisterCallback;
import com.skyworld.push.event.MessageEvent;
import com.skyworld.pushimpl.EasemobMessage;
import com.skyworld.service.dsf.Customer;
import com.skyworld.service.dsf.User;
import com.skyworld.service.resp.BasicResponse;
import com.skyworld.service.resp.RTCodeResponse;
import com.skyworld.service.resp.RegisterResponse;

public class APIRegisterService extends APIBasicJsonApiService {

	@Override
	protected BasicResponse service(JSONObject json) {
		JSONObject body = json.getJSONObject("body");
		if (!body.has("username") || !body.has("cellphone") || !body.has("pwd")|| !body.has("confirm_pwd")) {
			return new RTCodeResponse(APICode.REQUEST_PARAMETER_NOT_STISFIED);
		}
		String uname = null;
		String cellphone = null;
		String pwd = null;
		String confirmPwd = null;
		
		 uname = (String)body.getString("username");
		 cellphone = (String)body.getString("cellphone");
		 pwd = (String)body.getString("pwd");
		 confirmPwd = (String)body.getString("confirm_pwd");
		if (uname == null || uname.isEmpty() || pwd == null
				|| pwd.isEmpty() || confirmPwd == null
				|| confirmPwd.isEmpty() || cellphone == null
				|| cellphone.isEmpty()) {
			return new RTCodeResponse(APICode.REQUEST_PARAMETER_NOT_STISFIED);
		}
		
		
		if (!pwd.equals(confirmPwd)) {
			return new RTCodeResponse(APICode.REGISTER_ERROR_USER_PWD_MISMATCH);
		}
		
		
		User user = ServiceFactory.getESUserService().selectUser(cellphone, uname);
		if (user != null) {
			return new RTCodeResponse(APICode.REGISTER_ERROR_USER_EXIST);
		} 
			
		user = new User();
		user.setCellPhone(cellphone);
		user.setMail(uname);
		user.setPassword(pwd);
		int ret = ServiceFactory.getESUserService().addUser(user);
		if (ret == 0) {
			Token token = CacheManager.getIntance().saveUser(new Customer(user));
			requestRegisterEasemob(user);
			return new RegisterResponse(user, token);
			
		} else {
			return new RTCodeResponse(APICode.REGISTER_ERROR_INTERNAL);
		}
	}
	
	
	private void requestRegisterEasemob(final User user) {


		ServiceFactory.getEaseMobService().register(user.getCellPhone(), user.getPassword(), new EasemobRegisterCallback() {

			@Override
			public void onRegistered() {
				user.getPushTerminal().postEvent(new MessageEvent(new EasemobMessage(user)));
			}

			@Override
			public void onFailed() {
				user.getPushTerminal().postEvent(new MessageEvent(new EasemobMessage(user)));
			}

			@Override
			public void onError() {
				user.getPushTerminal().postEvent(new MessageEvent(new EasemobMessage(user)));
			}
			
		});
	}


	
}
