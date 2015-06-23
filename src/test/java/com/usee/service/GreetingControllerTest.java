/** 
 * Project Name:framework 
 * File Name:GreetingControllerTest.java 
 * Package Name:com.usee.service
 * Date:Jun 23, 201512:25:22 AM 
 * Copyright (c) 2015, jianlei.qin@sktlab.com All Rights Reserved. 
 * 
 */
package com.usee.service;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.usee.UseeApplicationControllerTests;
import com.usee.model.Greeting;

/**
 * ClassName: GreetingControllerTest
 * 
 * @author jet
 * @version Configuration Framework 1.0
 * @since JDK 1.7
 */
@Transactional
public class GreetingControllerTest extends UseeApplicationControllerTests
{

	@Autowired
	public GreetingService greetingService;

	@Before
	public void setup()
	{
		super.setup();
		greetingService.evictCache();
	}

	@Test
	public void testGetGreetings() throws Exception
	{
		String url = "/api/greetings";
		MvcResult result = super.mock.perform(MockMvcRequestBuilders.get(url)
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		Assert.assertEquals("failure - expected HTTP status 200.", 200,status);
		Assert.assertTrue("failure - expected HTTP response body to have a value", content.trim().length() > 0);
	}

	@Test
	public void testGetGreetingsNotFound() throws Exception
	{
		String url = "/api/greetings/{id}";
		Long id = Long.MAX_VALUE;
		MvcResult result = super.mock.perform(MockMvcRequestBuilders.get(url,id)
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		Assert.assertEquals("failure - expected HTTP status 404.", 404,status);
		Assert.assertTrue("failure - expected HTTP response body to have a value", content.trim().length() == 0);
	}
	
	@Test
	public void testCreateGreetings() throws Exception
	{
		String url = "/api/greetings";
		Greeting greeting = new Greeting();
		greeting.setText("test");
		String inputJson = mapToJson(greeting);
		
		MvcResult result = super.mock.perform(MockMvcRequestBuilders.post(url)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		Assert.assertEquals("failure - expected HTTP status 201.", 201,status);
		Assert.assertTrue("failure - expected HTTP response body to have a value", content.trim().length() > 0);

		Greeting createdGreeting = mapFromJson(content, Greeting.class);
		Assert.assertNotNull("failure - expected greeting  not null", createdGreeting);
		Assert.assertNotNull("failure - expected greeting  id not null", createdGreeting.getId());
		Assert.assertEquals("failure - expected attribute match", "test",createdGreeting.getText());
		
	}
}
