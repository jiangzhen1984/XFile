package com.breakfast.testcase;

import java.util.List;

import junit.framework.TestCase;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;

import com.eshopping.model.po.ESComboItem;
import com.eshopping.model.vo.AbsShoppingItem;
import com.eshopping.model.vo.Category;
import com.eshopping.model.vo.ComboShoppingItem;
import com.eshopping.model.vo.SingleShoppingItem;
import com.eshopping.service.EShoppingService;

public class EShoppingServiceTest extends TestCase  {

	EShoppingService mService;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mService = new EShoppingService();
	}

	
	@Test
	public void testAddShoppingItem() {
		SingleShoppingItem si = new SingleShoppingItem();
		si.setName("aaa");
		si.setPrice(10.0F);
		si.setDescription("ssss");
		mService.addShoppingItem(si);
		assertTrue(si.getId() > 0);
		
		
		ComboShoppingItem ci = new ComboShoppingItem();
		ci.setName("bbb");
		ci.setPrice(12.0F);
		ci.addItem(si);
		mService.addShoppingItem(ci);
		assertTrue(ci.getId() > 0);
	}
	
	
	@Test
	public void testQueryItemList() {
		List<AbsShoppingItem> list = mService.queryItemList(AbsShoppingItem.TYPE_SINGLE, 0, 1);
		assertTrue(list.size() > 0);
		
		list = mService.queryItemList(AbsShoppingItem.TYPE_SINGLE, 110, 10);
		assertTrue(list == null);
		
		
		list = mService.queryItemList(AbsShoppingItem.TYPE_COMBO, 0, 1);
		assertTrue(list.size() > 0);
		
		list = mService.queryItemList(AbsShoppingItem.TYPE_COMBO, 110, 10);
		assertTrue(list == null);
	}
	
	
	
	@Test
	public void testDeleteShoppingItem() {
		Session session = mService.getSessionFactory().openSession();
		Query query = session.createQuery(" from ESComboItem ");
		List l = query.list();
		if (l.size() <= 0) {
			assertTrue(false);
		}
		ESComboItem esi = (ESComboItem)l.get(0);
		
		mService.deleteShoppingItem(new ComboShoppingItem(esi));
		assertTrue(true);
		session.close();
		
	}
	
	
	

	
	
	@Test
	public void testAddCategory() {
		Category  cate = new Category();
		cate.setName("a");
		cate.updateParent(null);
		mService.addCategory(cate);
		assertTrue(cate.getId() > 0);
		
		
		Category  cateB = new Category();
		cateB.setName("b");
		cateB.updateParent(null);
		mService.addCategory(cateB);
		assertTrue(cateB.getId() > 0);
		
		Category  subCate = new Category();
		subCate.setName("a1");
		subCate.updateParent(cate);
		mService.addCategory(subCate);
		assertTrue(subCate.getId() > 0);
		
		
		subCate.updateParent(cateB);
		mService.updateCategory(subCate);
		
	}
	
	
	
	public void testQueryCategory() {
		List<Category> list = mService.queryCategory(0, 100);
		assertTrue(list != null);
		assertTrue(list.size() > 0);
		
	}
	
	
	public void testQueryItemListByCategory() {
		List<AbsShoppingItem> list = mService.queryItemList(1L, 0, 10);
		assertTrue(list  != null);
	}
	
	
}
