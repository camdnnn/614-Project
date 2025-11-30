package com.flightreservation.model;

import java.util.Date;

public class Booking {
    private int id;
    private int customerId;
    private int flightId;
    private String seatNumber;
    private Date bookingDate;
    private String status;

    public Booking(int id, int customerId, int flightId, String seatNumber, Date bookingDate, String status) {
        this.id = id;
        this.customerId = customerId;
        this.flightId = flightId;
        this.seatNumber = seatNumber;
        this.bookingDate = bookingDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getFlightId() {
        return flightId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public String getStatus() {
        return status;
    }
}
