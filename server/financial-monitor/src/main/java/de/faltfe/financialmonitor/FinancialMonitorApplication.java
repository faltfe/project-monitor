package de.faltfe.financialmonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class FinancialMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinancialMonitorApplication.class, args);
    }

}
