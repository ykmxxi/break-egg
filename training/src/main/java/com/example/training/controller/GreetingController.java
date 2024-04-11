package com.example.training.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.training.domain.configuration.Greeting;

@RestController
@RequestMapping("/greeting")
public class GreetingController {

    /*
    @Value("${greeting-name: DefaultName}")
    private String name;

    @Value("${greeting-coffee: ${greeting-name} is drinking ice latte}")
    private String coffee;

    @GetMapping
    String getGreeting() {
        return name;
    }

    @GetMapping("/coffee")
    String getNameAndCoffee() {
        return coffee;
    }
    */

    private final Greeting greeting; // properties 파일에 정의된 속성을 사용해 생성자 주입을 통해 빈으로 등록

    public GreetingController(final Greeting greeting) {
        this.greeting = greeting;
    }

    @GetMapping
    String getGreeting() {
        return greeting.getName();
    }

    @GetMapping("/coffee")
    String getNameAndCoffee() {
        return greeting.getCoffee();
    }

}
