package com.flightreservation.business.service;

import java.util.List;
import java.util.Objects;

import com.flightreservation.data.repository.Repository;
import com.flightreservation.model.Payment;

public class PaymentService {
    private final Repository<Payment> paymentRepository;
    private final BookingService bookingService;

    public PaymentService(Repository<Payment> paymentRepository, BookingService bookingService) {
        this.paymentRepository = Objects.requireNonNull(paymentRepository);
        this.bookingService = Objects.requireNonNull(bookingService);
    }

    public Payment recordPayment(Payment payment) {
        paymentRepository.create(payment);
        bookingService.markPaid(payment.getBookingId());
        return payment;
    }

    public Payment getPayment(int id) {
        return paymentRepository.read(id);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.getAll();
    }
}
