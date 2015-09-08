package elacier;

public interface QueryParametersAnalyzer {
	
	/**
	 * Convert text to query parameters
	 * @param text
	 * @return
	 * @throws TextParserException
	 * 
	 * @see RestaurantQueryParameters
	 */
	public RestaurantQueryParameters analyze(String [] text,int length) throws TextParserException;

}
