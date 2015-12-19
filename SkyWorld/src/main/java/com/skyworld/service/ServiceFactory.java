package com.skyworld.service;

import com.skyworld.easemob.EaseMobDeamon;

public class ServiceFactory {
	
	private static SWUserService eUserService;
	
	private static SWQuestionService eQuestionService;
	
	private static EaseMobDeamon  mEaseMobDeamon;

	public ServiceFactory() {
	}
	
	
	public static EaseMobDeamon getEaseMobService() {
		if (mEaseMobDeamon == null) {
			mEaseMobDeamon = new EaseMobDeamon();
		}
		
		return mEaseMobDeamon;
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
