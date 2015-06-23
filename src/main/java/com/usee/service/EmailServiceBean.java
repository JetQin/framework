/** 
 * Project Name:framework 
 * File Name:EmailServiceBean.java 
 * Package Name:com.usee.service
 * Date:Jun 22, 201510:38:06 AM 
 * Copyright (c) 2015, jianlei.qin@sktlab.com All Rights Reserved. 
 * 
 */
package com.usee.service;

import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.usee.model.Greeting;
import com.usee.model.common.AsyncResponse;

/**
 * ClassName: EmailServiceBean
 * 
 * @author jet
 * @version Configuration Framework 1.0
 * @since JDK 1.7
 */
@Service
public class EmailServiceBean implements EmailService
{

	private Logger logger = LoggerFactory.getLogger(EmailServiceBean.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.usee.service.EmailService#send(com.usee.model.Greeting)
	 */
	@Override
	public Boolean send(Greeting greeting)
	{
		logger.info(" > send");

		Boolean success = Boolean.FALSE;

		long pause = 5000;

		try
		{
			Thread.sleep(pause);
		}
		catch (Exception e)
		{
			logger.error("Send mail with exeception {}.",e.getMessage());
		}

		logger.info("Processing time was {} seconds.", pause / 1000);

		success = Boolean.TRUE;

		logger.info(" < send");

		return success;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.usee.service.EmailService#sendAsync(com.usee.model.Greeting)
	 */
	@Async
	@Override
	public void sendAsync(Greeting greeting)
	{

		logger.info(" > sendAsync");

		try
		{
			send(greeting);
		}
		catch (Exception e)
		{
			logger.error("Send mail with exeception {}.",e.getMessage());
		}

		logger.info(" < sendAsync");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.usee.service.EmailService#sendAsyncWithResult(com.usee.model.Greeting
	 * )
	 */
	@Async
	@Override
	public Future<Boolean> sendAsyncWithResult(Greeting greeting)
	{
		logger.info(" > sendAsyncWithResult");
		
		AsyncResponse<Boolean> response = new AsyncResponse<Boolean>();
		
		try
		{
			Boolean success = send(greeting);
			response.complete(success);
		}
		catch (Exception e)
		{
			logger.error("Send mail with exeception {}.",e.getMessage());
			response.completedExceptional(e);
		}

		logger.info("< sendAsyncWithResult");
		return response;
	}

}
