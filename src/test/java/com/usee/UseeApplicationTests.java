package com.usee;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = UseeApplication.class)
@WebAppConfiguration
public class UseeApplicationTests {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Test
	public void contextLoads() {
	}

}
