package elacier.cache;

import java.util.List;

import elacier.Restaurant;
import elacier.provider.msg.Terminal;


public class GlobalCacheHolder {
	
	
	
	private static GlobalCacheHolder instance;
	
	private GlobalCacheHolder() {
		
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
		
	}

}
