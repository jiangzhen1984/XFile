package com.todaybreakfast.service;

import java.util.List;

import com.todaybreakfast.model.vo.Location;
import com.todaybreakfast.model.vo.Restaurant;

public interface IRestaurantService {
	
	
	/**
	 * Query restaurants according parameters
	 * @param persons  number of attendee
	 * @param favKey   key words
	 * @param loc  near by location
	 * @param radius  radius of location
	 * @return 
	 */
	public List<Restaurant> queryRestaurant(int persons, String favKey, Location loc, int radius);

	
	/**
	 *  Query restaurants according parameters
	 * @param queryPara
	 * @return
	 */
	public List<Restaurant> queryRestaurant(RestaurantQueryParameters queryPara);
	
}
