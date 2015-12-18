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
import com.skyworld.service.ServiceFactory;
import com.skyworld.service.dsf.Question;
import com.skyworld.service.dsf.User;
import com.skyworld.utils.JSONFormat;

public class QuestionApi extends HttpServlet {

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
		} else {
			Map<String, JSONObject> map = null;
			boolean error = false;
			try {
				map = JSONFormat.parse(data);
			}catch(Exception e) {
				e.printStackTrace();
				response.append("{ret: -1}");
				error = true;
			}
			if (!error) {
				String action = null;
				String tokenId = null;
				try {
					tokenId = (String) map.get("header").get("token");
					action = (String) map.get("header").get("action");
				} catch (Exception e) {
					e.printStackTrace();
				}
	
				if (tokenId == null || tokenId.isEmpty() || action == null
						|| action.isEmpty()) {
					response.append("{ ret : -3 }");
					error = true;
				} 
				
				if (!"question".equalsIgnoreCase(action)) {
					response.append("{ ret : -2 }");
					error = true;
				}
				
				if (!error) {
					try {
						Long lon = Long.parseLong(tokenId);
						response.append(quest(TokenFactory.valueOf(lon), map.get("body")));
					} catch (NumberFormatException e) {
						response.append("{ ret : -4 }");
					}
	
				}
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

	private String quest(Token token, JSONObject jobject) {
		int opt = 1;
		String quest = null;

		try {
			opt = jobject.getInt("opt");
			quest = jobject.getString("question");
		} catch (Exception e) {
			e.printStackTrace();
			return "{ ret : -3 }";
		}
		
		
		User user = CacheManager.getIntance().getUser(token);
		if (user == null) {
			return "{ ret : -5 }";
		} 
		
		if (quest == null || quest.isEmpty()) {
			return "{ ret : -3 }";
		}
		
		Question question = new Question(quest);

		switch (opt) {
		case 1:
			int ret = ServiceFactory.getQuestionService().saveQuestion(user, question);
			if (ret == 0) {
				user.setCurrentQuest(question);
				return "{ ret : 0, question_id : "+question.getId()+" }";
			} else {
				return "{ ret : -302 }";
			}
		case 2:
			return "{ ret : 0 }";
		case 3:
			return "{ ret : 0 }";
		default:
			return "{ ret : -301 }";
		}
	}
	
	
	
	

}
