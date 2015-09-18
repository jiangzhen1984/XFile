package com.elacier.testcase;

import junit.framework.TestCase;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

import elacier.provider.JsonMessageTransformer;
import elacier.provider.JsonTransformImpl;
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
	
	
	JsonMessageTransformer transformer; 

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
		
		transformer = new JsonTransformImpl();

	}

	@Test
	public void testMessageTransform() {
		JSONObject json = transformer.transform(inquiryNotification);
		String strJson = json.toJSONString();
		assertTrue( strJson.indexOf("header") != -1);
		assertTrue( strJson.indexOf("body") != -1);
		
		 json = transformer.transform(inquiryRespondNotification);
		strJson = json.toJSONString();
		assertTrue( strJson.indexOf("header") != -1);
		assertTrue( strJson.indexOf("body") != -1);
		
		 json = transformer.transform(inquiryResponse);
		strJson = json.toJSONString();
		assertTrue( strJson.indexOf("header") != -1);
		assertTrue( strJson.indexOf("body") != -1);
		
		
		 json = transformer.transform(orderNotification);
		strJson = json.toJSONString();
		assertTrue( strJson.indexOf("header") != -1);
		assertTrue( strJson.indexOf("body") != -1);
		assertTrue( strJson.indexOf("bbb") != -1);
		assertTrue( strJson.indexOf("sss") != -1);
		
		
		 json = transformer.transform(orderRespondNotification);
		strJson = json.toJSONString();
		assertTrue( strJson.indexOf("header") != -1);
		assertTrue( strJson.indexOf("body") != -1);
		assertTrue( strJson.indexOf("ret") != -1);
		
		 json = transformer.transform(orderResponse);
		strJson = json.toJSONString();
		assertTrue( strJson.indexOf("header") != -1);
		assertTrue( strJson.indexOf("body") != -1);
		assertTrue( strJson.indexOf("ret") != -1);
		
		
	}

}

