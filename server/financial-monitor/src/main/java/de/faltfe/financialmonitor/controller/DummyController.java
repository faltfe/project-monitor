package de.faltfe.financialmonitor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/financial")
public class DummyController {

    @GetMapping
    public Mono<String> test() {
        return Mono.just("Hello World from Financial Monitor");
    }
}
