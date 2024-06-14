package com.example.redis_template;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.redis_template.domain.Aircraft;

public class PlaneFinderPoller {

    private WebClient client = WebClient.create("http://localhost:7634/aircraft");

    private final RedisConnectionFactory connectionFactory;
    private final AircraftRepository aircraftRepository;

    public PlaneFinderPoller(
            final RedisConnectionFactory connectionFactory,
            final AircraftRepository aircraftRepository
    ) {
        this.connectionFactory = connectionFactory;
        this.aircraftRepository = aircraftRepository;
    }

    @Scheduled(fixedRate = 1000)
    private void pollPlanes() {
        connectionFactory.getConnection()
                .serverCommands()
                .flushDb();

        client.get()
                .retrieve()
                .bodyToFlux(Aircraft.class)
                .filter(plane -> !plane.getReg().isEmpty())
                .toStream()
                .forEach(aircraftRepository::save);

        aircraftRepository.findAll()
                .forEach(System.out::println);
    }

}
