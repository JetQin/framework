package com.usee;

import java.io.IOException;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebAppConfiguration
public class UseeApplicationControllerTests extends UseeApplicationTests{

	
	protected MockMvc mock;
	
	@Autowired
	protected WebApplicationContext webApplivationContext;


	protected void setup()
	{
		mock = MockMvcBuilders.webAppContextSetup(webApplivationContext).build();
	}
	
	protected String mapToJson(Object obj) throws JsonProcessingException
	{
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(obj);
	}
	
	protected <T> T mapFromJson(String json,Class<T> clazz)
			throws JsonProcessingException,JsonMappingException,IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(json, clazz);
	}
}
