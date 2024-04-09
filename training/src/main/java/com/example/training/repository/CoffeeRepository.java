package com.example.training.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.training.domain.Coffee;

public interface CoffeeRepository extends CrudRepository<Coffee, String> {

}
