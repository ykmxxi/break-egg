package com.example.redis_template;

import org.springframework.data.repository.CrudRepository;

import com.example.redis_template.domain.Aircraft;

public interface AircraftRepository extends CrudRepository<Aircraft, Long> {
}
