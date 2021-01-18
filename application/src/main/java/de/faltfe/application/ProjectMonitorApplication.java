package de.faltfe.application;

import de.faltfe.vacation.config.VacationConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({VacationConfiguration.class})
public class ProjectMonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectMonitorApplication.class, args);
	}

}
