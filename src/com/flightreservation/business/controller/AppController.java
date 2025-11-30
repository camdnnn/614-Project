package com.flightreservation.business.controller;

import java.util.List;
import java.util.Objects;

import com.flightreservation.business.service.BookingService;
import com.flightreservation.business.service.FlightService;
import com.flightreservation.business.service.PaymentService;
import com.flightreservation.business.service.UserService;
import com.flightreservation.model.Booking;
import com.flightreservation.model.Flight;
import com.flightreservation.model.Payment;
import com.flightreservation.model.User;

public final class AppController {
    private static volatile AppController instance;

    private final PaymentService paymentService;
    private final BookingService bookingService;
    private final FlightService flightService;
    private final UserService userService;

    private AppController(PaymentService paymentService,
                          BookingService bookingService,
                          FlightService flightService,
                          UserService userService) {
        this.paymentService = Objects.requireNonNull(paymentService);
        this.bookingService = Objects.requireNonNull(bookingService);
        this.flightService = Objects.requireNonNull(flightService);
        this.userService = Objects.requireNonNull(userService);
    }

    public static AppController init(PaymentService paymentService,
                                     BookingService bookingService,
                                     FlightService flightService,
                                     UserService userService) {
        if (instance == null) {
            synchronized (AppController.class) {
                if (instance == null) {
                    instance = new AppController(paymentService, bookingService, flightService, userService);
                }
            }
        }
        return instance;
    }

    public static AppController getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Controller not Initialized");
        }
        return instance;
    }

    public Payment payForBooking(Payment payment) {
        return paymentService.recordPayment(payment);
    }

    public Booking getBooking(int bookingId) {
        return bookingService.getBookingById(bookingId);
    }

    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    public List<Flight> getAllFlights() {
        return flightService.getAllFlights();
    }

    public void addFlight(Flight flight) {
        flightService.addFlight(flight);
    }

    public void updateFlight(int id, Flight flight) {
        flightService.updateFlight(id, flight);
    }

    public void removeFlight(int id) {
        flightService.removeFlight(id);
    }

    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    public void createUser(User user) {
        userService.createUser(user);
    }

    public void updateUser(int id, User user) {
        userService.updateUser(id, user);
    }

    public void deleteUser(int id) {
        userService.deleteUser(id);
    }
}
