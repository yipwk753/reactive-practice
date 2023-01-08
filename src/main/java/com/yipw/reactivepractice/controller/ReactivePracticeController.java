package com.yipw.reactivepractice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/practice")
public class ReactivePracticeController {
    
    @GetMapping("/mono")
    public Mono<String> getMono() {
        return Mono.just("Hello world!");
    }
}
