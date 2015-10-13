package elacier.provider;

import java.util.ArrayList;
import java.util.List;

public class ProviderManager {
	
	private List<Provider> providers;

	private static ProviderManager instance;
	
	private ProviderManager() {
		providers = new ArrayList<Provider>();
	}
	
	
	public static ProviderManager getInstance() {
		if (instance == null) {
			instance = new ProviderManager(); 
		}
		return instance;
	}
	
	public List<Provider> getProviders() {
		return providers;
	}
	
	public void initAllProviders() {
		for (Provider provider : providers) {
			provider.init();
		}
	}
	
	
	public void releaseAllProviders() {
		for (Provider provider : providers) {
			provider.release();
		}
	}
	
	
	public void addProvider(Provider provider) {
		if (provider == null) {
			//FIXME log
			return;
		}
		this.providers.add(provider);
	}
	
}
