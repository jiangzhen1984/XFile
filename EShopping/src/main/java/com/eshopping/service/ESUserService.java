package com.eshopping.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.eshopping.model.po.ESUser;
import com.eshopping.model.po.ESUserAddress;
import com.eshopping.model.vo.Address;
import com.eshopping.model.vo.User;


public class ESUserService extends BaseService {

	
	public User selectUser(String userName, String mail, String password) {
		Session session = openSession();
		Query query = session.createQuery(" from ESUser where (cellPhone=? or mail =?) and password=?");
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
		Session session = openSession();
		Query query = session.createQuery(" from ESUser where (cellPhone=? or mail =?) ");
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
		Session session = openSession();
		Transaction t = session.beginTransaction();
		ESUser esu = new ESUser();
		esu.setCellPhone(user.getCellPhone());
		esu.setMail(user.getMail());
		esu.setName(user.getName());
		esu.setPassword(user.getPassword());
		session.save(esu);
		user.setId(esu.getId());
		t.commit();
		session.close();
		return 0;
	}
	
	
	public void addAddress(Address addr) {
		if (addr == null) {
			return;
		}
		
		Session session = openSession();
		Transaction t = session.beginTransaction();
		ESUserAddress ea = new ESUserAddress();
		ea.setAddress(addr.getAddress());
		ea.setCity(addr.getCity());
		ea.setPhoneNumber(addr.getPhoneNumber());
		ea.setCountry(addr.getCountry());
		ea.setPhoneNumber(addr.getPhoneNumber());
		ea.setName(addr.getName());
		ea.setState(addr.getState());
		ea.setUserId(addr.getUser().getId());
		addr.setId(ea.getId());
		session.save(ea);
		t.commit();
		session.close();
	}
	
	public void removeAddress(Address addr) {
		if (addr == null) {
			return;
		}
		Session session = openSession();
		Transaction t = session.beginTransaction();
		ESUserAddress  ea = new ESUserAddress();
		ea.setId(addr.getId());
		session.delete(ea);
		t.commit();
		session.close();
		
	}
	
	
	public void updateDefaultAddress(Address addr, boolean flag) {
		if (addr == null) {
			return;
		}
		
		Session session = openSession();
		Query query = session.createQuery(" from ESUserAddress where isDefault =:de ");
		query.setBoolean("de", true);
		List<ESUserAddress> list = query.list();
		ESUserAddress  ea = (ESUserAddress)session.load(ESUserAddress.class, addr.getId());
		ea.setDefault(flag);
		
		Transaction t = session.beginTransaction();
		for (ESUserAddress eua : list) {
			eua.setDefault(false);
			session.update(eua);
		}
		session.update(ea);
		t.commit();
		
		session.close();
	}
	
	
	public List<Address> queryUserAddress(User user) {
		if (user == null) {
			return null;
		}
		Session session = openSession();
		Query query = session.createQuery(" from ESUserAddress where userId =:uid order by isDefault desc");
		query.setLong("uid", user.getId());
		
		List<ESUserAddress> list = query.list();
		List<Address> queryList = null;
		if (list != null && list.size() > 0) {
			queryList = new  ArrayList<Address>(list.size()); 
			for (ESUserAddress eua : list) {
				Address addr = new Address(eua);
				addr.setUser(user);
				queryList.add(addr);
			}
		}
		session.close();
		
		return queryList;
	}
	
}
