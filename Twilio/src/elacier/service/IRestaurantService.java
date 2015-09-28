package elacier.service;

import java.util.List;

import elacier.Location;
import elacier.restaurant.Menu;
import elacier.restaurant.Restaurant;

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
	
	
	
	public void registerRestaurant(Restaurant rest);

	
	
	public void addRestaurantMenu(Restaurant rest, List<Menu> menus);
	
	
	
	public void removeRestaurantMenu(Restaurant rest, Menu menu);
	
	
	public List<Menu> queryRestaurantMenu(Restaurant rest, int start, int fetchCount);
}
