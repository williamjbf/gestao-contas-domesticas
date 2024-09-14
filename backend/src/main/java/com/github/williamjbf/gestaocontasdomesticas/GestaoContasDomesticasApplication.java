package com.github.williamjbf.gestaocontasdomesticas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class GestaoContasDomesticasApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestaoContasDomesticasApplication.class, args);
	}

}
