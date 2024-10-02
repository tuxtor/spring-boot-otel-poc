package com.vorozco.springboot_client_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SpringbootClientDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootClientDemoApplication.class, args);
	}

}
