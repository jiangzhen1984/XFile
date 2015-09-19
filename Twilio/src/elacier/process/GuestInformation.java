package elacier.process;

import java.util.ArrayList;
import java.util.List;

import elacier.Menu;

public class GuestInformation {
	
	private String guestName;
	
	private String guestPhone;
	
	private String fav;
	private String location;
	private int nums;
	
	private int type;
	
	private List<Menu> menus;
	
	
	

	public GuestInformation() {
		super();
		menus = new ArrayList<Menu>();
	}

	public String getFav() {
		return fav;
	}

	public void setFav(String fav) {
		this.fav = fav;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getNums() {
		return nums;
	}

	public void setNums(int nums) {
		this.nums = nums;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}
	
	
	
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void addMenu(Menu menu) {
		this.menus.add(menu);
	}
	
	public void removeMenu(Menu menu) {
		this.menus.remove(menu);
	}

	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	public String getGuestPhone() {
		return guestPhone;
	}

	public void setGuestPhone(String guestPhone) {
		this.guestPhone = guestPhone;
	}
	
	
	

}
