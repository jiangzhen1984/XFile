package elacier;

import java.util.List;

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
