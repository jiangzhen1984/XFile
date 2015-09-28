package elacier.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import elacier.Location;
import elacier.restaurant.Menu;
import elacier.restaurant.Restaurant;



public class RestaurantServiceDBImpl extends BaseService implements IRestaurantService {


	
	public RestaurantServiceDBImpl(){
		
		
	}
	
	@Override
	public List<Restaurant> queryRestaurant(int persons, String favKey,
			Location loc, int radius) {
		return null;
	}

	@Override
	public List<Restaurant> queryRestaurant(RestaurantQueryParameters queryPara) {
		return null;
	}

	public List<Restaurant> queryRestaurant(int start, int fetchCount) {
		Session session = openSession();
		Query query = session.createQuery(" from Restaurant order by id asc ");
		query.setMaxResults(fetchCount);
		query.setFirstResult(start);
		
		List<Restaurant> list = (List<Restaurant>)query.list();
		List<Restaurant> cacheList = new ArrayList<Restaurant>(list.size());
		for (Restaurant r : list) {
			Restaurant cacheM = new Restaurant();
			cacheM.setKeyWord(r.getKeyWord());
			cacheM.setRestId(r.getRestId());
			cacheM.setLoct(new Location(r.getLat(), r.getLng()));
			cacheM.setName(r.getName());
			cacheM.setRate(r.getRate());
			cacheList.add(cacheM);
		}
		session.close();
		return cacheList;
	}
	
	
	
	public void registerRestaurant(Restaurant rest) {
		if (rest == null) {
			throw new NullPointerException(" restaurant is null");
		}
		Session session = openSession();
		Transaction trans = session.beginTransaction();
		session.save(rest);
		trans.commit();
		session.close();
	}

	
	
	public void addRestaurantMenu(Restaurant rest, List<Menu> menus) {
		if (rest == null) {
			throw new NullPointerException(" restaurant or menus is null");
		}
		
		Session session = openSession();
		Transaction trans = session.beginTransaction();
		for (Menu m : menus) {
			m.setRestaurant(rest);
			session.save(m);
		}
		trans.commit();
		session.close();
	}
	
	
	
	public void removeRestaurantMenu(Restaurant rest, Menu menu) {
		if (rest == null) {
			throw new NullPointerException(" restaurant or menu is null");
		}

		Session session = openSession();
		Transaction trans = session.beginTransaction();
		session.delete(menu);
		trans.commit();
		session.flush();
		session.close();
	}
	
	
	public List<Menu> queryRestaurantMenu(Restaurant rest, int start, int fetchCount) {
		if (rest == null) {
			return null;
		}
		Session session = openSession();
		Query query = session.createQuery(" from elacier.restaurant.Menu as m where m.restaurant.id =:rid");
		query.setLong("rid", rest.getRestId());
		query.setMaxResults(fetchCount);
		query.setFirstResult(start);
		
		List<Menu> list = (List<Menu>)query.list();
		List<Menu> cacheList = new ArrayList<Menu>(list.size());
		for (Menu m : list) {
			Menu cacheM = new Menu();
			cacheM.setMenuId(m.getMenuId());
			cacheM.setKeyWord(m.getKeyWord());
			cacheM.setPrice(m.getPrice());
			cacheM.setName(m.getName());
			cacheList.add(m);
		}
		session.close();
		return cacheList;
	}
	

}
