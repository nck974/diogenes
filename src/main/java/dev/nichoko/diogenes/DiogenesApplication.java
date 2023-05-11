package dev.nichoko.diogenes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

import dev.nichoko.diogenes.config.SecurityProperties;

@SpringBootApplication
@RestController
@ComponentScan(basePackages = { "dev.nichoko.diogenes" })
@EnableConfigurationProperties(SecurityProperties.class)
public class DiogenesApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiogenesApplication.class, args);
	}

}
