package com.example.training.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.training.domain.Coffee;

@RestController
@RequestMapping("/coffees")
public class CoffeeController {

    private static final Logger logger = LoggerFactory.getLogger(CoffeeController.class);
    private List<Coffee> coffees = new ArrayList<>();

    public CoffeeController() {
        coffees.addAll(List.of(
                new Coffee("Cafe Americano"),
                new Coffee("Cafe Latte"),
                new Coffee("Espresso"),
                new Coffee("Ice Americano")
        ));
    }

    //  @RequestMapping(value = "/coffees", method = RequestMethod.GET)
    @GetMapping
    Iterable<Coffee> getCoffees() {
        return coffees;
    }

    @GetMapping("/{id}")
    Optional<Coffee> getCoffeeById(@PathVariable String id) {
        for (Coffee coffee : coffees) {
            if (coffee.getId().equals(id)) {
                return Optional.of(coffee);
            }
        }
        return Optional.empty();
    }

    @PostMapping
    Coffee createCoffee(@RequestBody Coffee coffee) {
        coffees.add(coffee);
        logger.info("coffee id={}, name={}", coffee.getId(), coffee.getName());
        return coffee;
    }

    @PutMapping("/{id}")
    ResponseEntity<Coffee> putCoffee(@PathVariable String id, @RequestBody Coffee coffee) {
        int coffeeIdx = -1;

        for (Coffee c : coffees) {
            if (c.getId() == null) {
                continue;
            }
            if (c.getId().equals(id)) {
                coffeeIdx = coffees.indexOf(c);
                coffees.set(coffeeIdx, coffee);
            }
        }

        return coffeeIdx == -1 ?
                new ResponseEntity<>(createCoffee(coffee), HttpStatus.CREATED) :
                new ResponseEntity<>(coffee, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    void deleteCoffee(@PathVariable String id) {
        coffees.removeIf(c -> c.getId().equals(id));
    }

}
