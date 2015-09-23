package elacier.service;

public class ServiceFactory {
	
	private static OrderService orderService;
	
	
	public static OrderService getOrderService() {
		if (orderService == null) {
			orderService = new OrderService();
		}
		return orderService;
	}

}
