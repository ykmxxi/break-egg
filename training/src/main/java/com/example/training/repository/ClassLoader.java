package com.example.training.repository;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.training.domain.Coffee;

import jakarta.annotation.PostConstruct;

@Component
public class ClassLoader {

    private final CoffeeRepository coffeeRepository;

    public ClassLoader(final CoffeeRepository coffeeRepository) {
        this.coffeeRepository = coffeeRepository;
    }

    @PostConstruct
    private void loadData() {
        coffeeRepository.saveAll(List.of(
                new Coffee("Cafe Americano"),
                new Coffee("Cafe Latte"),
                new Coffee("Espresso"),
                new Coffee("Ice Americano")
        ));
    }

}
