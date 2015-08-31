package com.todaybreakfast.service;

public class IncorrectParameterException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8477898677030976951L;

	public IncorrectParameterException() {
		super();
	}

	public IncorrectParameterException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public IncorrectParameterException(String message, Throwable cause) {
		super(message, cause);
	}

	public IncorrectParameterException(String message) {
		super(message);
	}

	public IncorrectParameterException(Throwable cause) {
		super(cause);
	}
	
	

}
