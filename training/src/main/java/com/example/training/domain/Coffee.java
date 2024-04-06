package com.example.training.domain;

import java.util.UUID;

public class Coffee {

    private final String id; // 특정 커피 종류의 고유 식별값
    private String name; // 커피(종류)명

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

    public void setName(final String name) {
        this.name = name;
    }

}
