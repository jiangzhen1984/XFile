package elacier.provider.msg;

import java.io.Serializable;
import java.util.Date;


/**
 * Message for communication with restaurant
 * @author 28851274
 *
 */
public abstract class BaseMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1863778571095557161L;

	/**
	 * Mark current message version number
	 */
	protected MessageVersion version;
	
	/**
	 * Mark current message type
	 */
	protected MessageType type;
	
	/**
	 * Mark current message time stamp
	 */
	protected Date timestamp;
	
	/**
	 * Mark for further use
	 */
	protected Token token;
	
	/**
	 * Mark for sender terminal
	 */
	protected Terminal terminal;
	
	/**
	 * Mark current message unique id
	 */
	protected String uuid;
	
	
	
	

	protected BaseMessage(MessageVersion version, MessageType type,
			Date timestamp, Token token, Terminal terminal, String uuid) {
		super();
		this.version = version;
		this.type = type;
		this.timestamp = timestamp;
		this.token = token;
		this.terminal = terminal;
		this.uuid = uuid;
	}
	
	
	protected BaseMessage(MessageVersion version, MessageType type,
			Date timestamp, Token token, Terminal terminal) {
		super();
		this.version = version;
		this.type = type;
		this.timestamp = timestamp;
		this.token = token;
		this.terminal = terminal;
	}


	public MessageVersion getVersion() {
		return version;
	}


	public void setVersion(MessageVersion version) {
		this.version = version;
	}


	public MessageType getType() {
		return type;
	}


	public void setType(MessageType type) {
		this.type = type;
	}


	public Date getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}


	public String getUuid() {
		return uuid;
	}
	
	void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	


	public Terminal getTerminal() {
		return terminal;
	}


	public void setTerminal(Terminal terminal) {
		this.terminal = terminal;
	}


	public Token getToken() {
		return token;
	}

	
}
