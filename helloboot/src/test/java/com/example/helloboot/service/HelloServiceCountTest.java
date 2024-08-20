package com.example.helloboot.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.helloboot.HellobootTest;
import com.example.helloboot.repository.HelloRepository;

@HellobootTest
class HelloServiceCountTest {

    @Autowired HelloService helloService;
    @Autowired HelloRepository helloRepository;

    @Test
    void sayHelloIncreaseCount() {
        String name = "Spring";

        IntStream.rangeClosed(1, 10)
                .forEach(count -> {
                    helloService.sayHello(name);
                    assertThat(helloRepository.countOf(name)).isEqualTo(count);
                });
    }

}
