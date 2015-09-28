package elacier.restaurant;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import elacier.Location;


@Entity
@Table(name = "E_RESTAURANT")
public class Restaurant {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private int mRestId;
	
	@Column(name="NAME", columnDefinition="VARCHAR(400)")
	private String mName;
	
	@Transient
	private Location mLoct;
	
	@Column(name="REST_RATE", columnDefinition="NUMERIC(3, 1)")
	private float mRate;
	
	@Column(name="LAT", columnDefinition="DOUBLE")
	private double lat;
	
	@Column(name="LNG", columnDefinition="DOUBLE")
	private double lng;
	
	@Column(name="KEY_WORD", columnDefinition="VARCHAR(800)")
	private String keyWord;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "restaurant")
	private List<Menu> mMenuList;
	
	
	public Restaurant() {
		mMenuList = new ArrayList<Menu>();
	}
	
	
	public Restaurant(int mRestId, String mName, Location mLoct, float mRate) {
		this();
		this.mRestId = mRestId;
		this.mName = mName;
		this.mLoct = mLoct;
		this.mRate = mRate;
		if (mLoct != null) {
			this.lat = mLoct.lat;
			this.lng = mLoct.lng;
		}
	}

	public Restaurant(int mRestId, String mName, Location mLoct) {
		this(mRestId, mName, mLoct, 0.0F);
	}

	public int getRestId() {
		return mRestId;
	}

	public void setRestId(int mRestId) {
		this.mRestId = mRestId;
	}

	public String getName() {
		return mName;
	}

	public void setName(String mName) {
		this.mName = mName;
	}

	public Location getLoct() {
		return mLoct;
	}

	public void setLoct(Location mLoct) {
		this.mLoct = mLoct;
	}

	public float getRate() {
		return mRate;
	}

	public void setRate(float mRate) {
		this.mRate = mRate;
	}
	
	
	

	
	public double getLat() {
		return lat;
	}


	public void setLat(double lat) {
		this.lat = lat;
	}


	public double getLng() {
		return lng;
	}


	public void setLng(double lng) {
		this.lng = lng;
	}

	
	

	public String getKeyWord() {
		return keyWord;
	}


	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}


	public void  addMenu(Menu m) {
		if (m == null) {
			throw new RuntimeException(" Menu can not be null");
		}
		mMenuList.add(m);
		m.setRestaurant(this);
	}
	
	public void removeMenu(Menu m) {
		mMenuList.remove(m);
		m.setRestaurant(null);
	}
	
	
	public List<Menu> getMenuList() {
		return this.mMenuList;
	}
}
