package elacier.provider;

import org.json.simple.JSONObject;

import elacier.provider.msg.BaseMessage;

public class APNSPayloadJsonTransformer extends JsonTransformImpl {
	
	
	@Override
	public JSONObject transform(BaseMessage message) {
		//TODO add apns header
		return super.transform(message);
	}

	@Override
	public BaseMessage transform(String data) {
		
		return super.transform(data);
	}

}
