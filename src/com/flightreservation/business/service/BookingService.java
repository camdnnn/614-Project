package com.flightreservation.business.service;

import java.util.List;
import java.util.Objects;

import com.flightreservation.data.repository.Repository;
import com.flightreservation.model.Booking;

public class BookingService {
    private final Repository<Booking> bookingRepository;

    public BookingService(Repository<Booking> bookingRepository) {
        this.bookingRepository = Objects.requireNonNull(bookingRepository);
    }

    public Booking getBookingById(int bookingId) {
        return bookingRepository.read(bookingId);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.getAll();
    }

    public void addBooking(Booking booking) {
        bookingRepository.create(booking);
    }

    public void updateBooking(int id, Booking booking) {
        bookingRepository.update(id, booking);
    }

    public void deleteBooking(int id) {
        bookingRepository.delete(id);
    }

    public void markPaid(int bookingId) {
        Booking existing = bookingRepository.read(bookingId);
        if (existing == null) {
            return;
        }
        Booking updated = new Booking(
                existing.getId(),
                existing.getCustomerId(),
                existing.getFlightId(),
                existing.getSeatNumber(),
                existing.getBookingDate(),
                "PAID");
        bookingRepository.update(bookingId, updated);
    }
}
