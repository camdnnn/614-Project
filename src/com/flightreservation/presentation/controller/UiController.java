package com.flightreservation.presentation.controller;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import com.flightreservation.business.service.AuthService;
import com.flightreservation.business.service.BookingService;
import com.flightreservation.business.service.FlightService;
import com.flightreservation.business.service.NewsService;
import com.flightreservation.business.service.PaymentService;
import com.flightreservation.business.service.UserService;
import com.flightreservation.model.Booking;
import com.flightreservation.model.Flight;
import com.flightreservation.model.Payment;
import com.flightreservation.model.User;
import com.flightreservation.presentation.views.AdminMenuView;
import com.flightreservation.presentation.views.AgentMenuView;
import com.flightreservation.presentation.views.BookingConfirmationView;
import com.flightreservation.presentation.views.BookingHistoryView;
import com.flightreservation.presentation.views.BookingView;
import com.flightreservation.presentation.views.CustomerMenuView;
import com.flightreservation.presentation.views.FlightResultsView;
import com.flightreservation.presentation.views.FlightSearchView;
import com.flightreservation.presentation.views.LoginView;
import com.flightreservation.presentation.views.MonthlyNewsView;
import com.flightreservation.presentation.views.PaymentHistoryView;
import com.flightreservation.presentation.views.PaymentView;

/**
 * Presentation controller that wires views to the services so the UI is usable.
 */
public class UiController {
    private final AuthService authService;
    private final NewsService newsService;
    private final FlightService flightService;
    private final BookingService bookingService;
    private final PaymentService paymentService;
    private final UserService userService;

    private User currentUser;
    private List<Flight> lastFlights;

    private LoginView loginView;
    private CustomerMenuView customerMenuView;
    private AgentMenuView agentMenuView;
    private AdminMenuView adminMenuView;
    private FlightSearchView flightSearchView;
    private FlightResultsView flightResultsView;
    private BookingView bookingView;
    private PaymentView paymentView;
    private MonthlyNewsView newsView;
    private BookingConfirmationView confirmationView;
    private BookingHistoryView bookingHistoryView;
    private PaymentHistoryView paymentHistoryView;

    public UiController(AuthService authService,
                        NewsService newsService,
                        FlightService flightService,
                        BookingService bookingService,
                        PaymentService paymentService,
                        UserService userService) {
        this.authService = Objects.requireNonNull(authService);
        this.newsService = Objects.requireNonNull(newsService);
        this.flightService = Objects.requireNonNull(flightService);
        this.bookingService = Objects.requireNonNull(bookingService);
        this.paymentService = Objects.requireNonNull(paymentService);
        this.userService = Objects.requireNonNull(userService);
    }

    public void start() {
        showLogin();
    }

    private void showLogin() {
        disposeAll();
        loginView = new LoginView();
        loginView.addLoginListener(e -> handleLogin());
        loginView.display();
    }

    private void handleLogin() {
        String email = loginView.getUsername();
        String password = loginView.getPassword();
        User user = authService.login(email, password);
        if (user == null) {
            loginView.showError("Invalid email or password");
            return;
        }
        currentUser = user;
        loginView.dispose();
        loginView = null;
        switch (user.getRole()) {
            case CUSTOMER:
                showCustomerMenu();
                break;
            case AGENT:
                showAgentMenu();
                break;
            case ADMIN:
                showAdminMenu();
                break;
            default:
                showLogin();
        }
    }

    private void showCustomerMenu() {
        disposeMenus();
        customerMenuView = new CustomerMenuView();
        customerMenuView.addLogoutListener(e -> showLogin());
        customerMenuView.addViewNewsListener(e -> showNews());
        customerMenuView.addViewBookingsListener(e -> showBookingHistory());
        customerMenuView.addViewPaymentsListener(e -> showPaymentHistory());
        customerMenuView.addSearchFlightsListener(e -> showFlightSearch());
        customerMenuView.display();
    }

    private void showAgentMenu() {
        disposeMenus();
        agentMenuView = new AgentMenuView();
        agentMenuView.addLogoutListener(e -> showLogin());
        agentMenuView.addViewFlightsListener(e -> showFlightSearch());
        agentMenuView.display();
    }

    private void showAdminMenu() {
        disposeMenus();
        adminMenuView = new AdminMenuView();
        adminMenuView.addLogoutListener(e -> showLogin());
        adminMenuView.addViewFlightsListener(e -> showFlightSearch());
        adminMenuView.display();
    }

    private void showFlightSearch() {
        closeSearchFlow();
        flightSearchView = new FlightSearchView();
        flightSearchView.addBackListener(e -> returnToMenu());
        flightSearchView.addClearListener(e -> flightSearchView.clearFields());
        flightSearchView.addSearchListener(e -> performFlightSearch());
        flightSearchView.display();
    }

    private void performFlightSearch() {
        String origin = flightSearchView.getOrigin();
        String destination = flightSearchView.getDestination();
        String airline = flightSearchView.getSelectedAirline();
        List<Flight> all = flightService.getAllFlights();

        lastFlights = all.stream()
                .filter(f -> origin.isEmpty() || f.getOrigin().equalsIgnoreCase(origin))
                .filter(f -> destination.isEmpty() || f.getDestination().equalsIgnoreCase(destination))
                .filter(f -> "All Airlines".equalsIgnoreCase(airline) || f.getAirline().equalsIgnoreCase(airline))
                .collect(Collectors.toList());

        if (flightResultsView == null) {
            flightResultsView = new FlightResultsView();
            flightResultsView.addBackListener(e -> {
                flightResultsView.dispose();
                flightResultsView = null;
                returnToMenu();
            });
            flightResultsView.addSelectListener(e -> handleFlightSelection());
        }

        flightResultsView.displayFlights(lastFlights);
        flightResultsView.display();
    }

    private void showBookingHistory() {
        if (currentUser == null) {
            showLogin();
            return;
        }

        List<Booking> myBookings = bookingService.getAllBookings().stream()
                .filter(b -> b.getCustomerId() == currentUser.getId())
                .collect(Collectors.toList());

        if (bookingHistoryView == null) {
            bookingHistoryView = new BookingHistoryView();
            bookingHistoryView.addBackListener(e -> {
                bookingHistoryView.dispose();
                bookingHistoryView = null;
                returnToMenu();
            });
        }

        bookingHistoryView.displayBookings(myBookings);
        bookingHistoryView.display();
    }

    private void showPaymentHistory() {
        if (currentUser == null) {
            showLogin();
            return;
        }

        List<Payment> myPayments = paymentService.getAllPayments().stream()
                .filter(p -> p.getCustomerId() == currentUser.getId())
                .collect(Collectors.toList());

        if (paymentHistoryView == null) {
            paymentHistoryView = new PaymentHistoryView();
            paymentHistoryView.addBackListener(e -> {
                paymentHistoryView.dispose();
                paymentHistoryView = null;
                returnToMenu();
            });
        }

        paymentHistoryView.displayPayments(myPayments);
        paymentHistoryView.display();
    }

    private void handleFlightSelection() {
        if (lastFlights == null || lastFlights.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No flights to select.", "Select Flight", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int idx = flightResultsView.getSelectedRowIndex();
        if (idx < 0 || idx >= lastFlights.size()) {
            JOptionPane.showMessageDialog(null, "Please select a flight first.", "Select Flight", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        Flight flight = lastFlights.get(idx);
        if (flightResultsView != null) {
            flightResultsView.dispose();
            flightResultsView = null;
        }
        showBooking(flight);
    }

    private void showBooking(Flight flight) {
        if (bookingView != null) {
            bookingView.dispose();
        }
        bookingView = new BookingView();
        bookingView.displayFlightDetails(flight);
        bookingView.addBookListener(e -> handleBooking(flight));
        bookingView.addBackListener(e -> {
            bookingView.dispose();
            bookingView = null;
            returnToMenu();
        });
        bookingView.display();
    }

    private void handleBooking(Flight flight) {
        if (currentUser == null) {
            JOptionPane.showMessageDialog(null, "Please log in again.", "Session expired", JOptionPane.WARNING_MESSAGE);
            showLogin();
            return;
        }

        Booking booking = new Booking(
                0,
                currentUser.getId(),
                flight.getId(),
                bookingView.getSeatSelection(),
                new Date(),
                "PENDING");

        bookingService.addBooking(booking);

        Integer bookingId = findLatestBookingIdForUserFlight(currentUser.getId(), flight.getId(), booking.getSeatNumber());
        if (bookingId == null) {
            JOptionPane.showMessageDialog(null, "Booking saved but could not retrieve confirmation.", "Booking", JOptionPane.WARNING_MESSAGE);
            returnToMenu();
            return;
        }

        // Proceed to payment
        showPayment(flight, bookingId);
    }

    private Integer findLatestBookingIdForUserFlight(int customerId, int flightId, String seat) {
        return bookingService.getAllBookings().stream()
                .filter(b -> b.getCustomerId() == customerId)
                .filter(b -> b.getFlightId() == flightId)
                .filter(b -> seat.equalsIgnoreCase(b.getSeatNumber()))
                .map(Booking::getId)
                .max(Integer::compareTo)
                .orElse(null);
    }

    private void showPayment(Flight flight, int bookingId) {
        if (paymentView != null) {
            paymentView.dispose();
        }
        paymentView = new PaymentView();
        paymentView.displayAmount(flight.getPrice());
        paymentView.addCancelListener(e -> {
            paymentView.dispose();
            paymentView = null;
            returnToMenu();
        });
        paymentView.addPayListener(e -> handlePayment(flight, bookingId));
        paymentView.display();
    }

    private void handlePayment(Flight flight, int bookingId) {
        if (currentUser == null) {
            JOptionPane.showMessageDialog(null, "Please log in again.", "Session expired", JOptionPane.WARNING_MESSAGE);
            showLogin();
            return;
        }

        Payment payment = new Payment(
                0,
                currentUser.getId(),
                bookingId,
                flight.getPrice(),
                new Date(),
                "CARD");

        paymentService.recordPayment(payment);
        paymentView.showSuccess();
        paymentView.dispose();
        paymentView = null;

        showConfirmation(bookingId, flight);
    }

    private void showConfirmation(int bookingId, Flight flight) {
        Booking booking = bookingService.getBookingById(bookingId);
        if (confirmationView != null) {
            confirmationView.dispose();
        }
        confirmationView = new BookingConfirmationView();
        if (booking != null) {
            confirmationView.displayConfirmation(booking, flight);
        } else {
            confirmationView.setConfirmationNumber(String.valueOf(bookingId));
        }
        confirmationView.addDoneListener(e -> {
            confirmationView.dispose();
            confirmationView = null;
            returnToMenu();
        });
        confirmationView.display();
    }

    private void showNews() {
        if (newsView != null) {
            newsView.dispose();
        }
        newsView = new MonthlyNewsView();
        newsView.addBackListener(e -> newsView.dispose());
        newsView.addRefreshListener(e -> {
            newsService.refreshNews();
            newsView.displayNews(newsService.getMonthlyNews());
        });
        newsView.displayNews(newsService.getMonthlyNews());
        newsView.display();
    }

    private void returnToMenu() {
        if (currentUser == null) {
            showLogin();
            return;
        }
        switch (currentUser.getRole()) {
            case CUSTOMER:
                showCustomerMenu();
                break;
            case AGENT:
                showAgentMenu();
                break;
            case ADMIN:
                showAdminMenu();
                break;
            default:
                showLogin();
        }
    }

    private void disposeMenus() {
        if (customerMenuView != null) {
            customerMenuView.dispose();
            customerMenuView = null;
        }
        if (agentMenuView != null) {
            agentMenuView.dispose();
            agentMenuView = null;
        }
        if (adminMenuView != null) {
            adminMenuView.dispose();
            adminMenuView = null;
        }
    }

    private void closeSearchFlow() {
        if (flightSearchView != null) {
            flightSearchView.dispose();
            flightSearchView = null;
        }
        if (flightResultsView != null) {
            flightResultsView.dispose();
            flightResultsView = null;
        }
        if (bookingView != null) {
            bookingView.dispose();
            bookingView = null;
        }
        if (paymentView != null) {
            paymentView.dispose();
            paymentView = null;
        }
        if (confirmationView != null) {
            confirmationView.dispose();
            confirmationView = null;
        }
    }

    private void disposeAll() {
        disposeMenus();
        closeSearchFlow();
        if (newsView != null) {
            newsView.dispose();
            newsView = null;
        }
        if (loginView != null) {
            loginView.dispose();
            loginView = null;
        }
    }
}
