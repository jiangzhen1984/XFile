package com.skyworld.service;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.skyworld.service.dsf.Question;
import com.skyworld.service.dsf.User;
import com.skyworld.service.po.SWPQuestion;


public class SWQuestionService extends BaseService {

	
	
	public int saveQuestion(User user, Question question) {
		
		SWPQuestion ques = new SWPQuestion();
		ques.setMail(user.getMail());
		ques.setName(user.getName());
		ques.setQuestion(question.getQuestion());
		ques.setTimestamp(question.getTimestamp());
		
		Session session = openSession();
		Transaction t = session.beginTransaction();
		session.save(ques);
		question.setId(ques.getId());
		t.commit();
		session.close();
		return 0;
	}
	



}
