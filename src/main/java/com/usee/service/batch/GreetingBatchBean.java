/** 
 * Project Name:framework 
 * File Name:GreetingBatchBean.java 
 * Package Name:com.usee.service.batch
 * Date:Jun 22, 201511:31:37 PM 
 * Copyright (c) 2015, jianlei.qin@sktlab.com All Rights Reserved. 
 * 
 */
package com.usee.service.batch;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.usee.model.Greeting;
import com.usee.service.GreetingService;

/**
 * ClassName: GreetingBatchBean
 * 
 * @author jet
 * @version Configuration Framework 1.0
 * @since JDK 1.7
 */
@Profile("batch")
@Component
public class GreetingBatchBean
{
	private Logger logger = LoggerFactory.getLogger(GreetingBatchBean.class);

	@Autowired
	private GreetingService greetingService;

	@Scheduled(cron = "${batch.greeting.cron}")
	public void cronJob()
	{
		logger.info(" > cronJob ");

		Collection<Greeting> greetings = greetingService.findAll();

		logger.info("There are {} greetings in the data store.", greetings.size());
		logger.info(" < cronJob ");
	}

	@Scheduled(initialDelayString = "${batch.greeting.initialDelay}", fixedRateString = "${batch.greeting.fixrate}")
	public void fixedRateJobWithInitialDelay()
	{

		logger.info(" > fixedRateJobWithInitialDelay ");
		logger.info("Process time was {} seconds.", 5);
		logger.info(" < fixedRateJobWithInitialDelay ");
	}
}
