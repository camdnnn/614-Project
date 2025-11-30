package com.flightreservation.business.service;

import java.util.List;
import java.util.Objects;

import com.flightreservation.data.repository.Repository;
import com.flightreservation.model.Flight;

public class FlightService {
    private final Repository<Flight> flightRepository;

    public FlightService(Repository<Flight> flightRepository) {
        this.flightRepository = Objects.requireNonNull(flightRepository);
    }

    public List<Flight> getAllFlights() {
        return flightRepository.getAll();
    }

    public Flight getFlight(int id) {
        return flightRepository.read(id);
    }

    public void addFlight(Flight flight) {
        flightRepository.create(flight);
    }

    public void updateFlight(int id, Flight flight) {
        flightRepository.update(id, flight);
    }

    public void removeFlight(int id) {
        flightRepository.delete(id);
    }
}
