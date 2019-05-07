package com.court.evidence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.court.evidence"})
public class EvidenceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EvidenceApplication.class, args);
	}

}
