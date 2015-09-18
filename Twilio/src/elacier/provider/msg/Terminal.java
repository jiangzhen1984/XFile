package elacier.provider.msg;

/**
 * Terminal definition
 * @author 28851274
 *
 */
public abstract class Terminal {
	
	protected Token  deviceId;
	
	protected TerminalType type;

	/**
	 * Construct terminal according device Id and type
	 * @param deviceId
	 * @param type
	 */
	public Terminal(Token deviceId, TerminalType type) {
		this.deviceId = deviceId;
		this.type = type;
	}

	public TerminalType getType() {
		return type;
	}
	

	public Token getDeviceId() {
		return deviceId;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((deviceId == null) ? 0 : deviceId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Terminal other = (Terminal) obj;
		if (deviceId == null) {
			if (other.deviceId != null)
				return false;
		} else if (!deviceId.equals(other.deviceId))
			return false;
		return true;
	}

	
	
}
