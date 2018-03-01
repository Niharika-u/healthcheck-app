package com.example.healthcheckapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HealthcheckAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthcheckAppApplication.class, args);
		System.out.println("Testing the GIT push");
	}
}
