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
import com.flightreservation.model.Role;
import com.flightreservation.model.User;
import com.flightreservation.presentation.views.AdminMenuView;
import com.flightreservation.presentation.views.AgentMenuView;
import com.flightreservation.presentation.views.BookingConfirmationView;
import com.flightreservation.presentation.views.BookingHistoryView;
import com.flightreservation.presentation.views.BookingView;
import com.flightreservation.presentation.views.CustomerProfileView;
import com.flightreservation.presentation.views.CustomerMenuView;
import com.flightreservation.presentation.views.FlightManagementView;
import com.flightreservation.presentation.views.FlightResultsView;
import com.flightreservation.presentation.views.FlightSearchView;
import com.flightreservation.presentation.views.LoginView;
import com.flightreservation.presentation.views.MonthlyNewsView;
import com.flightreservation.presentation.views.PaymentHistoryView;
import com.flightreservation.presentation.views.PaymentView;
import com.flightreservation.presentation.views.RoleSelectionView;

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
    private FlightManagementView flightManagementView;
    private CustomerProfileView customerProfileView;
    private RoleSelectionView roleSelectionView;

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
        showRoleSelect();
    }

    private void showRoleSelect() {
        disposeAll();
        roleSelectionView = new RoleSelectionView();
        roleSelectionView.addCustomerListener(e -> gotoLogin());
        roleSelectionView.addAgentListener(e -> gotoLogin());
        roleSelectionView.addAdminListener(e -> gotoLogin());
        roleSelectionView.addBackListener(e -> gotoLogin());
        roleSelectionView.display();
    }

    private void gotoLogin() {
        if (roleSelectionView != null) {
            roleSelectionView.dispose();
            roleSelectionView = null;
        }
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
        agentMenuView.addManageBookingsListener(e -> showAgentBookings());
        agentMenuView.addViewPaymentsListener(e -> showAllPayments());
        agentMenuView.addManageCustomersListener(e -> showCustomerProfiles());
        agentMenuView.addViewFlightsListener(e -> showFlightManagement(false));
        agentMenuView.display();
    }

    private void showAdminMenu() {
        disposeMenus();
        adminMenuView = new AdminMenuView();
        adminMenuView.addLogoutListener(e -> showLogin());
        adminMenuView.addViewFlightsListener(e -> showFlightSearch());
        adminMenuView.addManageFlightsListener(e -> showFlightManagement(true));
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

        initFlightResultsView();
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
            bookingHistoryView.addViewDetailsListener(e -> showBookingDetails());
            bookingHistoryView.addCancelBookingListener(e -> cancelSelectedBooking());
            bookingHistoryView.addModifyBookingListener(e -> modifySelectedBooking());
        }

        bookingHistoryView.displayBookings(myBookings);
        bookingHistoryView.display();
    }

    private void showAgentBookings() {
        if (currentUser == null) {
            showLogin();
            return;
        }
        List<Booking> allBookings = bookingService.getAllBookings();
        if (bookingHistoryView == null) {
            bookingHistoryView = new BookingHistoryView();
            bookingHistoryView.addBackListener(e -> {
                bookingHistoryView.dispose();
                bookingHistoryView = null;
                returnToMenu();
            });
            bookingHistoryView.addViewDetailsListener(e -> showBookingDetails());
            bookingHistoryView.addCancelBookingListener(e -> cancelSelectedBooking());
            bookingHistoryView.addModifyBookingListener(e -> modifySelectedBooking());
        }
        bookingHistoryView.displayBookings(allBookings);
        bookingHistoryView.display();
    }

    private void showBookingDetails() {
        Booking b = bookingHistoryView.getSelectedBooking();
        if (b == null) {
            JOptionPane.showMessageDialog(null, "Pick a booking first.", "Booking", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(null,
                "Booking #" + b.getId() +
                "\nFlight: " + b.getFlightId() +
                "\nSeat: " + b.getSeatNumber() +
                "\nStatus: " + b.getStatus(),
                "Booking Details",
                JOptionPane.PLAIN_MESSAGE);
    }

    private void cancelSelectedBooking() {
        Booking b = bookingHistoryView.getSelectedBooking();
        if (b == null) {
            JOptionPane.showMessageDialog(null, "Pick a booking first.", "Booking", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(null, "Cancel this booking?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Booking cancelled = new Booking(b.getId(), b.getCustomerId(), b.getFlightId(), b.getSeatNumber(), b.getBookingDate(), "CANCELLED");
            bookingService.updateBooking(b.getId(), cancelled);
            refreshBookings();
        }
    }

    private void modifySelectedBooking() {
        Booking b = bookingHistoryView.getSelectedBooking();
        if (b == null) {
            JOptionPane.showMessageDialog(null, "Pick a booking first.", "Booking", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String newSeat = JOptionPane.showInputDialog("New seat number:", b.getSeatNumber());
        if (newSeat == null || newSeat.isBlank()) {
            return;
        }
        Booking updated = new Booking(b.getId(), b.getCustomerId(), b.getFlightId(), newSeat, b.getBookingDate(), b.getStatus());
        bookingService.updateBooking(b.getId(), updated);
        refreshBookings();
    }

    private void refreshBookings() {
        if (currentUser == null || bookingHistoryView == null) return;
        List<Booking> myBookings = bookingService.getAllBookings().stream()
                .filter(b -> b.getCustomerId() == currentUser.getId())
                .collect(Collectors.toList());
        bookingHistoryView.displayBookings(myBookings);
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
            paymentHistoryView.addViewDetailsListener(e -> openPaymentDetails());
        }

        paymentHistoryView.displayPayments(myPayments);
        paymentHistoryView.display();
    }

    private void showAllPayments() {
        if (currentUser == null) {
            showLogin();
            return;
        }
        if (paymentHistoryView == null) {
            paymentHistoryView = new PaymentHistoryView();
            paymentHistoryView.addBackListener(e -> {
                paymentHistoryView.dispose();
                paymentHistoryView = null;
                returnToMenu();
            });
            paymentHistoryView.addViewDetailsListener(e -> openPaymentDetails());
        }
        paymentHistoryView.displayPayments(paymentService.getAllPayments());
        paymentHistoryView.display();
    }

    private void openPaymentDetails() {
        Payment p = paymentHistoryView.getSelectedPayment();
        if (p == null) {
            JOptionPane.showMessageDialog(null, "Pick a payment first.", "Payments", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        PaymentView pv = new PaymentView();
        pv.displayAmount(p.getAmount());
        pv.showError("Enter card info to re-pay booking #" + p.getBookingId());
        pv.display();
    }

    private void showFlightManagement(boolean requestEdit) {
        if (currentUser == null) {
            showLogin();
            return;
        }
        if (currentUser.getRole() != Role.ADMIN && currentUser.getRole() != Role.AGENT) {
            JOptionPane.showMessageDialog(null, "You do not have access.", "Access denied", JOptionPane.WARNING_MESSAGE);
            returnToMenu();
            return;
        }

        if (flightManagementView == null) {
            flightManagementView = new FlightManagementView();
            flightManagementView.addBackListener(e -> {
                flightManagementView.dispose();
                flightManagementView = null;
                returnToMenu();
            });
            flightManagementView.addAddListener(e -> handleAddFlight());
            flightManagementView.addUpdateListener(e -> handleUpdateFlight());
            flightManagementView.addRemoveListener(e -> handleRemoveFlight());
            flightManagementView.addClearListener(e -> flightManagementView.clearFields());
        }

        boolean canEdit = requestEdit && currentUser.getRole() == Role.ADMIN;
        flightManagementView.setEditingEnabled(canEdit);
        flightManagementView.displayFlights(flightService.getAllFlights());
        flightManagementView.display();
    }

    private void showCustomerProfiles() {
        if (currentUser == null) {
            showLogin();
            return;
        }
        if (customerProfileView == null) {
            customerProfileView = new CustomerProfileView();
            customerProfileView.addBackListener(e -> {
                customerProfileView.dispose();
                customerProfileView = null;
                returnToMenu();
            });
            customerProfileView.addClearListener(e -> customerProfileView.clearFields());
            customerProfileView.addAddListener(e -> handleAddCustomer());
            customerProfileView.addUpdateListener(e -> handleUpdateCustomer());
            customerProfileView.addSearchListener(e -> refreshCustomerTable());
        }
        refreshCustomerTable();
        customerProfileView.display();
    }

    private void handleAddCustomer() {
        String name = customerProfileView.getCustomerName();
        String email = customerProfileView.getCustomerEmail();
        String pass = customerProfileView.getCustomerPassword();
        if (name.isBlank() || email.isBlank() || pass.isBlank()) {
            JOptionPane.showMessageDialog(null, "Fill in name, email, and password.", "Customer", JOptionPane.WARNING_MESSAGE);
            return;
        }
        userService.createUser(new User(0, name, email, pass, Role.CUSTOMER));
        refreshCustomerTable();
        customerProfileView.clearFields();
    }

    private void handleUpdateCustomer() {
        User selected = customerProfileView.getSelectedCustomer();
        if (selected == null) {
            JOptionPane.showMessageDialog(null, "Pick a customer row first.", "Customer", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String name = customerProfileView.getCustomerName();
        String email = customerProfileView.getCustomerEmail();
        String pass = customerProfileView.getCustomerPassword();
        if (name.isBlank() || email.isBlank() || pass.isBlank()) {
            JOptionPane.showMessageDialog(null, "Fill in name, email, and password.", "Customer", JOptionPane.WARNING_MESSAGE);
            return;
        }
        userService.updateUser(selected.getId(), new User(selected.getId(), name, email, pass, Role.CUSTOMER));
        refreshCustomerTable();
    }

    private void refreshCustomerTable() {
        List<User> users = userService.getAllUsers();
        String q = customerProfileView.getSearchQuery().toLowerCase();
        List<User> customers = users.stream()
                .filter(u -> u.getRole() == Role.CUSTOMER)
                .filter(u -> q.isBlank() || u.getName().toLowerCase().contains(q) || u.getEmail().toLowerCase().contains(q))
                .collect(Collectors.toList());
        customerProfileView.displayCustomers(customers);
    }

    private void handleAddFlight() {
        if (!isAdmin()) {
            JOptionPane.showMessageDialog(null, "Only administrators can add flights.", "Access denied", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Flight flight = buildFlightFromForm(false);
        if (flight == null) return;
        flightService.addFlight(flight);
        refreshFlightsTable();
        flightManagementView.clearFields();
    }

    private void handleUpdateFlight() {
        if (!isAdmin()) {
            JOptionPane.showMessageDialog(null, "Only administrators can update flights.", "Access denied", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String idStr = flightManagementView.getFlightId();
        if (idStr == null || idStr.isBlank()) {
            JOptionPane.showMessageDialog(null, "Select a flight to update.", "Update Flight", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid flight ID.", "Update Flight", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Flight flight = buildFlightFromForm(true);
        if (flight == null) return;
        flightService.updateFlight(id, flight);
        refreshFlightsTable();
    }

    private void handleRemoveFlight() {
        if (!isAdmin()) {
            JOptionPane.showMessageDialog(null, "Only administrators can remove flights.", "Access denied", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String idStr = flightManagementView.getFlightId();
        if (idStr == null || idStr.isBlank()) {
            JOptionPane.showMessageDialog(null, "Select a flight to remove.", "Remove Flight", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid flight ID.", "Remove Flight", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove this flight?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            flightService.removeFlight(id);
            refreshFlightsTable();
            flightManagementView.clearFields();
        }
    }

    private Flight buildFlightFromForm(boolean requireId) {
        String airline = flightManagementView.getAirline();
        String origin = flightManagementView.getOrigin();
        String destination = flightManagementView.getDestination();
        String departureStr = flightManagementView.getDepartureDate();
        String arrivalStr = flightManagementView.getArrivalDate();
        String priceStr = flightManagementView.getPrice();
        String seatsStr = flightManagementView.getSeats();

        if (airline.isBlank() || origin.isBlank() || destination.isBlank()
                || departureStr.isBlank() || arrivalStr.isBlank() || priceStr.isBlank() || seatsStr.isBlank()) {
            JOptionPane.showMessageDialog(null, "All fields except Flight ID are required.", "Validation", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        Date departure = parseDateTime(departureStr);
        Date arrival = parseDateTime(arrivalStr);
        if (departure == null || arrival == null) {
            JOptionPane.showMessageDialog(null, "Dates must be in format yyyy-MM-dd HH:mm.", "Validation", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        float price;
        int seats;
        try {
            price = Float.parseFloat(priceStr);
            seats = Integer.parseInt(seatsStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Price must be a number and seats must be an integer.", "Validation", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        int id = 0;
        if (requireId) {
            try {
                id = Integer.parseInt(flightManagementView.getFlightId());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid flight ID.", "Validation", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }

        return new Flight(id, airline, origin, destination, departure, arrival, price, seats);
    }

    private Date parseDateTime(String text) {
        try {
            return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").parse(text);
        } catch (Exception e) {
            return null;
        }
    }

    private void refreshFlightsTable() {
        flightManagementView.displayFlights(flightService.getAllFlights());
    }

    private boolean isAdmin() {
        return currentUser != null && currentUser.getRole() == Role.ADMIN;
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

        String card = paymentView.getCardNumber();
        String holder = paymentView.getCardHolder();
        String exp = paymentView.getExpiryDate();
        String cvv = paymentView.getCvv();
        if (card.isBlank() || holder.isBlank() || exp.isBlank() || cvv.isBlank()) {
            paymentView.showError("Enter card, name, expiry, and CVV first.");
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
        if (roleSelectionView != null) {
            roleSelectionView.dispose();
            roleSelectionView = null;
        }
    }

    private void initFlightResultsView() {
        if (flightResultsView == null) {
            flightResultsView = new FlightResultsView();
            flightResultsView.addBackListener(e -> {
                flightResultsView.dispose();
                flightResultsView = null;
                returnToMenu();
            });
            flightResultsView.addSelectListener(e -> handleFlightSelection());
        }
    }
}
