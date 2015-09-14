package com.eshopping.service;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.eshopping.model.po.User;


public class ESUserService extends BaseService {

	
	public User selectUser(String userName, String mail, String password) {
		Session session = this.getSessionFactory().openSession();
		Query query = session.createQuery(" from User where (cellPhone=? or mail =?) and password=?");
		query.setString(0, userName);
		query.setString(1, mail);
		query.setString(2, password);
		List<User> list = (List<User>)query.list();
		User user = null;
		if (list.size() > 0) {
			user = new User(list.get(0));
		}
		session.close();
		return user;
	}
	
	
	public User selectUser(String phone, String mail) {
		Session session = this.getSessionFactory().openSession();
		Query query = session.createQuery(" from User where (cellPhone=? or mail =?) ");
		query.setString(0, phone);
		query.setString(1, mail);
		List<User> list = (List<User>)query.list();
		User user = null;
		if (list.size() > 0) {
			user = new User(list.get(0));
		}
		session.close();
		return user;
	}
	
	public int addUser(User user) {
		User u = selectUser(user.getCellPhone(), user.getMail());
		if (u != null) {
			return -1;
		}
		Session session = this.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.save(user);
		t.commit();
		session.close();
		return 0;
	}
}
