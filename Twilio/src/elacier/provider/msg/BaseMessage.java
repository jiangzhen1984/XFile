package elacier.provider.msg;

import java.io.Serializable;
import java.util.Date;

import org.json.simple.JSONObject;

import elacier.provider.JsonMessageTransformer;

/**
 * Message for communication with restaurant
 * @author 28851274
 *
 */
public abstract class BaseMessage implements Serializable, JsonMessageTransformer {

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


	@SuppressWarnings("unchecked")
	@Override
	public JSONObject transform(JSONObject json) {
		if (json == null) {
			return null;
		}
		JSONObject header = new JSONObject();
		header.put("version", this.version.ordinal());
		header.put("type", this.type.ordinal());
		header.put("timestamp", this.timestamp.getTime());
		header.put("token", this.token);
		header.put("device", this.terminal.type.ordinal());
		header.put("device-id", this.terminal.deviceId);
		json.put("header", header);
		return json;
	}
	
	
	
	
}
