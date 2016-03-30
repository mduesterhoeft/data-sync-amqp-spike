package com.epages;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@SpringBootApplication
@EnableCircuitBreaker
public class DataSynchronizationSpringAmqpClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataSynchronizationSpringAmqpClientApplication.class, args);
	}
}
