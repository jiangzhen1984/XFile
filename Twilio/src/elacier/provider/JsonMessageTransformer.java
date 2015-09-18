package elacier.provider;

import org.json.simple.JSONObject;

import elacier.provider.msg.BaseMessage;


/**
 * Use json as serialization
 * @author 28851274
 *
 */
public interface JsonMessageTransformer extends MessageTransformer {

	/**
	 * transform to json 
	 * @param message
	 * @return transformed json object
	 */
	public JSONObject transform(BaseMessage message);
	
	
	/**
	 * Transform json string to message object
	 * @param json
	 * @return
	 */
	public BaseMessage transform(String json);
}
