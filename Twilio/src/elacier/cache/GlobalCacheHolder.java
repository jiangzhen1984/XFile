package elacier.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import elacier.provider.msg.Terminal;
import elacier.restaurant.Restaurant;


public class GlobalCacheHolder {
	
	
	private Map<Long, Restaurant> maps;
	private List<Restaurant> allRestaurantList;
	
	
	private static GlobalCacheHolder instance;
	
	private GlobalCacheHolder() {
		allRestaurantList = new ArrayList<Restaurant>();
		maps = new ConcurrentHashMap<Long, Restaurant>();
	}
	
	
	public static GlobalCacheHolder getInstance() {
		if (instance == null) {
			instance = new GlobalCacheHolder();
		}
		return instance;
	}
	
	
	
	public Restaurant getRestaurant(long id) {
		return null;
	}
	
	public Restaurant getRestaurant(Terminal termianl) {
		return null;
	}
	
	
	public Terminal getRestaurantTerminal(long restaurantId) {
		return null;
	}
	
	
	public void updateRestaurantTerminal(Restaurant restau, Terminal terminal) {
		
	}
	
	public void removeRestaurantTerminal(Restaurant restar) {
		
	}
	
	public void initRestaurantList(List<Restaurant> list) {
		this.allRestaurantList = list;
		for (Restaurant r : list) {
			maps.put((long)r.getRestId(), r);
		}
	}
	
	public void addRestaurantList(List<Restaurant> list) {
		this.allRestaurantList.addAll(list);
		for (Restaurant r : list) {
			maps.put((long)r.getRestId(), r);
		}
	}
	
	public List<Restaurant> getAllList() {
		return allRestaurantList;
	}

	
	public void addRestaurant(Restaurant r) {
		if (r == null || r.getRestId() <= 0) {
			throw new IllegalArgumentException(" r is incorrect ");
		}
		this.allRestaurantList.add(r);
		maps.put((long)r.getRestId(), r);
	}
	
	public Restaurant getResturant(long id) {
		return maps.get(id);
	}
}
