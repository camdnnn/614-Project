package com.flightreservation.model;

public class Payment {
    private int id;
    private int customerId;
    private int bookingId;
    private float amount;
    private String paymentDate;
    private String method;

    public Payment(int id, int customerId, int bookingId, float amount, String paymentDate, String method) {
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

    public float getAmount() {
        return amount;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public String getMethod() {
        return method;
    }
}
