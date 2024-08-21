package com.example.helloboot.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.example.helloboot.repository.HelloRepository;

// 스프링 빈들을 컨테이너로 로딩하지만 테스트 동안 웹 환경 설정이 필요없음
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
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
