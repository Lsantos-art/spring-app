package com.springapp.springapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@RestController
public class SpringappApplication {

	@GetMapping("/")
	public String index() {
		return "Aplication is running in http://localhost:8080/";
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringappApplication.class, args);
	}

}
