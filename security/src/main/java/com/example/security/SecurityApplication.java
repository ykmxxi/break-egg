package com.example.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

// localhost:8080 접속시 바로 기본 시큐리티가 제공하는 로그인 페이지로 이동하기 때문에 제외
//@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@SpringBootApplication
public class SecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }

}
