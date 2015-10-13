package elacier.provider;

import java.io.IOException;
import java.util.List;

import elacier.provider.msg.BaseMessage;
import elacier.provider.msg.Terminal;

public interface PushProvider extends Provider {
	
	
	/**
	 * 
	 * @param message
	 * @param terminal
	 * @return
	 * @throws IOException
	 */
	public boolean pushNotification(BaseMessage message, Terminal terminal) throws IOException;
	
	
	
	/**
	 * 
	 * @param message
	 * @param terminal
	 * @return
	 * @throws IOException
	 */
	public boolean pushNotification(BaseMessage message, List<Terminal> terminal) throws IOException;

}
