package com.example.PitchSide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PitchSideApplication {

	public static void main(String[] args) {
		SpringApplication.run(PitchSideApplication.class, args);
	}

}
