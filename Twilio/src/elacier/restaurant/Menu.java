package elacier.restaurant;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "E_RESTAURANT_MENU")
public class Menu {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private long menuId;
	
	@Column(name="NAME", columnDefinition="VARCHAR(400)")
	private String name;
	
	@Column(name="PRICE", columnDefinition="NUMERIC(5, 2)")
	private float price;
	
	@Column(name="KEY_WORD", columnDefinition="VARCHAR(800)")
	private String keyWord;
	
	
	@ManyToOne
	@JoinColumn(name = "E_RESTAURANT_ID", referencedColumnName = "ID", unique = false)
	private Restaurant restaurant;
	
	public Menu() {
		this(0, null, 0F);
	}
	
	
	public Menu( String name, float price) {
		this(0, name, price);
		this.name = name;
		this.price = price;
	}

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

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}


	public String getKeyWord() {
		return keyWord;
	}


	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	
	
	
	

	
	
}
