package com.example.helloboot.service;

public interface HelloService {

    String sayHello(String name);

    default int countOf(final String name) {
        return 0;
    }

}
