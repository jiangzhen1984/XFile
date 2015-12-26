package com.skyworld.service;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.skyworld.service.dsf.Question;
import com.skyworld.service.dsf.User;
import com.skyworld.service.po.SWPQuestion;


public class SWQuestionService extends BaseService {

	
	
	public int saveQuestion(User user, Question question) {
		if (question == null) {
			throw new NullPointerException(" question is null");
		}
		SWPQuestion ques = new SWPQuestion();
		ques.setMail(user.getMail());
		ques.setName(user.getName());
		ques.setQuestion(question.getQuestion());
		ques.setTimestamp(question.getTimestamp());
		ques.setState(question.getState().ordinal());
		
		Session session = openSession();
		Transaction t = session.beginTransaction();
		session.save(ques);
		question.setId(ques.getId());
		t.commit();
		session.close();
		return 0;
	}
	


	
	public void cancelQuestion(Question question) {
		if (question == null) {
			throw new NullPointerException(" question is null");
		}
		Session session = openSession();
		SWPQuestion q = (SWPQuestion)session.load(SWPQuestion.class , question.getId());
		q.setState(Question.State.CANCEL.ordinal());
		Transaction t = session.beginTransaction();
		session.update(q);
		t.commit();
		session.close();
		question.setState(Question.State.CANCEL);
	}
	
	
	public void finishQuestion(Question question) {
		if (question == null) {
			throw new NullPointerException(" question is null");
		}
		
		Session session = openSession();
		SWPQuestion q = (SWPQuestion)session.load(SWPQuestion.class , question.getId());
		q.setState(Question.State.RESOVLED.ordinal());
		Transaction t = session.beginTransaction();
		session.update(q);
		t.commit();
		session.close();
		question.setState(Question.State.RESOVLED);
	}

}
