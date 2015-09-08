package elacier;


/**
 * Use to query restaurants parameters from database
 * @author jiangzhen
 *
 */
public class RestaurantQueryParameters {

	public int persons;
	
	public String favKey;
	
	public Location loc;
	
	//unit meters 500
	public int radius = 500;
	
	public int result = ElacierThread.PARSE_RESULT_FINISH;

	public RestaurantQueryParameters(int persons, String favKey, Location loc) {
		this(persons, favKey, loc, 500);
	}

	public RestaurantQueryParameters(int persons, String favKey, Location loc,
			int radius) {
		super();
		this.persons = persons;
		this.favKey = favKey;
		this.loc = loc;
		this.radius = radius;
	}

	public RestaurantQueryParameters(){
		result = ElacierThread.PARSE_RESULT_FINISH;
		persons = 4;
		favKey = "BBQ";
	}
	

	
	
}
