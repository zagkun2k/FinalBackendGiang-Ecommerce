package com.truongiang.ecommerceweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EcommercewebApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommercewebApplication.class, args);
	}

}
