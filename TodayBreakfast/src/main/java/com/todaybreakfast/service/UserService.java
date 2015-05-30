package com.todaybreakfast.service;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.todaybreakfast.model.User;

public class UserService extends BaseService {

	
	public User selectUser(String userName, String password) {
		Session session = this.getSessionFactory().openSession();
		Query query = session.createQuery(" from User where cellPhone=? and password=?");
		query.setString(0, userName);
		query.setString(1, password);
		List<User> list = (List<User>)query.list();
		User user = null;
		if (list.size() > 0) {
			user = new User(list.get(0));
		}
		session.close();
		return user;
	}
	
	
	public User selectUser(String userName) {
		Session session = this.getSessionFactory().openSession();
		Query query = session.createQuery(" from User where cellPhone=?");
		query.setString(0, userName);
		List<User> list = (List<User>)query.list();
		User user = null;
		if (list.size() > 0) {
			user = new User(list.get(0));
		}
		session.close();
		return user;
	}
	
	public void addUser(User user) {
		
	}
}
