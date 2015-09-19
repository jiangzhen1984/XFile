package com.elacier.testcase;


import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import elacier.Restaurant;
import elacier.process.GuestInformation;
import elacier.process.GuestTransaction;
import elacier.provider.msg.InquiryRespondNotification;
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
		
		trans.updateOrderInformation(2, new Restaurant(1, "ss", null, 0.3F), null);
		assertTrue(trans.getState() instanceof GuestTransaction.GuestRequestOrderState);
		
		trans.updateRestaurantConfirmationOrder(1, 1);
		assertTrue(trans.getState() instanceof GuestTransaction.RestaurantConfirmOrderState);
		
		TransactionManager.getInstance().finishTransaction(trans);
		
	}

}
