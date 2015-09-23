package elacier.service;

import java.util.ArrayList;
import java.util.List;

import elacier.IncorrectParameterException;
import elacier.Location;
import elacier.Restaurant;



public class RestaurantServiceDBImpl implements IRestaurantService {

	@Override
	public List<Restaurant> queryRestaurant(int persons, String favKey,
			Location loc, int radius) {
		//TODO query from cache 
		//

		//ArrayList<Restaurant> a1 = new ArrayList<Restaurant>();
		//a1.add(new Restaurant(101,"KevinBBQ",null));
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
	
	public RestaurantServiceDBImpl(){
		
		
	}

	

}
