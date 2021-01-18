package de.faltfe.vacation.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootConfiguration
@EnableAutoConfiguration
@EnableJpaRepositories("de.faltfe.vacation.repositories")
@ComponentScan("de.faltfe.vacation")
public class VacationTestConfiguration extends VacationConfiguration {}
