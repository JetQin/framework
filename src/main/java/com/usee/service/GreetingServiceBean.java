/** 
 * Project Name:framework 
 * File Name:GreetingServiceBean.java 
 * Package Name:com.usee.service
 * Date:Jun 22, 201510:48:55 AM 
 * Copyright (c) 2015, jianlei.qin@sktlab.com All Rights Reserved. 
 * 
 */
package com.usee.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.usee.model.Greeting;
import com.usee.repository.GreetingRepository;

/**
 * ClassName: GreetingServiceBean
 * 
 * @author jet
 * @version Configuration Framework 1.0
 * @since JDK 1.7
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class GreetingServiceBean implements GreetingService
{

	@Autowired
	GreetingRepository greetingRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.usee.service.GreetingService#findAll()
	 */
	@Override
	public Collection<Greeting> findAll()
	{
		Collection<Greeting> greetings = greetingRepository.findAll();
		return greetings;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.usee.service.GreetingService#findOne(java.lang.Long)
	 */
	@Override
	@Cacheable(value = "greetings", key = "#id")
	public Greeting findOne(Long id)
	{
		Greeting greeting = greetingRepository.findOne(id);
		return greeting;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.usee.service.GreetingService#create(com.usee.model.Greeting)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@CachePut(value = "greetings", key = "#result.id")
	public Greeting create(Greeting greeting)
	{
		if (greeting.getId() != null)
		{
			return null;
		}
		Greeting savedGreeting = greetingRepository.save(greeting);
		return savedGreeting;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.usee.service.GreetingService#update(com.usee.model.Greeting)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@CachePut(value = "greetings", key = "#greeting.id")
	public Greeting update(Greeting greeting)
	{
		Greeting greetingPersisted = findOne(greeting.getId());
		if (null == greetingPersisted)
		{
			return null;
		}
		Greeting updatedGreeting = greetingRepository.save(greeting);
		return updatedGreeting;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.usee.service.GreetingService#delete(java.lang.Long)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@CacheEvict(value = "greetings", key = "#id")
	public void delete(Long id)
	{
		greetingRepository.delete(id);
	}

	@Override
	@CacheEvict(value = "greetings", allEntries = true)
	public void evictCache()
	{

	}

}
