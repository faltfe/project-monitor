package de.faltfe.vacation.controller;

import de.faltfe.vacation.clients.FinancialClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/vacation")
@Slf4j
public class VacationController {

    @Autowired
    private FinancialClient financialClient;

    @GetMapping("/hello-world")
    public String helloWorld() {
        return "Hello World";
    }

    @GetMapping("/financials")
    public String getFinancials() {
        return financialClient.getFinancial();
    }

}
