package com.chiliclub.chilichat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ChilichatApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChilichatApplication.class, args);
	}

}
