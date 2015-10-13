package elacier.process;

import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import elacier.cache.GlobalCacheHolder;
import elacier.provider.Provider;
import elacier.provider.ProviderFactory;
import elacier.provider.ProviderManager;
import elacier.restaurant.Menu;
import elacier.restaurant.Restaurant;
import elacier.service.RestaurantServiceDBImpl;

public class LoaderServlet extends HttpServlet {
	
	

	@Override
	public void destroy() {
		super.destroy();
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		RestaurantServiceDBImpl service = new RestaurantServiceDBImpl();
		int fetchCount = 500;
		int index = 0;
		List<Restaurant> restList = null;
		do {
			restList = service.queryRestaurant(index * fetchCount, fetchCount);
			GlobalCacheHolder.getInstance().addRestaurantList(restList);
			if (restList != null && restList.size() > 0) {
				for (Restaurant rest : restList) {
					List<Menu> mList = service.queryRestaurantMenu(rest, 0, fetchCount);
					for (int i = 0; mList != null && i < mList.size(); i++) {
						rest.addMenu(mList.get(i));
					}
				}
			}
		} while (restList != null && restList.size() >= fetchCount);
		
		
		
		//TODO load ios providers
		Provider iosProvider = ProviderFactory.createProvider(ProviderFactory.PROVIDER_TYPE_IOS, null);
		ProviderManager.getInstance().addProvider(iosProvider);
		
	}

	


}
