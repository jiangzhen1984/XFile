package com.elacier.testcase;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import elacier.restaurant.Menu;
import elacier.restaurant.Restaurant;
import elacier.service.IRestaurantService;
import elacier.service.RestaurantServiceDBImpl;

public class RestaurantServiceDBImplTest extends TestCase {
	
	IRestaurantService service;

	@Before
	protected void setUp() throws Exception {
		super.setUp();
		service = new RestaurantServiceDBImpl();
		
		Restaurant rest = new Restaurant();
		rest.setName("ssss");
		rest.setRate(2.5F);
		service.registerRestaurant(rest);
		
		Restaurant rest1 = new Restaurant();
		rest1.setName("ss2222");
		rest1.setRate(2.5F);
		rest1.addMenu(new Menu("m1,", 22F));
		rest1.addMenu(new Menu("m2,", 22F));
		rest1.addMenu(new Menu("m3,", 22F));
		rest1.addMenu(new Menu("m4,", 22F));
		
		service.registerRestaurant(rest1);
	}

	@Test
	public void testQueryRestaurantIntStringLocationInt() {
	}

	@Test
	public void testQueryRestaurantRestaurantQueryParameters() {
	}


	@Test
	public void testRegisterRestaurant() {
		Restaurant rest = new Restaurant();
		rest.setName("ssss");
		rest.setRate(2.5F);
		service.registerRestaurant(rest);
		
		
		Restaurant rest1 = new Restaurant();
		rest1.setName("ss2222");
		rest1.setRate(2.5F);
		rest1.addMenu(new Menu("m1,", 22F));
		rest1.addMenu(new Menu("m2,", 22F));
		rest1.addMenu(new Menu("m3,", 22F));
		rest1.addMenu(new Menu("m4,", 22F));
		
		service.registerRestaurant(rest1);
	}

	@Test
	public void testAddRestaurantMenu() {
		Restaurant rest = new Restaurant();
		rest.setName("test add menu");
		rest.setRate(2.5F);
		service.registerRestaurant(rest);
		List<Menu> menus = new ArrayList<Menu>();
		menus.add(new Menu("mm1,", 22F));
		menus.add(new Menu("mm2,", 22F));
		menus.add(new Menu("mm3,", 22F));
		menus.add(new Menu("mm4,", 22F));
		
		service.addRestaurantMenu(rest, menus);
	}

	@Test
	public void testRemoveRestaurantMenu() {
		List<Menu> list = service.queryRestaurantMenu(new Restaurant(2, null, null, 0), 0, 100);
		if (list != null && list.size() > 0) {
			service.removeRestaurantMenu(new Restaurant(2, null, null, 0), list.iterator().next());
		}
	}
	

}
