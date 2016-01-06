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
import com.skyworld.push.event.MessageEvent;
import com.skyworld.pushimpl.AnswerMessage;
import com.skyworld.service.ServiceFactory;
import com.skyworld.service.dsf.Answer;
import com.skyworld.service.dsf.Customer;
import com.skyworld.service.dsf.Question;
import com.skyworld.service.dsf.SKServicer;
import com.skyworld.utils.JSONFormat;

public class QuestionApi extends HttpServlet {

	
	Log log = LogFactory.getLog(this.getClass());
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		checkJson(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		checkJson(req, resp);
	}

	private void checkJson(HttpServletRequest req, HttpServletResponse resp) {
		String data = req.getParameter("data");
		log.info("====> data: " + data);
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
				
				if ("question".equalsIgnoreCase(action) && !error) {
					try {
						Long lon = Long.parseLong(tokenId);
						response.append(quest(TokenFactory.valueOf(tokenId), map.get("body")));
					} catch (NumberFormatException e) {
						response.append("{ ret : -4 }");
						error = true;
					}
				}
				
				if ("answer".equalsIgnoreCase(action) && !error) {
					try {
						Long lon = Long.parseLong(tokenId);
						response.append(answer(TokenFactory.valueOf(tokenId), map.get("body")));
					} catch (NumberFormatException e) {
						response.append("{ ret : -4 }");
						error = true;
					}
				}
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
	
	
	private String answer(Token token, JSONObject jobject) {
		String ans = null;
		long  questId;
		try {
			ans = jobject.getString("answer");
			questId = jobject.getLong("question_id");
		} catch (Exception e) {
			e.printStackTrace();
			return "{ ret : -3 }";
		}
		
		if (ans == null || ans.isEmpty()) {
			return "{ ret : -3 }";
		}
		
		SKServicer servicer = CacheManager.getIntance().getSKServicer(token);
		if (servicer == null) {
			return "{ ret : -5 }";
		}
		
		Question quest = CacheManager.getIntance().getPendingQuestion(questId);
		if (quest == null) {
			return "{ ret : 401 }";
		}
		Answer  answer = new Answer(ans);
		quest.setAnswer(servicer, answer);
		if (quest.getAsker().getPushTerminal() != null) {
			quest.getAsker().getPushTerminal().postEvent(new MessageEvent(new AnswerMessage(quest, answer, servicer)));
		} else {
			log.error("[ERROR] No push terminal : " + quest.getAsker());
		}
		
		return "{ ret : 0 }";
	}
	
	
	private String quest(Token token, JSONObject jobject) {
		int opt = 1;
		String quest = null;
		long id = 0;

		try {
			opt = jobject.getInt("opt");
			if (opt == 1) {
				quest = jobject.getString("question");
			} else {
				id = jobject.getLong("question_id");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "{ ret : -3 }";
		}
		
		
		Customer user = CacheManager.getIntance().getCustomer(token);
		if (user == null) {
			return "{ ret : -5 }";
		} 
		
		

		switch (opt) {
		case 1:
			if (quest == null || quest.isEmpty()) {
				return "{ ret : -3 }";
			}
			
			Question question = new Question(user, quest);
			int ret = ServiceFactory.getQuestionService().saveQuestion(user, question);
			CacheManager.getIntance().addPendingQuestion(question);
			if (ret == 0) {
				user.setCurrentQuest(question);
				ServiceFactory.getQuestionService().broadcastQuestion(question);
				return "{ ret : 0, question_id : "+question.getId()+" }";
			} else {
				return "{ ret : -302 }";
			}
		case 2:
			Question cancelQuest = new Question();
			cancelQuest.setId(id);
			ServiceFactory.getQuestionService().cancelQuestion(cancelQuest);
			CacheManager.getIntance().removePendingQuestion(id);
			return "{ ret : 0, question_id : "+id+" }";
		case 3:
			Question finishQuest = new Question();
			finishQuest.setId(id);
			ServiceFactory.getQuestionService().finishQuestion(finishQuest);
			CacheManager.getIntance().removePendingQuestion(id);
			return "{ ret : 0, question_id : "+id+" }";
		default:
			return "{ ret : -301 }";
		}
	}
	
	
	
	

}
