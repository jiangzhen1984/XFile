package elacier.provider.msg;

/**
 * Terminal type definition
 * @author 28851274
 *
 */
public enum TerminalType {
	
	UNKNOWN, SERVER, IOS, ANDROID;


	public static TerminalType fromInt(int i) {
		switch (i) {
		case 1:
			return TerminalType.SERVER;
		case 2:
			return TerminalType.IOS;
		case 3:
			return TerminalType.ANDROID;
		default:
			return TerminalType.UNKNOWN;
		}
	}

}
