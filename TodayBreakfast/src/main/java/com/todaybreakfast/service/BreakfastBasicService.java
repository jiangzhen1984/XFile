package com.todaybreakfast.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.todaybreakfast.model.BFBreakFastCombo;
import com.todaybreakfast.model.BFBreakfast;
import com.todaybreakfast.model.vo.BreakfastComboWrapper;
import com.todaybreakfast.model.vo.BreakfastSingleWrapper;
import com.todaybreakfast.model.vo.BreakfastWrapper;

public class BreakfastBasicService extends BaseService {

	private static List<BreakfastWrapper> cacheList;
	
	public List<BreakfastWrapper> getBreakfastList() {
		if (cacheList != null) {
			return cacheList;
		}
		cacheList =new ArrayList<BreakfastWrapper>();
		Session session = this.getSessionFactory().openSession();
		Query query = session.createQuery("from BFBreakfast" );
		List<BFBreakfast> basicItemList = query.list();
		for (BFBreakfast bf : basicItemList) {
			cacheList.add(new BreakfastSingleWrapper(bf));
		}
		
		query = session.createQuery("from BFBreakFastCombo " );
		List<BFBreakFastCombo> comboItemList = query.list();
		for (BFBreakFastCombo bfc : comboItemList) {
			cacheList.add(new BreakfastComboWrapper(bfc));
		}
		
		session.close();
		
		return cacheList;
	}
	
	
	public void addBreakfastWrapper(BreakfastWrapper bw) {
		Session session = this.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		if (bw.getType() == BreakfastWrapper.Type.COMBO) {
			BFBreakFastCombo bfc = new BFBreakFastCombo();
			bfc.setName(bw.getName());
			bfc.setPrice(bw.getPrice());
			bfc.setToday(true);
			bfc.setDescription(bw.getDescription());
			bfc.setStuff(bw.getDescription());
			
			BreakfastComboWrapper bcw = (BreakfastComboWrapper) bw;
			List<BreakfastSingleWrapper> itemWrapperList = bcw.getItems();
			for(BreakfastSingleWrapper item: itemWrapperList) {
				BFBreakfast bf = new BFBreakfast();
				bf.setId(item.getId());
				bfc.addBreakfast(bf);
			}
			session.saveOrUpdate(bfc);
			bw.setId(bfc.getId());
		} else if (bw.getType() == BreakfastWrapper.Type.SINGLE) {
			BFBreakfast bf = new BFBreakfast();
			bf.setName(bw.getName());
			bf.setPrice(bw.getPrice());
			bf.setToday(true);
			bf.setPicUrl(((BreakfastSingleWrapper)bw).getUrl());
			bf.setDescription(bw.getDescription());
			bf.setStuff(bw.getDescription());
			session.save(bf);
			bw.setId(bf.getId());
		}
		session.flush();
		t.commit();
		session.close();
	}
	
	
	public void removeBreakfastWrapper(BreakfastWrapper bw) {
		Session session = this.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		if (bw.getType() == BreakfastWrapper.Type.COMBO) {
			BFBreakFastCombo bfc = new BFBreakFastCombo();
			bfc.setId(bw.getId());
			session.delete(bfc);
		} else if (bw.getType() == BreakfastWrapper.Type.SINGLE) {
			BFBreakfast bf = new BFBreakfast();
			bf.setId(bw.getId());
			session.delete(bf);
		}
		session.flush();
		t.commit();
		session.close();
	}
	
	
	public BreakfastWrapper findBreakfast(long id, BreakfastWrapper.Type type) {
		for (int i = 0; i < cacheList.size(); i++) {
			BreakfastWrapper bw = cacheList.get(i);
			if (bw.getId() == id) {
				return bw;
			}
		}
		//TODO search from database
		return null;
	}
	
}
