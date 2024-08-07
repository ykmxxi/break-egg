package com.example.helloboot.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class HelloDecorator implements HelloService {

    private final HelloService target;

    public HelloDecorator(final HelloService target) {
        this.target = target;
    }

    @Override
    public String sayHello(final String name) {
        return "*" + target.sayHello(name) + "*";
    }

}
