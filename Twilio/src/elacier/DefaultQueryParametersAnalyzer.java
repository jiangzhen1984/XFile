package elacier;

import elacier.service.RestaurantQueryParameters;

public class DefaultQueryParametersAnalyzer implements QueryParametersAnalyzer {

	@Override
	public RestaurantQueryParameters analyze(String [] text,int length)
			throws TextParserException {
		// TODO Auto-generated method stub
		return new RestaurantQueryParameters();
	}

	public DefaultQueryParametersAnalyzer(){

	}

}
