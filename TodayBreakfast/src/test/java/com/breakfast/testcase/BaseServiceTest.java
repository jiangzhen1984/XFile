package com.breakfast.testcase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;

import com.todaybreakfast.model.BFBreakFastCombo;
import com.todaybreakfast.model.BFBreakfast;
import com.todaybreakfast.model.vo.BreakfastComboWrapper;
import com.todaybreakfast.model.vo.BreakfastSingleWrapper;
import com.todaybreakfast.model.vo.BreakfastWrapper;
import com.todaybreakfast.service.BreakfastBasicService;

public class BaseServiceTest {
	
	BreakfastBasicService bs;
	@Before
	public void setUp() throws Exception {
		bs =  new BreakfastBasicService();
	}

	@Test
	public void testGetBFItemList() {
		Session session = bs.getSessionFactory().openSession();

		
		
		Transaction t = session.beginTransaction();

		BFBreakfast bf1 = new BFBreakfast();
		bf1.setName("aa");
		bf1.setToday(true);
		bf1.setPicUrl("aaaa");
		bf1.setPrice(20.2F);
		session.save(bf1);
		session.flush();
		
		t.commit();
		
		
		BFBreakFastCombo combo = new BFBreakFastCombo();
		combo.setName("cccc");
		combo.setPrice(123);
		combo.setToday(true);
		combo.addBreakfast(bf1);
		t = session.beginTransaction();
		session.save(combo);
		session.flush();
		t.commit();
		
		List<BreakfastWrapper> list = bs.getBreakfastList();
		System.out.println(list.size());
		assertTrue(list.size() > 1);
		
		session.close();
		
	}
	
	@Test
	public void testAddBreakfastWrapper() {
		BreakfastSingleWrapper bw = new BreakfastSingleWrapper(12.2F, "sdf", "/erwe", "辣椒， 狮子", "描述信息");
		bs.addBreakfastWrapper(bw);
		assertTrue(bw.getId() > 0);
		
		BreakfastSingleWrapper bw1 = new BreakfastSingleWrapper(12.2F, "bw", "/erwe1111", "辣椒， 狮子", "描述信息");
		bs.addBreakfastWrapper(bw1);
		assertTrue(bw1.getId() > 0);
		
		BreakfastComboWrapper bwc = new BreakfastComboWrapper(13.2F, "sdf", "辣椒， 狮子", "描述信息"	);
		bwc.addItem(bw);
		bwc.addItem(bw1);
		bs.addBreakfastWrapper(bwc);
		assertTrue(bwc.getId() > 0);
	}
	
	
	@Test
	public void testRemoveBreakfastWrapper() {
		BreakfastComboWrapper bwc = new BreakfastComboWrapper(1);
		bs.removeBreakfastWrapper(bwc);
	}

}
