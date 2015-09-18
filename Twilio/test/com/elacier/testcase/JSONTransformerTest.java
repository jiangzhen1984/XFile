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
import elacier.provider.msg.OrderMessage;
import elacier.provider.msg.OrderNotificaiton;
import elacier.provider.msg.OrderRespondNotification;
import elacier.provider.msg.OrderResponse;
import elacier.provider.msg.ServerTerminal;
import elacier.transaction.StringToken;

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
		
		
		inquiryRespondNotification  = new InquiryRespondNotification(new StringToken("a1"),
				new ServerTerminal(new StringToken("s-a")), 002L, InquiryRespondNotification.INQUIRY_RESPOND_NOTIFICAITON_RET_OK);
		
		inquiryResponse  = new InquiryResponse(new StringToken("a2"),
				new ServerTerminal(new StringToken("s-a")), 003L, InquiryResponse.INQUIRY_RESPOND_CLIENT_RESPONSE_FAILED);
		
		
		orderNotification = new OrderNotificaiton(new StringToken("OrderNotificaiton"),
				new ServerTerminal(new StringToken("s-a")), 001L, 032L);
		((OrderMessage)orderNotification).setState(OrderMessage.OrderState.ONLINE_PAID);
		
		((OrderNotificaiton)orderNotification).addSelectionMenu(1, "sss");
		((OrderNotificaiton)orderNotification).addSelectionMenu(2, "bbb");
		
		orderRespondNotification = new OrderRespondNotification(new StringToken("OrderRespondNotification"),
				new ServerTerminal(new StringToken("s-a")), 001L, 102L, OrderRespondNotification.RESTAURANT_RESPONSE_RET_BUSY);
		
		
		orderResponse = new OrderResponse(new StringToken("OrderResponse"),
				new ServerTerminal(new StringToken("OrderResponses-a")), 032L, 502L, OrderResponse.RESPONSE_RET_OK);
		
		transformer = new JsonTransformImpl();

	}

	@Test
	public void testMessageTransform() {
		JSONObject json = transformer.transform(inquiryNotification);
		String strJson = json.toJSONString();
		assertTrue( strJson.indexOf("header") != -1);
		assertTrue( strJson.indexOf("body") != -1);
		
		BaseMessage bm = transformer.transform(json.toJSONString());
		assertTrue(bm instanceof InquiryNotification);
		assertTrue(((InquiryNotification)bm).getToken().equals(new StringToken("a")));
		assertTrue(((InquiryNotification)bm).getInquiryType() == InquiryMessage.INQUIRY_OPT_TYPE_ON_SITE);
		assertTrue(((InquiryNotification)bm).getTransactionId() == 001);
		
		 json = transformer.transform(inquiryRespondNotification);
		strJson = json.toJSONString();
		assertTrue( strJson.indexOf("header") != -1);
		assertTrue( strJson.indexOf("body") != -1);
		
		bm = transformer.transform(json.toJSONString());
		assertTrue(((InquiryRespondNotification)bm).getToken().equals(new StringToken("a1")));
		assertTrue(((InquiryRespondNotification)bm).getResult() == InquiryRespondNotification.INQUIRY_RESPOND_NOTIFICAITON_RET_OK);
		assertTrue(((InquiryRespondNotification)bm).getTransactionId() == 002);
		
		
		 json = transformer.transform(inquiryResponse);
		strJson = json.toJSONString();
		assertTrue( strJson.indexOf("header") != -1);
		assertTrue( strJson.indexOf("body") != -1);
		
		bm = transformer.transform(json.toJSONString());
		assertTrue(((InquiryResponse)bm).getToken().equals(new StringToken("a2")));
		assertTrue(((InquiryResponse)bm).getResult() == InquiryResponse.INQUIRY_RESPOND_CLIENT_RESPONSE_FAILED);
		assertTrue(((InquiryResponse)bm).getTransactionId() == 003);
		
		
		//order notification
		 json = transformer.transform(orderNotification);
		strJson = json.toJSONString();
		assertTrue( strJson.indexOf("header") != -1);
		assertTrue( strJson.indexOf("body") != -1);
		assertTrue( strJson.indexOf("bbb") != -1);
		assertTrue( strJson.indexOf("sss") != -1);
		
		bm = transformer.transform(json.toJSONString());
		assertTrue(bm != null);
		assertTrue(((OrderNotificaiton)bm).getToken().equals(new StringToken("OrderNotificaiton")));
		assertTrue(((OrderNotificaiton)bm).getTransactionId() == 001);
		assertTrue(((OrderNotificaiton)bm).getOrderId() == 032);
		
		
		
		
		//Order respond notification
		 json = transformer.transform(orderRespondNotification);
		strJson = json.toJSONString();
		assertTrue( strJson.indexOf("header") != -1);
		assertTrue( strJson.indexOf("body") != -1);
		assertTrue( strJson.indexOf("ret") != -1);
		
		bm = transformer.transform(json.toJSONString());
		assertTrue(((OrderRespondNotification)bm).getToken().equals(new StringToken("OrderRespondNotification")));
		assertTrue(((OrderRespondNotification)bm).getRet() == OrderRespondNotification.RESTAURANT_RESPONSE_RET_BUSY);
		assertTrue(((OrderRespondNotification)bm).getTransactionId() == 001);
		assertTrue(((OrderRespondNotification)bm).getOrderId() == 102L);
		
		
		//Order response
		 json = transformer.transform(orderResponse);
		strJson = json.toJSONString();
		assertTrue( strJson.indexOf("header") != -1);
		assertTrue( strJson.indexOf("body") != -1);
		assertTrue( strJson.indexOf("ret") != -1);
		
		bm = transformer.transform(json.toJSONString());
		assertTrue(((OrderResponse)bm).getToken().equals(new StringToken("OrderResponse")));
		assertTrue(((OrderResponse)bm).getRet() == OrderResponse.RESPONSE_RET_OK);
		assertTrue(((OrderResponse)bm).getTransactionId() == 032);
		assertTrue(((OrderResponse)bm).getOrderId() == 502L);
		
	}

}

