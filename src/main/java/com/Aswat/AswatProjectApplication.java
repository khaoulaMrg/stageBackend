package com.Aswat;

import com.Aswat.services.jwt.FileStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
public class AswatProjectApplication implements CommandLineRunner {

	@Autowired
	private FileStorageService fileStorageService;

	public static void main(String[] args) {
		SpringApplication.run(AswatProjectApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		fileStorageService.init(); // Initialise le répertoire de destination lors du démarrage de l'application
	}
}
