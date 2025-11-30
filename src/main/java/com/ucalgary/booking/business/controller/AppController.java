package com.ucalgary.booking.business.controller;

import java.util.List;
import java.util.Objects;

import com.ucalgary.booking.Booking;
import com.ucalgary.booking.CardDetails;
import com.ucalgary.booking.Flight;
import com.ucalgary.booking.Payment;
import com.ucalgary.booking.User;
import com.ucalgary.booking.business.service.BookingService;
import com.ucalgary.booking.business.service.FlightService;
import com.ucalgary.booking.business.service.PaymentService;
import com.ucalgary.booking.business.service.UserService;

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
            throw new IllegalStateException("AppController not initialized");
        }
        return instance;
    }

    public Payment payForBooking(long bookingId, double amount, CardDetails cardDetails) {
        return paymentService.processPaymentWithStrategy(bookingId, amount, cardDetails);
    }

    public Booking getBooking(long bookingId) {
        return bookingService.getBookingById(bookingId);
    }

    public List<Flight> getAllFlights() {
        return flightService.getAllFlights();
    }

    public void addFlight(Flight flight) {
        flightService.addFlight(flight);
    }

    public void updateFlight(long id, Flight flight) {
        flightService.updateFlight(id, flight);
    }

    public void removeFlight(long id) {
        flightService.removeFlight(id);
    }

    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    public void createUser(User user) {
        userService.createUser(user);
    }

    public void updateUser(long id, User user) {
        userService.updateUser(id, user);
    }
}
