package com.todaybreakfast.service;

import java.util.List;

import com.todaybreakfast.model.vo.Location;
import com.todaybreakfast.model.vo.Restaurant;

public class RestaurantServiceDBImpl implements IRestaurantService {

	@Override
	public List<Restaurant> queryRestaurant(int persons, String favKey,
			Location loc, int radius) {
		//TODO query from cache 
		//
		return null;
	}

	@Override
	public List<Restaurant> queryRestaurant(RestaurantQueryParameters queryPara) {
		if (queryPara == null) {
			throw new NullPointerException(" queryPara is null");
		}
		if (queryPara.persons <= 0) {
			throw new IncorrectParameterException(" persons paramter is incorrect");
		}
		
		if (queryPara.favKey == null || queryPara.favKey.isEmpty()) {
			throw new IncorrectParameterException(" favKey paramter is incorrect");
		}
		return queryRestaurant(queryPara.persons, queryPara.favKey, queryPara.loc, queryPara.radius);
	}

}
