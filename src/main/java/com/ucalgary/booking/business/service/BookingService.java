package com.ucalgary.booking.business.service;

import java.util.Objects;

import com.ucalgary.booking.Booking;
import com.ucalgary.booking.BookingRepository;
import com.ucalgary.booking.BookingStatus;

public class BookingService {
    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = Objects.requireNonNull(bookingRepository);
    }

    public Booking getBookingById(long bookingId) {
        return bookingRepository.read(bookingId);
    }

    public void markPaid(long bookingId) {
        bookingRepository.updateStatus(bookingId, BookingStatus.PAID);
    }
}
