package elacier;

import java.util.List;

import elacier.restaurant.Restaurant;

public class Transaction_Info {

	public String trans_id;
	public List<Restaurant> shop_list;
	public String From;
	public Location loc;
	public int persons;
	public Restaurant bingo;
	
	public int token;
	public Object lock;
	
	public Transaction_Info() {
		// TODO Auto-generated constructor stub
	}

}
