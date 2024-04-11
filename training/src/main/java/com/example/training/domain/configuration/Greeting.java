package com.example.training.domain.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 관련 속성을 캡슐화
 */
@ConfigurationProperties(prefix = "greeting")
public class Greeting {

    private String name;
    private String coffee;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getCoffee() {
        return coffee;
    }

    public void setCoffee(final String coffee) {
        this.coffee = coffee;
    }

}
