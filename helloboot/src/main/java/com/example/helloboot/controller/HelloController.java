package com.example.helloboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.helloboot.service.HelloService;

@RestController
public class HelloController {

    private final HelloService helloService;

    // 스프링 빈이 단일 생성자인 경우 필요한 의존 오브젝트를 스프링 컨테이너가 자동으로 주입
    // 원래는 @Autowired 필요: @Autowired public HelloController(...) {...}
    public HelloController(final HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping("/hello")
    public String hello(final String name) {
        // 사용자 요청 검증은 컨트롤러의 중요한 역할 중 하나
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException();
        }
        return helloService.sayHello(name);
    }

    @GetMapping("/count")
    public String count(final String name) {
        return name + ": " + helloService.countOf(name);
    }

}
