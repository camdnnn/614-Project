package com.flightreservation.model;

<<<<<<< Updated upstream
import java.util.Date;
=======
import java.time.LocalDateTime;
>>>>>>> Stashed changes

public class Payment {
    private int id;
    private int customerId;
    private int bookingId;
<<<<<<< Updated upstream
    private float amount;
    private Date paymentDate;
    private String method;

    public Payment(int id, int customerId, int bookingId, float amount, Date paymentDate, String method) {
=======
    private double amount;
    private LocalDateTime paymentDate;
    private String method;

    public Payment(int id, int customerId, int bookingId, double amount, LocalDateTime paymentDate, String method) {
>>>>>>> Stashed changes
        this.id = id;
        this.customerId = customerId;
        this.bookingId = bookingId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.method = method;
    }

    public int getId() {
        return id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public double getAmount() {
        return amount;
    }

<<<<<<< Updated upstream
    public Date getPaymentDate() {
=======
    public LocalDateTime getPaymentDate() {
>>>>>>> Stashed changes
        return paymentDate;
    }

    public String getMethod() {
        return method;
    }
}
