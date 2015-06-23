/** 
 * Project Name:framework 
 * File Name:GreetingController.java 
 * Package Name:com.usee.controller
 * Date:Jun 22, 201510:46:26 AM 
 * Copyright (c) 2015, jianlei.qin@sktlab.com All Rights Reserved. 
 * 
 */
package com.usee.controller;

import java.util.Collection;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.usee.model.Greeting;
import com.usee.service.EmailService;
import com.usee.service.GreetingService;

/**
 * ClassName: GreetingController
 * 
 * @author jet
 * @version Configuration Framework 1.0
 * @since JDK 1.7
 */
@RestController
@RequestMapping("/api")
public class GreetingController
{
	private Logger logger = LoggerFactory.getLogger(GreetingController.class);

	@Autowired
	GreetingService greetingService;
	
	@Autowired
	EmailService emailService;

	@RequestMapping(value = "/greetings", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Greeting>> getGreetings()
	{
		logger.info(" >  getGreetings");

		Collection<Greeting> greetings = greetingService.findAll();
		logger.info(" <  getGreetings");
		return new ResponseEntity<>(greetings, HttpStatus.OK);
	}

	@RequestMapping(value = "/greetings/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Greeting> getGreeting(@PathVariable("id") Long id)
	{
		logger.info("> getGreeting");
		Greeting greeting = greetingService.findOne(id);
		if (null == greeting)
		{
			return new ResponseEntity<Greeting>(HttpStatus.NOT_FOUND);
		}
		logger.info("< getGreeting");
		return new ResponseEntity<Greeting>(greeting, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/greetings", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Greeting> createGreeting(@RequestBody Greeting greeting)
	{
		logger.info(" >  createGreeting");
		
		Greeting savedGreeting = greetingService.create(greeting);
		
		logger.info(" <  createGreeting");
		return new ResponseEntity<Greeting>(savedGreeting,HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/greetings/{id}", method = RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Greeting> updateGreeting(@RequestBody Greeting greeting)
	{
		logger.info(" >  updateGreeting");
		
		Greeting updatedGreeting = greetingService.update(greeting);
		if(null == updatedGreeting)
		{
			logger.info(" < updateGreeting");
			return new ResponseEntity<Greeting>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.info(" <  updatedGreeting");
		return new ResponseEntity<Greeting>(updatedGreeting,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/greetings/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Greeting> deleteGreeting(@PathVariable("id") Long id)
	{
		logger.info(" >  deleteGreeting");
		
	    greetingService.delete(id);
	    
		logger.info(" <  deleteGreeting");
		return new ResponseEntity<Greeting>(HttpStatus.NO_CONTENT);
	}
	
	
	@RequestMapping(value = "/greetings/{id}/send", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity<Greeting>> sendGreeting(@PathVariable("id") Long id,
			@RequestParam(value = "wait", defaultValue = "false") boolean waitForAsyncResult)
	{
		logger.info(" > sendGreeting");
		
		DeferredResult<ResponseEntity<Greeting>> result = new DeferredResult<ResponseEntity<Greeting>>();
		ResponseEntity<Greeting> responseEntity = null;
		
		Greeting greeting = null;
		try
		{
			logger.info(" get Greetings ");
			greeting = greetingService.findOne(id);
			if (null == greeting)
			{
				logger.info(" not found greetings ");
				responseEntity = new ResponseEntity<Greeting>(HttpStatus.NOT_FOUND);
				result.setResult(responseEntity);
			}

			if (waitForAsyncResult)
			{
				logger.info(" wait for result ");
				Future<Boolean> asyncResponse = emailService.sendAsyncWithResult(greeting);
				boolean emailSend = asyncResponse.get();
				logger.info("- greeting email send ? {}", emailSend);
			}
			else
			{
				logger.info(" wait for no response ");
				emailService.sendAsync(greeting);
			}
		}
		catch (Exception e)
		{
			logger.error(" A problem occured sending the greeting.", e);
			responseEntity = new ResponseEntity<Greeting>(HttpStatus.INTERNAL_SERVER_ERROR);
			result.setResult(responseEntity);
		}
		
		logger.info("< sendGreeting");
		responseEntity = new ResponseEntity<Greeting>(greeting, HttpStatus.OK);
		result.setResult(responseEntity);
		return result;

}
//	@RequestMapping(value = "/greetings/{id}/send", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<Greeting> sendGreeting(
//			@PathVariable("id")Long id,
//			@RequestParam(value="wait",defaultValue= "false") boolean waitForAsyncResult
//			)
//	{
//		logger.info(" > sendGreeting");
//		Greeting greeting = null;
//		try
//		{
//			greeting = greetingService.findOne(id);
//			if(null == greeting)
//			{
//				logger.info("< sendGreeting");
//				return new ResponseEntity<Greeting>(HttpStatus.NOT_FOUND);
//			}
//			
//			if(waitForAsyncResult)
//			{
//				Future<Boolean> asyncResponse = emailService.sendAsyncWithResult(greeting);
//				boolean emailSend = asyncResponse.get();
//				logger.info("- greeting email send ? {}",emailSend);
//			}
//			else
//			{
//				emailService.sendAsync(greeting);
//			}
//		}
//		catch (Exception e)
//		{
//			logger.error(" A problem occured sending the greeting.",e);
//			return new ResponseEntity<Greeting>(HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//		
//		return new ResponseEntity<Greeting>(greeting,HttpStatus.OK);
//				
//	}
}
