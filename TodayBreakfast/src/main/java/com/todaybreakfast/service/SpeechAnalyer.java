package com.todaybreakfast.service;

public interface SpeechAnalyer {
	
	/**
	 * Use to convert speech to text
	 * @param param
	 * @return
	 * @throws VoiceConverterException
	 */
	public String analyze(VoiceData param) throws VoiceConverterException;

}
