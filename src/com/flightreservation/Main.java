package com.flightreservation;

import javax.swing.SwingUtilities;

import com.flightreservation.business.controller.AppController;
import com.flightreservation.business.service.AuthService;
import com.flightreservation.business.service.BookingService;
import com.flightreservation.business.service.FlightService;
import com.flightreservation.business.service.NewsService;
import com.flightreservation.business.service.PaymentService;
import com.flightreservation.business.service.UserService;
import com.flightreservation.data.repository.BookingRepository;
import com.flightreservation.data.repository.FlightRepository;
import com.flightreservation.data.repository.MonthlyNewsRepository;
import com.flightreservation.data.repository.PaymentRepository;
import com.flightreservation.data.repository.UserRepository;
import com.flightreservation.presentation.controller.UiController;

/**
 * Application entrypoint that wires services and launches the Swing UI controller.
 */
public class Main {
    public static void main(String[] args) {
        // Data repositories
        UserRepository userRepository = new UserRepository();
        FlightRepository flightRepository = new FlightRepository();
        BookingRepository bookingRepository = new BookingRepository();
        PaymentRepository paymentRepository = new PaymentRepository();
        MonthlyNewsRepository monthlyNewsRepository = new MonthlyNewsRepository();

        // Services
        UserService userService = new UserService(userRepository);
        FlightService flightService = new FlightService(flightRepository);
        BookingService bookingService = new BookingService(bookingRepository);
        PaymentService paymentService = new PaymentService(paymentRepository, bookingService);
        AuthService authService = new AuthService(userRepository);
        NewsService newsService = new NewsService(monthlyNewsRepository);

        // Initialize central controller for domain services
        AppController.init(paymentService, bookingService, flightService, userService);

        // Launch UI
        SwingUtilities.invokeLater(() -> {
            UiController ui = new UiController(
                    authService,
                    newsService,
                    flightService,
                    bookingService,
                    paymentService,
                    userService);
            ui.start();
        });
    }
}
