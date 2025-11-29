package com.flightreservation.model;

import java.util.Date;

public class Flight {
    private int id;
    private String airline;
    private String origin;
    private String destination;
    private Date departure;
    private Date arrival;
    private double price;
    private int availableSeats;

    public Flight() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getAirline() { return airline; }
    public void setAirline(String airline) { this.airline = airline; }

    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public Date getDeparture() { return departure; }
    public void setDeparture(Date departure) { this.departure = departure; }

    public Date getArrival() { return arrival; }
    public void setArrival(Date arrival) { this.arrival = arrival; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }
}

