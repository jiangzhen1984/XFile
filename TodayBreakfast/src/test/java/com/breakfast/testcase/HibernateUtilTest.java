package com.breakfast.testcase;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;

import com.todaybreakfast.model.util.HibernateUtil;

public class HibernateUtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetSessionFactory() {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		assert(sf != null);
	}

}
