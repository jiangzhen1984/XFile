package com.elacier.testcase;


import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import elacier.process.GuestInformation;
import elacier.process.GuestTransaction;
import elacier.provider.msg.InquiryRespondNotification;
import elacier.restaurant.Menu;
import elacier.restaurant.Restaurant;
import elacier.transaction.TransactionManager;

public class GuestTransactionTest extends TestCase {

	GuestTransaction trans;
	
	@Before
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void test() {
		trans = (GuestTransaction)TransactionManager.getInstance().createTransaction(GuestTransaction.class);
		trans.setInformation(new GuestInformation());
		TransactionManager.getInstance().beginTransaction(trans);
		assertTrue(trans.getState() instanceof GuestTransaction.InquiryState);
		
		trans.updateRestaurantResponse(1, InquiryRespondNotification.INQUIRY_RESPOND_NOTIFICAITON_RET_OK);
		assertTrue(trans.getState() instanceof GuestTransaction.NotifyGuestRestaurantRespondState);
		
		List<Menu> list = new ArrayList<Menu>();
		list.add(new Menu(1, "a", 2.0F));
		list.add(new Menu(2, "a1", 2.0F));
		list.add(new Menu(3, "a2", 2.0F));
		trans.updateOrderInformation(new Restaurant(1, "ss", null, 0.3F), list);
		assertTrue(trans.getState() instanceof GuestTransaction.GuestRequestOrderState);
		
		trans.updateRestaurantConfirmationOrder(1, 1);
		assertTrue(trans.getState() instanceof GuestTransaction.RestaurantConfirmOrderState);
		
		TransactionManager.getInstance().finishTransaction(trans);
		
	}

}
