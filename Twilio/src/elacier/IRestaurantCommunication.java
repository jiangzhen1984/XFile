package elacier;

import java.util.List;

public interface IRestaurantCommunication {
	final static int RESTAURANT_COMM_OK=0;
	final static int RESTAURANT_COMM_NOK=1;

	
	public int queryRestaurantAvailable(List<Restaurant> restaurant,Transaction_Info trans_info );
	//public int sendFormToRestaurant(Restaurant restaurant, Menu menu );
	
	
	
}


