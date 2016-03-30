package com.epages;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DataSynchronizationSpringAmqpServerApplication.class)
public class DataSynchronizationSpringAmqpServerApplicationTests {


	@Autowired
	ObjectMapper objectMapper;

	@Test
	public void contextLoads() throws IOException {
		objectMapper.readValue("{\"tenantId\":1}", BusinessUnitRefRequest.class);
	}

}
