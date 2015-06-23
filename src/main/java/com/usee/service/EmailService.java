/** 
 * Project Name:framework 
 * File Name:EmailService.java 
 * Package Name:com.usee.service
 * Date:Jun 22, 201510:01:40 AM 
 * Copyright (c) 2015, jianlei.qin@sktlab.com All Rights Reserved. 
 * 
 */
package com.usee.service;

import java.util.concurrent.Future;

import com.usee.model.Greeting;

/**
 * ClassName: EmailService
 * 
 * @author jet
 * @version Configuration Framework 1.0
 * @since JDK 1.7
 */
public interface EmailService
{
	Boolean send(Greeting greeting);

	void sendAsync(Greeting greeting);

	Future<Boolean> sendAsyncWithResult(Greeting greeting);
}
