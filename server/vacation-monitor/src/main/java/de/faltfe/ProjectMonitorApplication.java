package de.faltfe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ProjectMonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectMonitorApplication.class, args);
	}

}
