package com.eshopping.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.eshopping.model.po.ESCategory;
import com.eshopping.model.po.ESComboItem;
import com.eshopping.model.po.ESItem;
import com.eshopping.model.vo.AbsShoppingItem;
import com.eshopping.model.vo.Category;
import com.eshopping.model.vo.ComboShoppingItem;
import com.eshopping.model.vo.SingleShoppingItem;

public class EShoppingService extends BaseService {
	
	public void addShoppingItem(AbsShoppingItem item) {
		Session session = openSession();
		Transaction t = beginTransaction(session);
		if (item.getType() == AbsShoppingItem.TYPE_SINGLE) {
			ESItem esitem = new ESItem();
			esitem.setDescription(item.getDescription());
			esitem.setName(item.getName());
			esitem.setPrice(item.getPrice());
			esitem.setPicUrl(((SingleShoppingItem)item).getPicUrl());
			session.saveOrUpdate(esitem);
			item.setId(esitem.getId());
		} else if (item.getType() == AbsShoppingItem.TYPE_COMBO) {
			ComboShoppingItem   comboShoppingItem = (ComboShoppingItem)item;
			ESComboItem comboItem = new ESComboItem();
			comboItem.setDescription(item.getDescription());
			comboItem.setName(item.getName());
			comboItem.setPrice(item.getPrice());
			
			List<SingleShoppingItem> list = comboShoppingItem.getItemList();
			for (SingleShoppingItem si : list) {
				ESItem esi = new ESItem();
				esi.setId(si.getId());
				comboItem.addItem(esi);
			}
			
			session.saveOrUpdate(comboItem);
			item.setId(comboItem.getId());
		}
		
		t.commit();
		session.close();
	}
	
	public void deleteShoppingItem(AbsShoppingItem item) {
		Session session = openSession();
		Transaction t = beginTransaction(session);
		if (item.getType() == AbsShoppingItem.TYPE_SINGLE) {
			ESItem esitem = new ESItem();
			esitem.setId(item.getId());
			session.delete(esitem);
		} else if (item.getType() == AbsShoppingItem.TYPE_COMBO) {
			ESComboItem eci = new ESComboItem();
			eci.setId(item.getId());
			session.delete(eci);
		}
		t.commit();
		session.close();
	}
	
	
	public void addCategory(Category cate) {
		Session session = openSession();
		Transaction t = beginTransaction(session);
		ESCategory  escate = new ESCategory();
		escate.setName(cate.getName());
		escate.setSeq(cate.getSeq());
		escate.setParentId(cate.getParentId());
		session.saveOrUpdate(escate);
		cate.setId(escate.getId());
		t.commit();
		session.close();
	}
	
	
	public void updateCategory(Category cate) {
		Session session = openSession();
		Transaction t = beginTransaction(session);
		ESCategory  escate = (ESCategory)session.load(ESCategory.class, cate.getId());
		escate.setName(cate.getName());
		escate.setSeq(cate.getSeq());
		escate.setParentId(cate.getParentId());
		session.saveOrUpdate(escate);
		cate.setId(escate.getId());
		t.commit();
		session.close();
	}
	
	
	
	public List<Category> queryCategory(int start, int fetchCount) {
		Session sess = openSession();
		List<Category> queryList =  null;
			Query query = sess.createQuery(" from ESCategory order by parentId ");
			query.setFirstResult(start);
			query.setMaxResults(fetchCount);
			List list = query.list();
			int size = list.size();
			if (size > 0) {
				queryList = new ArrayList<Category>(size);
				for (int i = 0; i < size; i++) {
					ESCategory ei = (ESCategory) list.get(i);
					queryList.add(new Category(ei));
				}
			}
		
		sess.close();
		return queryList;
	}
	
	
	
	public List<AbsShoppingItem> queryItemList(int type, int start, int fetchCount) {
		
		Session sess = openSession();
		List<AbsShoppingItem> queryList =  null;
		if (type == AbsShoppingItem.TYPE_SINGLE) {
			Query query = sess.createQuery(" from ESItem ");
			query.setFirstResult(start);
			query.setMaxResults(fetchCount);
			List list = query.list();
			int size = list.size();
			if (size > 0) {
				queryList = new ArrayList<AbsShoppingItem>(size);
				for (int i = 0; i < size; i++) {
					ESItem ei = (ESItem) list.get(i);
					queryList.add(new SingleShoppingItem(ei));
				}
			}
			
		} else if (type == AbsShoppingItem.TYPE_COMBO) {
			Query query = sess.createQuery(" from ESComboItem ");
			query.setFirstResult(start);
			query.setMaxResults(fetchCount);
			List list = query.list();
			int size = list.size();
			if (size > 0) {
				queryList = new ArrayList<AbsShoppingItem>(size);
				for (int i = 0; i < size; i++) {
					ESComboItem ei = (ESComboItem) list.get(i);
					queryList.add(new ComboShoppingItem(ei));
				}
			}
		}
		
		sess.close();
		return queryList;
	}

}
