package de.faltfe.vacation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vacation")
public class VacationController {

    @GetMapping("/hello-world")
    public String helloWorld() {
        return "Hello World";
    }
}
