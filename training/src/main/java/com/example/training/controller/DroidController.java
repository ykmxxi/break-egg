package com.example.training.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.training.domain.configuration.Droid;

@RestController
@RequestMapping("/droid")
public class DroidController {

    private final Droid droid;

    public DroidController(final Droid droid) {
        this.droid = droid;
    }

    @GetMapping
    String getDroid() {
        return droid.getId() + "의 description은 " + droid.getDescription();
    }

}
