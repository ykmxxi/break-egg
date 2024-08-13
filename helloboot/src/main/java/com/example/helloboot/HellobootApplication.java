package com.example.helloboot;

import org.springframework.boot.SpringApplication;

import com.example.config.MySpringBootApplication;

@MySpringBootApplication
public class HellobootApplication {

//    @Bean
//    ApplicationRunner applicationRunner(final Environment environment) {
//        return args -> {
//            String name = environment.getProperty("my.name");
//            System.out.println("name = " + name);
//        };
//    }

    public static void main(String[] args) {
        SpringApplication.run(HellobootApplication.class, args);
    }

}
