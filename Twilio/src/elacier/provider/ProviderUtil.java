package elacier.provider;

import java.io.IOException;
import java.util.List;

import elacier.provider.msg.BaseMessage;
import elacier.provider.msg.Terminal;

public class ProviderUtil {

	
	public static  boolean pushNotification(BaseMessage message, List<Terminal> terminals) throws IOException {
		for (Terminal terminal : terminals) {
			pushNotification(message, terminal);
		}
		return true;
	}
	
	public static  boolean pushNotification(BaseMessage message, Terminal terminal) throws IOException {
		
		return true;
	}
}
