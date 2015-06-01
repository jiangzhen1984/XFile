package com.todaybreakfast.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.todaybreakfast.model.Order;
import com.todaybreakfast.model.Order.OrderState;
import com.todaybreakfast.model.OrderItem;
import com.todaybreakfast.model.User;
import com.todaybreakfast.model.vo.Cart;
import com.todaybreakfast.model.vo.Cart.InnerBox;
import com.todaybreakfast.model.vo.Cart.Key;

public class OrderService extends BaseService {

	
	
	
	public Order checkoutCart(Cart cart, User user, String retrievePlace) {
		if (cart == null || user == null) {
			return null;
		}
		if (cart.getItemCount() <= 0) {
			return null;
		}
		
		Order order = new Order();
		order.setOrderDate(new Date(System.currentTimeMillis()));
		order.setState(OrderState.NOT_PAIED);
		order.setRetrievePlace(retrievePlace);
		order.setUser(user);
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH)+1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		int second = c.get(Calendar.SECOND);
		int ran = (new Random()).nextInt(9999);
		long transaction = year * 100000000000000L + month * 1000000000000L + day * 10000000000L
				+ hour * 100000000L + minute * 1000000L+ second * 10000L + ran;
		
		order.setTransaction(transaction);
		Session session = openSession();
		Transaction t = beginTransaction(session);
		session.save(order);
		session.flush();
		
		Set<Entry<Key, InnerBox>> sets = cart.getItems();
		Iterator<Entry<Key, InnerBox>> itr= sets.iterator();
		while(itr.hasNext()) {
			Entry<Key, InnerBox> entry = itr.next();
			OrderItem item = new OrderItem();
			item.setCount(entry.getValue().getCount());
			item.setPrice(entry.getValue().getWr().getPrice());
			item.setPicUrl(entry.getValue().getWr().getUrl());
			item.setName(entry.getValue().getWr().getName());
			item.setOrder(order);
			order.addItem(item);
			session.save(item);
		}
		
		
		
		t.commit();
		session.close();
		return order;
	}
	
	
	
	
	
	
	public List<Order> getUserOrderList(User user) {
		List<Order> list = new ArrayList<Order>();
		Session session = openSession();
		Query query = session.createQuery(" from Order where user.id=? order by transaction desc");
		query.setLong(0, user.getId());
		List<Order> dbList = query.list();
		for(int i = 0; i<dbList.size(); i++) {
			list.add(new Order(dbList.get(i)));
		}
		session.close();
		return list;
	}
	
	
	
	public List<Order> getUserOrderList(User user, Order.OrderState state) {
		List<Order> list = new ArrayList<Order>();
		Session session = openSession();
		Query query = session.createQuery(" from Order where user.id=? and state = ? order by transaction desc");
		query.setLong(0, user.getId());
		query.setInteger(1, state.ordinal());
		List<Order> dbList = query.list();
		for(int i = 0; i<dbList.size(); i++) {
			list.add(new Order(dbList.get(i)));
		}
		session.close();
		return list;
	}
	
	
	public int getUserOrderCount(User user, Order.OrderState state) {
		Session session = openSession();
		Query query = session.createQuery(" select count(*) from Order where user.id=? and state = ?");
		query.setLong(0, user.getId());
		query.setInteger(1, state.ordinal());
		Long i = (Long)query.list().get(0);
		session.close();
		return i.intValue();
	}
	
	
	
	public void payOrder(Order order) {
		Session session = openSession();
		Transaction t = session.beginTransaction();
		order.setState(OrderState.PAIED);
		order.setPaidDate(new Date(System.currentTimeMillis()));
		session.update(order);
		t.commit();
		session.close();
	}
	
	
	
	
	public void finishOrder(Order order) {
		Session session = openSession();
		Transaction t = session.beginTransaction();
		order.setState(OrderState.COMPLETED);
		order.setPaidDate(new Date(System.currentTimeMillis()));
		session.update(order);
		t.commit();
		session.close();
	}
	
	
	public void cancelOrder(Order order) {
		Session session = openSession();
		Transaction t = session.beginTransaction();
		order.setState(OrderState.CANCELED);
		order.setPaidDate(new Date(System.currentTimeMillis()));
		session.update(order);
		t.commit();
		session.close();
	}

}
