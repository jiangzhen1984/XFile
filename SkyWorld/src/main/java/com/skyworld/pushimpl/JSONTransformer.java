package com.skyworld.pushimpl;

import java.io.IOException;
import java.io.InputStream;

import com.skyworld.push.HttpPushMessageTransformer;
import com.skyworld.push.msg.HttpPushMessage;

public class JSONTransformer implements HttpPushMessageTransformer {

	@Override
	public HttpPushMessage<?> unserialize(InputStream in) throws IOException {
		return null;
	}

	@Override
	public String serialize(HttpPushMessage<?> message) {
		if (message instanceof QuestionMessage) {
			QuestionMessage qm = (QuestionMessage)message;
			return "{\"header\":{\"category\":\"question\"}, \"body\" :{\"opt\":1, \"quest\":\""
					+ qm.getQuestion().getQuestion()
					+ "\", \"quest_id\":"
					+ qm.getQuestion().getId()
					+ ", \"user_id\":"
					+ qm.getQuestion().getAsker().getId()
					+ ", \"user_cellphone\":"
					+ qm.getQuestion().getAsker().getCellPhone()
					+ ", \"user_mail\":"
					+ qm.getQuestion().getAsker().getMail() + "}}";
		} else if (message instanceof AnswerMessage) {
			AnswerMessage am = (AnswerMessage)message;
			return "{\"header\":{\"category\":\"answer\"}, \"body\" :{\"opt\":1, \"quest\":\""
					+ am.getQuestion().getQuestion()
					+ "\", \"quest_id\":"
					+ am.getQuestion().getId()
					+ ", \"syservicer\":"
					+ am.getServicer().getId()
					+ ", \"answer\":"
					+ am.getAns().getContent()
					+"}}";
		} else {
			return "{header:{ret: 0}}";
		}
	}

	@Override
	public String getContentType() {
		return "application/json";
	}

}
