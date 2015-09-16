package com.eshopping.service;

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

import com.eshopping.model.Key;
import com.eshopping.model.po.ESOrder;
import com.eshopping.model.po.ESOrderItem;
import com.eshopping.model.vo.Cart;
import com.eshopping.model.vo.Cart.InnerBox;
import com.eshopping.model.vo.DeliveryInfo;
import com.eshopping.model.vo.Order;
import com.eshopping.model.vo.Order.OrderState;
import com.eshopping.model.vo.Order.PaymentType;
import com.eshopping.model.vo.User;


public class OrderService extends BaseService {

	
	
	
	public Order checkoutCart(Cart cart, User user, String retrievePlace, PaymentType pType) {
		if (cart == null || user == null) {
			return null;
		}
		if (cart.getItemCount() <= 0) {
			return null;
		}
		
		ESOrder order = new ESOrder();
		order.setLastUpdateDate(new Date(System.currentTimeMillis()));
		order.setState(Order.OrderState.NOT_PAIED_YET.ordinal());
		order.setRetrievePlace(retrievePlace);
		order.setUser(user);
		order.setPayType(pType.ordinal());
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
			ESOrderItem item = new ESOrderItem();
			item.setCount(entry.getValue().getCount());
			item.setPrice(entry.getValue().getWr().getPrice());
			//TODO update
			//item.setPicUrl(entry.getValue().getWr().getUrl());
			item.setName(entry.getValue().getWr().getName());
			item.setOrder(order);
			order.addItem(item);
			session.save(item);
		}
		
		Order vOrder = new Order(order);
		
		
		t.commit();
		session.close();
		return vOrder;
	}
	
	
	public void handleOrderForDelivery(Order order, DeliveryInfo dinfo) {
		Session session = openSession();
		Transaction t = beginTransaction(session);
		ESOrder cacheOrder = (ESOrder)session.get(ESOrder.class, order.getId());
		cacheOrder.setLastUpdateDate(new Date(System.currentTimeMillis()));
		cacheOrder.setState(OrderState.DELIVERING.ordinal());
		cacheOrder.setDeliverDate(cacheOrder.getDeliverDate());
		session.update(cacheOrder);
		
		//TODO add delivery info
		session.update(cacheOrder);
		t.commit();
		session.close();
		order.setState(Order.OrderState.DELIVERING);
		order.setLastUpdateDate(cacheOrder.getPaidDate());
		order.setDeliverDate(cacheOrder.getDeliverDate());
	}
	
	
	
	public void payOrder(Order order) {
		Session session = openSession();
		Transaction t = session.beginTransaction();
		ESOrder cacheOrder = (ESOrder)session.get(ESOrder.class, order.getId());
		cacheOrder.setPaidDate(new Date(System.currentTimeMillis()));
		cacheOrder.setLastUpdateDate(cacheOrder.getPaidDate());
		cacheOrder.setState(Order.OrderState.PAIED.ordinal());
		session.update(cacheOrder);
		t.commit();
		session.close();
		order.setState(Order.OrderState.PAIED);
		order.setPaidDate(cacheOrder.getPaidDate());
		order.setLastUpdateDate(cacheOrder.getPaidDate());
	}
	
	
	
	
	public void finishOrder(Order order) {
		Session session = openSession();
		Transaction t = session.beginTransaction();
		ESOrder cacheOrder = (ESOrder)session.get(ESOrder.class, order.getId());
		cacheOrder.setState(Order.OrderState.COMPLETED.ordinal());
		cacheOrder.setLastUpdateDate(cacheOrder.getPaidDate());
		session.update(cacheOrder);
		t.commit();
		session.close();
		order.setState(Order.OrderState.COMPLETED);
		order.setLastUpdateDate(cacheOrder.getLastUpdateDate());
	}
	
	
	public void cancelOrder(Order order) {
		Session session = openSession();
		Transaction t = session.beginTransaction();
		ESOrder cacheOrder = (ESOrder)session.get(ESOrder.class, order.getId());
		cacheOrder.setState(Order.OrderState.CANCELED.ordinal());
		cacheOrder.setLastUpdateDate(new Date(System.currentTimeMillis()));
		session.update(cacheOrder);
		t.commit();
		session.close();
		order.setState(Order.OrderState.CANCELED);
		order.setLastUpdateDate(cacheOrder.getLastUpdateDate());
	}
	

	
	
	
	public List<ESOrder> getUserOrderList(User user) {
		List<ESOrder> list = new ArrayList<ESOrder>();
		Session session = openSession();
		Query query = session.createQuery(" from Order where user.id=? order by transaction desc");
		query.setLong(0, user.getId());
		List<ESOrder> dbList = query.list();
		for(int i = 0; i<dbList.size(); i++) {
			list.add(new ESOrder(dbList.get(i)));
		}
		session.close();
		return list;
	}
	
	
	
	public List<ESOrder> getUserOrderList(User user, Order.OrderState state) {
		List<ESOrder> list = new ArrayList<ESOrder>();
		Session session = openSession();
		Query query = session.createQuery(" from Order where user.id=? and state = ? order by transaction desc");
		query.setLong(0, user.getId());
		query.setInteger(1, state.ordinal());
		List<ESOrder> dbList = query.list();
		for(int i = 0; i<dbList.size(); i++) {
			list.add(new ESOrder(dbList.get(i)));
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
	
	


}
