package elacier.provider;

import org.json.simple.JSONObject;


/**
 * Use json as serialization
 * @author 28851274
 *
 */
public interface JsonMessageTransformer extends MessageTransformer {

	/**
	 * transform to json 
	 * @param json
	 * @return transformed json object
	 */
	public JSONObject transform(JSONObject json);
}
