package com.alura.foroHub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.alura.foroHub")
public class ForoHubApplication {
	public static void main(String[] args) {
		SpringApplication.run(ForoHubApplication.class, args);
	}
}

