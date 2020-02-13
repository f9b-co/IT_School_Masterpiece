package fr.formation.masterpieceApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class MasterpieceApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MasterpieceApiApplication.class, args);
	}

}
