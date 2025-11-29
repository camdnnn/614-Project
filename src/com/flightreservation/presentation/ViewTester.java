package com.flightreservation.presentation;

import com.flightreservation.model.*;
import com.flightreservation.presentation.views.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Test class to display and navigate through all views
 */
public class ViewTester {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            showMainMenu();
        });
    }
    
    private static void showMainMenu() {
        String[] options = {
            "1. Login View",
            "2. Role Selection View",
            "3. Customer Menu View",
            "4. Agent Menu View",
            "5. Admin Menu View",
            "6. Flight Search View",
            "7. Flight Results View",
            "8. Booking View",
            "9. Booking Confirmation View",
            "10. Booking History View",
            "11. Payment View",
            "12. Payment History View",
            "13. Monthly News View",
            "14. Customer Profile View",
            "15. Flight Management View",
            "Exit"
        };
        
        String selection = (String) JOptionPane.showInputDialog(
            null,
            "Select a view to test:",
            "View Tester",
            JOptionPane.PLAIN_MESSAGE,
            null,
            options,
            options[0]
        );
        
        if (selection == null || selection.equals("Exit")) {
            System.exit(0);
        }
        
        switch (selection) {
            case "1. Login View":
                testLoginView();
                break;
            case "2. Role Selection View":
                testRoleSelectionView();
                break;
            case "3. Customer Menu View":
                testCustomerMenuView();
                break;
            case "4. Agent Menu View":
                testAgentMenuView();
                break;
            case "5. Admin Menu View":
                testAdminMenuView();
                break;
            case "6. Flight Search View":
                testFlightSearchView();
                break;
            case "7. Flight Results View":
                testFlightResultsView();
                break;
            case "8. Booking View":
                testBookingView();
                break;
            case "9. Booking Confirmation View":
                testBookingConfirmationView();
                break;
            case "10. Booking History View":
                testBookingHistoryView();
                break;
            case "11. Payment View":
                testPaymentView();
                break;
            case "12. Payment History View":
                testPaymentHistoryView();
                break;
            case "13. Monthly News View":
                testMonthlyNewsView();
                break;
            case "14. Customer Profile View":
                testCustomerProfileView();
                break;
            case "15. Flight Management View":
                testFlightManagementView();
                break;
        }
    }
    
    private static void testLoginView() {
        LoginView view = new LoginView();
        view.addLoginListener(e -> {
            JOptionPane.showMessageDialog(view.getFrame(), 
                "Login clicked!\nUsername: " + view.getUsername() + 
                "\nPassword: " + view.getPassword());
        });
        view.display();
        addBackToMenu(view.getFrame());
    }
    
    private static void testRoleSelectionView() {
        RoleSelectionView view = new RoleSelectionView();
        view.addCustomerListener(e -> JOptionPane.showMessageDialog(view.getFrame(), "Customer selected!"));
        view.addAgentListener(e -> JOptionPane.showMessageDialog(view.getFrame(), "Agent selected!"));
        view.addAdminListener(e -> JOptionPane.showMessageDialog(view.getFrame(), "Admin selected!"));
        view.addBackListener(e -> {
            view.dispose();
            showMainMenu();
        });
        view.display();
    }
    
    private static void testCustomerMenuView() {
        CustomerMenuView view = new CustomerMenuView();
        view.addSearchFlightsListener(e -> JOptionPane.showMessageDialog(view.getFrame(), "Search Flights clicked!"));
        view.addViewBookingsListener(e -> JOptionPane.showMessageDialog(view.getFrame(), "View Bookings clicked!"));
        view.addViewPaymentsListener(e -> JOptionPane.showMessageDialog(view.getFrame(), "View Payments clicked!"));
        view.addViewNewsListener(e -> JOptionPane.showMessageDialog(view.getFrame(), "View News clicked!"));
        view.addLogoutListener(e -> {
            view.dispose();
            showMainMenu();
        });
        view.display();
    }
    
    private static void testAgentMenuView() {
        AgentMenuView view = new AgentMenuView();
        view.addManageBookingsListener(e -> JOptionPane.showMessageDialog(view.getFrame(), "Manage Bookings clicked!"));
        view.addManageCustomersListener(e -> JOptionPane.showMessageDialog(view.getFrame(), "Manage Customers clicked!"));
        view.addViewFlightsListener(e -> JOptionPane.showMessageDialog(view.getFrame(), "View Flights clicked!"));
        view.addViewPaymentsListener(e -> JOptionPane.showMessageDialog(view.getFrame(), "View Payments clicked!"));
        view.addLogoutListener(e -> {
            view.dispose();
            showMainMenu();
        });
        view.display();
    }
    
    private static void testAdminMenuView() {
        AdminMenuView view = new AdminMenuView();
        view.addManageFlightsListener(e -> JOptionPane.showMessageDialog(view.getFrame(), "Manage Flights clicked!"));
        view.addViewFlightsListener(e -> JOptionPane.showMessageDialog(view.getFrame(), "View Flights clicked!"));
        view.addLogoutListener(e -> {
            view.dispose();
            showMainMenu();
        });
        view.display();
    }
    
    private static void testFlightSearchView() {
        FlightSearchView view = new FlightSearchView();
        view.addSearchListener(e -> {
            JOptionPane.showMessageDialog(view.getFrame(), 
                "Search clicked!\nOrigin: " + view.getOrigin() + 
                "\nDestination: " + view.getDestination() +
                "\nDate: " + view.getDepartureDate() +
                "\nAirline: " + view.getSelectedAirline());
        });
        view.addClearListener(e -> view.clearFields());
        view.addBackListener(e -> {
            view.dispose();
            showMainMenu();
        });
        view.display();
    }
    
    private static void testFlightResultsView() {
        FlightResultsView view = new FlightResultsView();
        
        // Create sample flight data
        List<Flight> flights = createSampleFlights();
        view.displayFlights(flights);
        
        view.addSelectListener(e -> {
            Flight selected = view.getSelectedFlight();
            if (selected != null) {
                JOptionPane.showMessageDialog(view.getFrame(), 
                    "Selected flight ID: " + selected.getId() + 
                    "\nAirline: " + selected.getAirline());
            } else {
                JOptionPane.showMessageDialog(view.getFrame(), "Please select a flight first!");
            }
        });
        view.addBackListener(e -> {
            view.dispose();
            showMainMenu();
        });
        view.display();
    }
    
    private static void testBookingView() {
        BookingView view = new BookingView();
        
        // Display sample flight details
        Flight flight = createSampleFlights().get(0);
        view.displayFlightDetails(flight);
        
        view.addBookListener(e -> {
            JOptionPane.showMessageDialog(view.getFrame(), 
                "Booking requested!\nSeat: " + view.getSeatSelection() +
                "\nPassenger: " + view.getPassengerName() +
                "\nEmail: " + view.getPassengerEmail());
        });
        view.addBackListener(e -> {
            view.dispose();
            showMainMenu();
        });
        view.display();
    }
    
    private static void testBookingConfirmationView() {
        BookingConfirmationView view = new BookingConfirmationView();
        
        // Create sample booking and flight
        Booking booking = new Booking();
        booking.setId(12345);
        booking.setSeatNumber("12A");
        booking.setStatus("Confirmed");
        
        Flight flight = createSampleFlights().get(0);
        
        view.displayConfirmation(booking, flight);
        
        view.addPrintListener(e -> JOptionPane.showMessageDialog(view.getFrame(), "Print clicked!"));
        view.addDoneListener(e -> {
            view.dispose();
            showMainMenu();
        });
        view.display();
    }
    
    private static void testBookingHistoryView() {
        BookingHistoryView view = new BookingHistoryView();
        
        // Create sample bookings
        List<Booking> bookings = createSampleBookings();
        view.displayBookings(bookings);
        
        view.addViewDetailsListener(e -> {
            Booking selected = view.getSelectedBooking();
            if (selected != null) {
                JOptionPane.showMessageDialog(view.getFrame(), "View details for booking: " + selected.getId());
            } else {
                JOptionPane.showMessageDialog(view.getFrame(), "Please select a booking first!");
            }
        });
        view.addCancelBookingListener(e -> {
            Booking selected = view.getSelectedBooking();
            if (selected != null) {
                JOptionPane.showMessageDialog(view.getFrame(), "Cancel booking: " + selected.getId());
            } else {
                JOptionPane.showMessageDialog(view.getFrame(), "Please select a booking first!");
            }
        });
        view.addBackListener(e -> {
            view.dispose();
            showMainMenu();
        });
        view.display();
    }
    
    private static void testPaymentView() {
        PaymentView view = new PaymentView();
        view.displayAmount(299.99);
        
        view.addPayListener(e -> {
            JOptionPane.showMessageDialog(view.getFrame(), 
                "Payment submitted!\nCard: " + view.getCardNumber() +
                "\nHolder: " + view.getCardHolder() +
                "\nExpiry: " + view.getExpiryDate());
        });
        view.addCancelListener(e -> {
            view.dispose();
            showMainMenu();
        });
        view.display();
    }
    
    private static void testPaymentHistoryView() {
        PaymentHistoryView view = new PaymentHistoryView();
        
        // Create sample payments
        List<Payment> payments = createSamplePayments();
        view.displayPayments(payments);
        
        view.addViewDetailsListener(e -> {
            Payment selected = view.getSelectedPayment();
            if (selected != null) {
                JOptionPane.showMessageDialog(view.getFrame(), "View payment: " + selected.getId());
            } else {
                JOptionPane.showMessageDialog(view.getFrame(), "Please select a payment first!");
            }
        });
        view.addBackListener(e -> {
            view.dispose();
            showMainMenu();
        });
        view.display();
    }
    
    private static void testMonthlyNewsView() {
        MonthlyNewsView view = new MonthlyNewsView();
        
        // Create sample news
        MonthlyNews news = new MonthlyNews();
        news.setTitle("December 2025 Newsletter - Holiday Travel Deals!");
        news.setDate(new Date());
        news.setContent(
            "🎄 Holiday Travel Deals! 🎄\n\n" +
            "Book your holiday flights now and save up to 30%!\n\n" +
            "Featured Destinations:\n" +
            "• Toronto - Starting at $199\n" +
            "• Vancouver - Starting at $149\n" +
            "• Montreal - Starting at $249\n\n" +
            "New Routes:\n" +
            "We're excited to announce new direct flights to:\n" +
            "- Miami\n" +
            "- Los Angeles\n" +
            "- New York\n\n" +
            "Thank you for flying with us!"
        );
        
        view.displayNews(news);
        view.addRefreshListener(e -> JOptionPane.showMessageDialog(view.getFrame(), "Refresh clicked!"));
        view.addBackListener(e -> {
            view.dispose();
            showMainMenu();
        });
        view.display();
    }
    
    private static void testCustomerProfileView() {
        CustomerProfileView view = new CustomerProfileView();
        
        // Create sample customers
        List<User> customers = createSampleUsers();
        view.displayCustomers(customers);
        
        view.addSearchListener(e -> JOptionPane.showMessageDialog(view.getFrame(), 
            "Search for: " + view.getSearchQuery()));
        view.addAddListener(e -> JOptionPane.showMessageDialog(view.getFrame(), 
            "Add customer: " + view.getCustomerName()));
        view.addUpdateListener(e -> {
            User selected = view.getSelectedCustomer();
            if (selected != null) {
                JOptionPane.showMessageDialog(view.getFrame(), "Update customer: " + selected.getId());
            } else {
                JOptionPane.showMessageDialog(view.getFrame(), "Please select a customer first!");
            }
        });
        view.addClearListener(e -> view.clearFields());
        view.addBackListener(e -> {
            view.dispose();
            showMainMenu();
        });
        view.display();
    }
    
    private static void testFlightManagementView() {
        FlightManagementView view = new FlightManagementView();
        
        // Display sample flights
        List<Flight> flights = createSampleFlights();
        view.displayFlights(flights);
        
        view.addAddListener(e -> JOptionPane.showMessageDialog(view.getFrame(), 
            "Add flight:\nOrigin: " + view.getOrigin() + 
            "\nDestination: " + view.getDestination()));
        view.addUpdateListener(e -> {
            Flight selected = view.getSelectedFlight();
            if (selected != null) {
                JOptionPane.showMessageDialog(view.getFrame(), "Update flight: " + selected.getId());
            } else {
                JOptionPane.showMessageDialog(view.getFrame(), "Please select a flight first!");
            }
        });
        view.addRemoveListener(e -> {
            Flight selected = view.getSelectedFlight();
            if (selected != null) {
                JOptionPane.showMessageDialog(view.getFrame(), "Remove flight: " + selected.getId());
            } else {
                JOptionPane.showMessageDialog(view.getFrame(), "Please select a flight first!");
            }
        });
        view.addClearListener(e -> view.clearFields());
        view.addBackListener(e -> {
            view.dispose();
            showMainMenu();
        });
        view.display();
    }
    
    // Helper methods to create sample data
    
    private static List<Flight> createSampleFlights() {
        List<Flight> flights = new ArrayList<>();
        
        Flight f1 = new Flight();
        f1.setId(1);
        f1.setAirline("Air Canada");
        f1.setOrigin("Calgary");
        f1.setDestination("Toronto");
        f1.setDeparture(new Date(System.currentTimeMillis() + 86400000)); // Tomorrow
        f1.setArrival(new Date(System.currentTimeMillis() + 86400000 + 14400000)); // 4 hours later
        f1.setPrice(299.99);
        f1.setAvailableSeats(45);
        flights.add(f1);
        
        Flight f2 = new Flight();
        f2.setId(2);
        f2.setAirline("WestJet");
        f2.setOrigin("Calgary");
        f2.setDestination("Vancouver");
        f2.setDeparture(new Date(System.currentTimeMillis() + 172800000)); // 2 days
        f2.setArrival(new Date(System.currentTimeMillis() + 172800000 + 5400000)); // 1.5 hours
        f2.setPrice(199.99);
        f2.setAvailableSeats(32);
        flights.add(f2);
        
        Flight f3 = new Flight();
        f3.setId(3);
        f3.setAirline("United Airlines");
        f3.setOrigin("Calgary");
        f3.setDestination("Seattle");
        f3.setDeparture(new Date(System.currentTimeMillis() + 259200000)); // 3 days
        f3.setArrival(new Date(System.currentTimeMillis() + 259200000 + 7200000)); // 2 hours
        f3.setPrice(149.99);
        f3.setAvailableSeats(28);
        flights.add(f3);
        
        return flights;
    }
    
    private static List<Booking> createSampleBookings() {
        List<Booking> bookings = new ArrayList<>();
        
        Booking b1 = new Booking();
        b1.setId(1001);
        b1.setCustomerId(1);
        b1.setFlightId(1);
        b1.setSeatNumber("12A");
        b1.setDate(new Date());
        b1.setStatus("Confirmed");
        bookings.add(b1);
        
        Booking b2 = new Booking();
        b2.setId(1002);
        b2.setCustomerId(1);
        b2.setFlightId(2);
        b2.setSeatNumber("5C");
        b2.setDate(new Date(System.currentTimeMillis() - 86400000));
        b2.setStatus("Pending");
        bookings.add(b2);
        
        Booking b3 = new Booking();
        b3.setId(1003);
        b3.setCustomerId(1);
        b3.setFlightId(3);
        b3.setSeatNumber("8B");
        b3.setDate(new Date(System.currentTimeMillis() - 604800000));
        b3.setStatus("Completed");
        bookings.add(b3);
        
        return bookings;
    }
    
    private static List<Payment> createSamplePayments() {
        List<Payment> payments = new ArrayList<>();
        
        Payment p1 = new Payment();
        p1.setId(2001);
        p1.setCustomerId(1);
        p1.setBookingId(1001);
        p1.setAmount(299.99);
        p1.setDate(new Date());
        p1.setMethod("Credit Card");
        payments.add(p1);
        
        Payment p2 = new Payment();
        p2.setId(2002);
        p2.setCustomerId(1);
        p2.setBookingId(1002);
        p2.setAmount(199.99);
        p2.setDate(new Date(System.currentTimeMillis() - 86400000));
        p2.setMethod("Debit Card");
        payments.add(p2);
        
        return payments;
    }
    
    private static List<User> createSampleUsers() {
        List<User> users = new ArrayList<>();
        
        User u1 = new User();
        u1.setId(1);
        u1.setName("John Doe");
        u1.setEmail("john.doe@email.com");
        u1.setRole(User.Role.CUSTOMER);
        users.add(u1);
        
        User u2 = new User();
        u2.setId(2);
        u2.setName("Jane Smith");
        u2.setEmail("jane.smith@email.com");
        u2.setRole(User.Role.CUSTOMER);
        users.add(u2);
        
        User u3 = new User();
        u3.setId(3);
        u3.setName("Bob Wilson");
        u3.setEmail("bob.wilson@email.com");
        u3.setRole(User.Role.CUSTOMER);
        users.add(u3);
        
        return users;
    }
    
    private static void addBackToMenu(JFrame frame) {
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                showMainMenu();
            }
        });
    }
}
