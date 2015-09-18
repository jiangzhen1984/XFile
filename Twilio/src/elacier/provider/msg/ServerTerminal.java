package elacier.provider.msg;

import elacier.transaction.Token;

public class ServerTerminal extends Terminal {

	public ServerTerminal(Token deviceId) {
		super(deviceId, TerminalType.SERVER);
	}

}
