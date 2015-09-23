package elacier.service;

import java.util.Iterator;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;

import elacier.order.Order;
import elacier.order.OrderItem;

public class OrderService extends BaseService {

	public void addOrder(Order order) {
		Session session = openSession();
		Transaction trans = session.beginTransaction();
		session.save(order);
		Set<OrderItem> sets = order.getItems();
		for(Iterator<OrderItem> it = sets.iterator(); it.hasNext();) {
			OrderItem oi = it.next();
			session.save(oi);
		}
		trans.commit();
		session.close();
	}
	
	
	public void updateOrderState(Order order) {
		Session session = openSession();
		Order cache = (Order)session.load(Order.class, order.getId());
		cache.setState(order.getState());
		Transaction trans = session.beginTransaction();
		session.update(cache);
		trans.commit();
		session.close();
	}
}
