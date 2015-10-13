package elacier.provider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

public class ProviderFactory {
	
	
	
	public static final int PROVIDER_TYPE_IOS = 1;
	public static final int PROVIDER_TYPE_ANDROID = 2;
	public static final int PROVIDER_TYPE_NATIVE = 3;
	
	
	
	public static Provider createProvider(int type, ProviderConfig config) {
		if (type == PROVIDER_TYPE_IOS) {
			//TODO check use ssl flag
			String strfile = config.getCertFile();
			File file = new File(strfile);
			if (!file.exists()) {
				throw new RuntimeException("No cert file");
			}
			
			InputStream certIn = null;
			
	        try {
	        	certIn = new FileInputStream(file);
				
				
				 KeyStore keyStore = KeyStore.getInstance("PKCS12");

		        keyStore.load(certIn, config.getPassword().toCharArray());
		        KeyManagerFactory keyMgrFactory = KeyManagerFactory.getInstance("SunX509");
		        
				keyMgrFactory.init(keyStore, config.getPassword().toCharArray());
				  SSLContext sslContext = SSLContext.getInstance("TLS");
		        sslContext.init(keyMgrFactory.getKeyManagers(), null, null);
		        
		        SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
		        new IOSApnsPushProvider(config, sslSocketFactory);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (certIn != null) {
					try {
						certIn.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

	      
		        
			//TODO check configuration properties
			return new IOSApnsPushProvider(config, null);
		} else if (type == PROVIDER_TYPE_ANDROID) {
			throw new RuntimeException("Doesn't support type yet");
		} else if (type == PROVIDER_TYPE_NATIVE) {
			throw new RuntimeException("Doesn't support type yet");
		} else {
			throw new RuntimeException("Unsupport type");
		}
	}
	
	

}
