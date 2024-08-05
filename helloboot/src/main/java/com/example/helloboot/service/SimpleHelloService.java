package com.example.helloboot.service;

import org.springframework.stereotype.Service;

@Service
public class SimpleHelloService implements HelloService {

    @Override
    public String sayHello(final String name) {
        return "Hello " + name;
    }

}
