package com.flightreservation.model;

<<<<<<< Updated upstream
import java.util.Date;
=======
import java.time.LocalDateTime;
>>>>>>> Stashed changes

public class Booking {
    private int id;
    private int customerId;
    private int flightId;
    private String seatNumber;
<<<<<<< Updated upstream
    private Date bookingDate;
    private String status;

    public Booking(int id, int customerId, int flightId, String seatNumber, Date bookingDate, String status) {
=======
    private LocalDateTime bookingDate;
    private String status;

    public Booking(int id, int customerId, int flightId, String seatNumber, LocalDateTime bookingDate, String status) {
>>>>>>> Stashed changes
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

<<<<<<< Updated upstream
    public Date getBookingDate() {
=======
    public LocalDateTime getBookingDate() {
>>>>>>> Stashed changes
        return bookingDate;
    }

    public String getStatus() {
        return status;
    }
}
