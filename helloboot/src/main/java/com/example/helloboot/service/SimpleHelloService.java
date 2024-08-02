package com.example.helloboot.service;

public class SimpleHelloService implements HelloService {

    @Override
    public String sayHello(final String name) {
        return "Hello " + name;
    }

}
