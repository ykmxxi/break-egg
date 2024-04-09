package com.example.training.domain;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Coffee {

    @Id
    private String id; // 특정 커피 종류의 고유 식별값
    private String name; // 커피(종류)명

    protected Coffee() {
    }

    public Coffee(final String id, final String name) {
        this.id = id;
        this.name = name;
    }

    public Coffee(final String name) {
        this(UUID.randomUUID().toString(), name);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }

}
