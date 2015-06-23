/** 
 * Project Name:framework 
 * File Name:GreetingRepository.java 
 * Package Name:com.usee.repository
 * Date:Jun 22, 201512:00:19 PM 
 * Copyright (c) 2015, jianlei.qin@sktlab.com All Rights Reserved. 
 * 
 */
package com.usee.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.usee.model.Greeting;



/** 
 * ClassName: GreetingRepository  
 * 
 * @author jet 
 * @version Configuration Framework 1.0
 * @since JDK 1.7 
 */
@Repository
public interface GreetingRepository extends JpaRepository<Greeting,Long>
{
	
}
