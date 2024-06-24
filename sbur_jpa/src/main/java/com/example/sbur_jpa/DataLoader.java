package com.example.sbur_jpa;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataLoader {

    private final AircraftRepository repository;

    @PostConstruct
    private void loadData() {
        repository.deleteAll();

        repository.save(new Aircraft());
    }

}
