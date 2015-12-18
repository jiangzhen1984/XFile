package com.skyworld.service;

public class ServiceFactory {
	
	private static SWUserService eUserService;
	
	private static SWQuestionService eQuestionService;

	public ServiceFactory() {
	}
	
	
	public static SWUserService getESUserService() {
		if (eUserService == null) {
			eUserService = new SWUserService();
		}
		
		return eUserService;
	}
	
	
	
	public static SWQuestionService getQuestionService() {
		if (eQuestionService == null) {
			eQuestionService = new SWQuestionService();
		}
		
		return eQuestionService;
	}
	
	

}
