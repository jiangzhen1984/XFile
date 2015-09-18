package elacier.provider.msg;

import elacier.transaction.Token;

public class AndroidTerminal extends Terminal {

	public AndroidTerminal(Token deviceId) {
		super(deviceId, TerminalType.ANDROID);
	}

}
