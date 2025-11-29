package com.flightreservation.model;

public class Flight {
    private int id;
    private String airline;
    private String origin;
    private String destination;
    private String departure;
    private String arrival;
    private float price;
    private int availableSeats;

    public Flight(int id, String airline, String origin, String destination, String departure, String arrival, float price, int availableSeats) {
        this.id = id;
        this.airline = airline;
        this.origin = origin;
        this.destination = destination;
        this.departure = departure;
        this.arrival = arrival;
        this.price = price;
        this.availableSeats = availableSeats;
    }

    public int getId() {
        return id;
    }

    public String getAirline() {
        return airline;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getDeparture() {
        return departure;
    }

    public String getArrival() {
        return arrival;
    }

    public float getPrice() {
        return price;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }
}
