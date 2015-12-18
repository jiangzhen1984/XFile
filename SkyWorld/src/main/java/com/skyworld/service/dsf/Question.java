package com.skyworld.service.dsf;

import java.sql.Date;

public class Question {
	
	private long id;
	
	private String question;
	
	private Date timestamp;

	public Question(String question) {
		super();
		this.question = question;
		timestamp = new Date(System.currentTimeMillis());
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	

	
	
}
