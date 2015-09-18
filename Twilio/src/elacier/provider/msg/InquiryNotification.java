package elacier.provider.msg;

import java.util.Date;
import java.util.UUID;

import org.json.simple.JSONObject;

import elacier.transaction.Token;

public class InquiryNotification extends InquiryMessage {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6099713579437540511L;

	private int inquiryType;
	
	private String guestPhone;
	
	private String guestName;
	
	private int numOfPeople;
	
	private String word;
	
	private double locationLat;
	
	private double locationLng;

	public InquiryNotification(Token token, Terminal terminal,
			long transactionId, int inquiryType) {
		super(MessageVersion.V01, MessageType.NOTIFICATION, new Date(), token,
				terminal, UUID.randomUUID().toString(), transactionId,
				InquiryMessage.OPT_SERVER_SEND_NOTIFICATION);
		this.inquiryType = inquiryType;
	}

	public int getInquiryType() {
		return inquiryType;
	}

	public void setInquiryType(int inquiryType) {
		this.inquiryType = inquiryType;
	}

	public String getGuestPhone() {
		return guestPhone;
	}

	public void setGuestPhone(String guestPhone) {
		this.guestPhone = guestPhone;
	}

	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	public int getNumOfPeople() {
		return numOfPeople;
	}

	public void setNumOfPeople(int numOfPeople) {
		this.numOfPeople = numOfPeople;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public double getLocationLat() {
		return locationLat;
	}

	public void setLocationLat(double locationLat) {
		this.locationLat = locationLat;
	}

	public double getLocationLng() {
		return locationLng;
	}

	public void setLocationLng(double locationLng) {
		this.locationLng = locationLng;
	}

	

}
