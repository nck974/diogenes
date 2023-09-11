package dev.nichoko.diogenes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = { "dev.nichoko.diogenes" })
public class DiogenesApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiogenesApplication.class, args);
	}

}
