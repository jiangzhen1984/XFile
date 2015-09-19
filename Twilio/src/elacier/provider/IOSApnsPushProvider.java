package elacier.provider;

import java.io.IOException;

import elacier.provider.msg.BaseMessage;
import elacier.provider.msg.Terminal;

public class IOSApnsPushProvider implements PushProvider {

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void release() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean pushNotification(BaseMessage message, Terminal terminal)
			throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

}
