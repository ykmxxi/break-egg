package com.example.helloboot.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class HelloControllerTest {

    @Test
    void helloController() {
        // 의존 오브젝트인 HelloService 인터페이스는 Functional Interface
        // 람다식으로 테스트용 의존 오브젝트를 생성해서 진행
        HelloController helloController = new HelloController(name -> name);

        String result = helloController.hello("Test");

        assertThat(result).isEqualTo("Test");
    }

    @Test
    void failsHelloController() {
        HelloController helloController = new HelloController(name -> name);

        assertThatThrownBy(() -> helloController.hello(null))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> helloController.hello("  "))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
