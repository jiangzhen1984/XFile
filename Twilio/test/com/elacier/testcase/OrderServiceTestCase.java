package com.elacier.testcase;


import java.sql.Date;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import elacier.order.Order;
import elacier.service.OrderService;

public class OrderServiceTestCase extends TestCase {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testAddOrder() {
		Order order = new Order();
		order.setTransaction(111111);
		order.setCancelDate(new Date(System.currentTimeMillis()));
		order.setFinishDate(new Date(System.currentTimeMillis()));
		order.setState(Order.ORDER_STATE_NOT_PAID);
		OrderService service = new OrderService();
		service.addOrder(order);
		assertTrue(order.getId() > 0);
	}

}
