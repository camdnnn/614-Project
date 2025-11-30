package com.ucalgary.booking.business.service;

import java.util.List;
import java.util.Objects;

import com.ucalgary.booking.Flight;
import com.ucalgary.booking.FlightRepository;

public class FlightService {
    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = Objects.requireNonNull(flightRepository);
    }

    public List<Flight> getAllFlights() {
        return flightRepository.readAll();
    }

    public void addFlight(Flight flight) {
        flightRepository.create(flight);
    }

    public void updateFlight(long id, Flight flight) {
        flightRepository.update(id, flight);
    }

    public void removeFlight(long id) {
        flightRepository.delete(id);
    }
}
