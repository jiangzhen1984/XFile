package com.skyworld.service;

public class ServiceFactory {
	
	private static SWUserService eUserService;

	public ServiceFactory() {
	}
	
	
	public static SWUserService getESUserService() {
		if (eUserService == null) {
			eUserService = new SWUserService();
		}
		
		return eUserService;
	}
	
	

}
