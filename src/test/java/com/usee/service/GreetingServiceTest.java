/** 
 * Project Name:framework 
 * File Name:GreetingServiceTest.java 
 * Package Name:com.usee.service
 * Date:Jun 22, 201511:49:35 PM 
 * Copyright (c) 2015, jianlei.qin@sktlab.com All Rights Reserved. 
 * 
 */
package com.usee.service;

import java.util.Collection;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.usee.UseeApplicationTests;
import com.usee.model.Greeting;



/** 
 * ClassName: GreetingServiceTest  
 * 
 * @author jet 
 * @version Configuration Framework 1.0
 * @since JDK 1.7 
 */
@Transactional
public class GreetingServiceTest extends UseeApplicationTests
{
	
	@Autowired
	private GreetingService greetingService;
	
	@Before
	public void setup()
	{
		greetingService.evictCache();
	}
	
	@After
	public void tearDown()
	{
		
	}
	
	@Test
	public void testFindAll()
	{
		Collection<Greeting> list = greetingService.findAll();
		Assert.assertNotNull("failure - expected not null",list);
		Assert.assertEquals("failure- expected size", 2, list.size());
	}
	
	@Test
	public void testFindOne()
	{
		Long id = new Long(1);
		
		Greeting entity = greetingService.findOne(id);
		Assert.assertNotNull("failure - expected Not null",entity);
		Assert.assertEquals("failure - expected attribute match",id,entity.getId());
	}

	@Test
	public void testFindOneNotFound()
	{
		Long id = Long.MAX_VALUE;
		
		Greeting entity = greetingService.findOne(id);
		Assert.assertNull("failure - expected  null",entity);
	}
}
