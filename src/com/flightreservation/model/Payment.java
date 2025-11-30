package com.flightreservation.model;

import java.util.Date;

public class Payment {
    private int id;
    private int customerId;
    private int bookingId;
    private float amount;
    private Date paymentDate;
    private String method;

    public Payment(int id, int customerId, int bookingId, float amount, Date paymentDate, String method) {
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

    public Date getPaymentDate() {
        return paymentDate;
    }

    public String getMethod() {
        return method;
    }
}
