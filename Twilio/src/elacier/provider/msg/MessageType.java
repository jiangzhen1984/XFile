package elacier.provider.msg;

/**
 * Message type definition.
 * @author 28851274
 *
 */
public enum MessageType {

	UNKNOWN, REQUEST, RESPONSE, NOTIFICATION;


	public static MessageType fromInt(int i) {
		switch (i) {
		case 1:
			return MessageType.REQUEST;
		case 2:
			return MessageType.RESPONSE;
		case 3:
			return MessageType.NOTIFICATION;
		default:
			return MessageType.UNKNOWN;
		}
	}
}
