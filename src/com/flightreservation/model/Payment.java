package com.flightreservation.model;

import java.util.Date;

public class Payment {
    private int id;
    private int customerId;
    private int bookingId;
    private double amount;
    private Date date;
    private String method;

    public Payment() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }
}

