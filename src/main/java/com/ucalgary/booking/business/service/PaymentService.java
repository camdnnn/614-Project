package com.ucalgary.booking.business.service;

import java.util.Objects;

import com.ucalgary.booking.CardDetails;
import com.ucalgary.booking.Payment;
import com.ucalgary.booking.PaymentStrategy;

public class PaymentService {
    private final PaymentStrategy paymentStrategy;

    public PaymentService(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = Objects.requireNonNull(paymentStrategy);
    }

    public Payment processPaymentWithStrategy(long bookingId,
                                              double amount,
                                              CardDetails cardDetails) {
        return paymentStrategy.pay(bookingId, amount, cardDetails);
    }
}
