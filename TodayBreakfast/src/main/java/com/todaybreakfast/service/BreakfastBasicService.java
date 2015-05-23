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

	
	
	public List<BreakfastWrapper> getBreakfastList() {
		List<BreakfastWrapper> itemList =new ArrayList<BreakfastWrapper>();
		Session session = this.getSessionFactory().openSession();
		Query query = session.createQuery("from BFBreakfast" );
		List<BFBreakfast> basicItemList = query.list();
		for (BFBreakfast bf : basicItemList) {
			itemList.add(new BreakfastSingleWrapper(bf));
		}
		
		query = session.createQuery("from BFBreakFastCombo " );
		List<BFBreakFastCombo> comboItemList = query.list();
		for (BFBreakFastCombo bfc : comboItemList) {
			itemList.add(new BreakfastComboWrapper(bfc));
		}
		
		session.close();
		
		return itemList;
	}
	
	
	public void addBreakfastWrapper(BreakfastWrapper bw) {
		Session session = this.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		if (bw.getType() == BreakfastWrapper.Type.COMBO) {
			BFBreakFastCombo bfc = new BFBreakFastCombo();
			bfc.setName(bw.getName());
			bfc.setPrice(bw.getPrice());
			bfc.setToday(true);
			
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
	
	
	
	
}
