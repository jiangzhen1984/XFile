package elacier;

public class Menu {
	
	private long menuId;
	
	private String name;
	
	private float price;
	
	

	public Menu(long menuId, String name, float price) {
		super();
		this.menuId = menuId;
		this.name = name;
		this.price = price;
	}

	public long getMenuId() {
		return menuId;
	}

	public void setMenuId(long menuId) {
		this.menuId = menuId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
	
	
	

	
	
}
