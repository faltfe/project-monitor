package de.faltfe.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "de.faltfe.vacation.entities")
public class ProjectMonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectMonitorApplication.class, args);
	}

}
