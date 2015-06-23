/** 
 * Project Name:framework 
 * File Name:GreetingService.java 
 * Package Name:com.usee.service
 * Date:Jun 22, 201510:48:46 AM 
 * Copyright (c) 2015, jianlei.qin@sktlab.com All Rights Reserved. 
 * 
 */
package com.usee.service;

import java.util.Collection;

import com.usee.model.Greeting;



/** 
 * ClassName: GreetingService  
 * 
 * @author jet 
 * @version Configuration Framework 1.0
 * @since JDK 1.7 
 */
public interface GreetingService
{
	Collection<Greeting> findAll();
	
	Greeting findOne(Long id);
	
	Greeting create(Greeting greeting);
	
	Greeting update(Greeting greeting);
	
	void delete(Long id);
	
	void evictCache();
}
