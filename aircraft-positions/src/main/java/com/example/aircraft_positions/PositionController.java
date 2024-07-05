package com.example.aircraft_positions;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PositionController {

    private final AircraftRepository aircraftRepository;
    private WebClient client = WebClient.create("http://localhost:7634/aircraft");

    @GetMapping("/aircraft")
    public String getCurrentAircraftPositions(final Model model) {
        aircraftRepository.deleteAll();

        client.get()
                .retrieve()
                .bodyToFlux(Aircraft.class)
                .filter(plane -> !plane.getReg().isEmpty())
                .toStream()
                .forEach(aircraftRepository::save);

        model.addAttribute("currentPositions", aircraftRepository.findAll());
        return "positions";
    }

}
