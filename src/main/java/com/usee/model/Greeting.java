/** 
 * Project Name:framework 
 * File Name:Greeting.java 
 * Package Name:com.usee.model
 * Date:Jun 22, 201510:04:31 AM 
 * Copyright (c) 2015, jianlei.qin@sktlab.com All Rights Reserved. 
 * 
 */
package com.usee.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;



/** 
 * ClassName: Greeting  
 * 
 * @author jet 
 * @version Configuration Framework 1.0
 * @since JDK 1.7 
 */
@Entity
public @Data class Greeting
{

	@Id
	@GeneratedValue
	private Long id;
	
	private String text;
	
}
