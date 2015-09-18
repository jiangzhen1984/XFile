package com.elacier.testcase;

import junit.framework.TestCase;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

import elacier.provider.msg.BaseMessage;
import elacier.provider.msg.InquiryMessage;
import elacier.provider.msg.InquiryNotification;
import elacier.provider.msg.InquiryRespondNotification;
import elacier.provider.msg.InquiryResponse;
import elacier.provider.msg.OrderNotificaiton;
import elacier.provider.msg.OrderRespondNotification;
import elacier.provider.msg.OrderResponse;
import elacier.provider.msg.ServerTerminal;
import elacier.provider.msg.StringToken;

public class JSONTransformerTest extends TestCase{
	
	BaseMessage inquiryNotification;
	BaseMessage inquiryRespondNotification;
	BaseMessage inquiryResponse;
	
	
	BaseMessage orderNotification;
	BaseMessage orderRespondNotification;
	BaseMessage orderResponse;
	

	@Before
	public void setUp() throws Exception {
		inquiryNotification = new InquiryNotification(new StringToken("a"),
				new ServerTerminal(new StringToken("s-a")), 001L, InquiryMessage.INQUIRY_OPT_TYPE_ON_SITE);
		
		
		inquiryRespondNotification  = new InquiryRespondNotification(new StringToken("a"),
				new ServerTerminal(new StringToken("s-a")), 001L, InquiryRespondNotification.INQUIRY_RESPOND_NOTIFICAITON_RET_OK);
		
		inquiryResponse  = new InquiryResponse(new StringToken("a"),
				new ServerTerminal(new StringToken("s-a")), 001L, InquiryResponse.INQUIRY_RESPOND_CLIENT_RESPONSE_FAILED);
		
		
		orderNotification = new OrderNotificaiton(new StringToken("a"),
				new ServerTerminal(new StringToken("s-a")), 001L, 002L);
		
		((OrderNotificaiton)orderNotification).addSelectionMenu(1, "sss");
		((OrderNotificaiton)orderNotification).addSelectionMenu(2, "bbb");
		
		orderRespondNotification = new OrderRespondNotification(new StringToken("a"),
				new ServerTerminal(new StringToken("s-a")), 001L, 002L, OrderRespondNotification.RESTAURANT_RESPONSE_RET_OK);
		
		
		orderResponse = new OrderResponse(new StringToken("a"),
				new ServerTerminal(new StringToken("s-a")), 001L, 002L, OrderResponse.RESPONSE_RET_OK);

	}

	@Test
	public void testMessageTransform() {
		JSONObject js = inquiryNotification.transform(null);
		assertTrue(js == null);
		JSONObject json = new JSONObject();
		inquiryNotification.transform(json);
		String strJson = json.toJSONString();
		assertTrue( strJson.indexOf("header") != -1);
		assertTrue( strJson.indexOf("body") != -1);
		
		json.clear();
		js = inquiryRespondNotification.transform(null);
		assertTrue(js == null);
		inquiryRespondNotification.transform(json);
		strJson = json.toJSONString();
		assertTrue( strJson.indexOf("header") != -1);
		assertTrue( strJson.indexOf("body") != -1);
		
		json.clear();
		js = inquiryResponse.transform(null);
		assertTrue(js == null);
		inquiryResponse.transform(json);
		strJson = json.toJSONString();
		assertTrue( strJson.indexOf("header") != -1);
		assertTrue( strJson.indexOf("body") != -1);
		
		
		json.clear();
		js = orderNotification.transform(null);
		assertTrue(js == null);
		orderNotification.transform(json);
		strJson = json.toJSONString();
		assertTrue( strJson.indexOf("header") != -1);
		assertTrue( strJson.indexOf("body") != -1);
		assertTrue( strJson.indexOf("bbb") != -1);
		assertTrue( strJson.indexOf("sss") != -1);
		
		
		json.clear();
		js = orderRespondNotification.transform(null);
		assertTrue(js == null);
		orderRespondNotification.transform(json);
		strJson = json.toJSONString();
		assertTrue( strJson.indexOf("header") != -1);
		assertTrue( strJson.indexOf("body") != -1);
		assertTrue( strJson.indexOf("ret") != -1);
		
		json.clear();
		js = orderResponse.transform(null);
		assertTrue(js == null);
		orderResponse.transform(json);
		strJson = json.toJSONString();
		assertTrue( strJson.indexOf("header") != -1);
		assertTrue( strJson.indexOf("body") != -1);
		assertTrue( strJson.indexOf("ret") != -1);
		
		
	}

}

