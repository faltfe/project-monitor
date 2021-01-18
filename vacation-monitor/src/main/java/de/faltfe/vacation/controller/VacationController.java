package de.faltfe.vacation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VacationController {

    @GetMapping("/hello-world")
    public String helloWorld() {
        return "Hello World";
    }
}
