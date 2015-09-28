package elacier.service;

import java.util.List;

import elacier.IncorrectParameterException;
import elacier.Location;
import elacier.cache.GlobalCacheHolder;
import elacier.restaurant.Menu;
import elacier.restaurant.Restaurant;

public class RestaurantServiceProxy implements IRestaurantService {
	
	
	private RestaurantServiceDBImpl impl;
	
	
	public RestaurantServiceProxy(RestaurantServiceDBImpl impl) {
		super();
		this.impl = impl;
	}

	@Override
	public List<Restaurant> queryRestaurant(int persons, String favKey,
			Location loc, int radius) {
		// TODO Auto-generated method stub
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

	@Override
	public void registerRestaurant(Restaurant rest) {
		impl.registerRestaurant(rest);
		GlobalCacheHolder.getInstance().addRestaurant(rest);
	}

	@Override
	public void addRestaurantMenu(Restaurant rest, List<Menu> menus) {
		impl.addRestaurantMenu(rest, menus);
		for(Menu m : menus) {
			rest.addMenu(m);
		}
	}

	@Override
	public void removeRestaurantMenu(Restaurant rest, Menu menu) {
		impl.removeRestaurantMenu(rest, menu);
		rest.removeMenu(menu);
	}

	@Override
	public List<Menu> queryRestaurantMenu(Restaurant rest, int start,
			int fetchCount) {
		if (rest == null) {
			return null;
		}
		if (fetchCount <= 0 || start <= 0) {
			return null;
		}
		Restaurant cache =  GlobalCacheHolder.getInstance().getRestaurant(rest.getRestId());
		List<Menu> list = cache.getMenuList();
		int size = list.size();
		if (size >= start + fetchCount) {
			return list.subList(start, start + fetchCount);
		} else {
			return list.subList(start, size - (start + fetchCount - size));
		}
	}

}
