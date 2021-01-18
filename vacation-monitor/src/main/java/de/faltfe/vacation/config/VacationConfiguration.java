package de.faltfe.vacation.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan("de.faltfe.vacation.entities")
@ComponentScan("de.faltfe.vacation.controller")
@EnableAutoConfiguration
public class VacationConfiguration {}
