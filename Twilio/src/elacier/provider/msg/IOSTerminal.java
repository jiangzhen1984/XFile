package elacier.provider.msg;

import elacier.transaction.Token;

public class IOSTerminal extends Terminal {

	public IOSTerminal(Token deviceId) {
		super(deviceId, TerminalType.IOS);
	}

}
