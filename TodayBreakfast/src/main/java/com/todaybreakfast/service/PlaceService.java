package com.todaybreakfast.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.todaybreakfast.model.Place;

public class PlaceService extends BaseService {

	private static List<Place> list;

	public List<Place> getPlaceList() {
		if (list == null) {
			Session session = this.getSessionFactory().openSession();
			Query query = session.createQuery("from Place");
			List<Place> dbList = (List<Place>) query.list();
			list = new ArrayList<Place>(dbList.size());
			for (int i = 0; i < dbList.size(); i++) {
				Place dbPlace = dbList.get(i);
				Place cachePlace = new Place(dbPlace);
				list.add(cachePlace);
			}
			session.close();

		}
		return list;
	}

	public void addPlace(Place place) {
		Session session = this.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.save(place);
		t.commit();
		session.close();
		if (list != null) {
			list.add(place);
		}
	}

	public void removePlace(long id) {
		Session session = this.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Object obj = session.get(Place.class, id);
		session.delete(obj);
		t.commit();
		session.close();
		for (int i = 0; list != null && i < list.size(); i++) {
			if (list.get(i).getId() == id) {
				list.remove(i);
			}
		}
	}

	public void removePlace(Place place) {
		removePlace(place.getId());
	}

}
