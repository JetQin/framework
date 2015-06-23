/** 
 * Project Name:framework 
 * File Name:WebSecurityConfig.java 
 * Package Name:com.usee.configuration
 * Date:Jun 22, 20151:48:26 PM 
 * Copyright (c) 2015, jianlei.qin@sktlab.com All Rights Reserved. 
 * 
 */
package com.usee.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

/**
 * ClassName: WebSecurityConfig
 * 
 * @author jet
 * @version Configuration Framework 1.0
 * @since JDK 1.7
 */
@Configuration
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{

	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http.csrf().disable();
		
		http.authorizeRequests()
				.anyRequest().permitAll();
//				.antMatchers("/*", "/home").permitAll();
//				.antMatchers("/api","/api").permitAll()
//				.anyRequest().authenticated()
//				.and().formLogin()
//				.loginPage("/login").permitAll()
//				.and().logout().permitAll();
				
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
	}
}
