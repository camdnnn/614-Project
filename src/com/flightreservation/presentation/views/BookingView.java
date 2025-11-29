package com.flightreservation.presentation.views;

import com.flightreservation.model.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

public class BookingView {
    private JFrame frame;
    private JPanel flightDetailsPanel;
    private JComboBox<String> seatComboBox;
    private JTextField passengerNameField;
    private JTextField passengerEmailField;
    private JButton bookButton;
    private JButton cancelButton;
    private JButton modifyButton;
    private JButton backButton;

    public BookingView() {
        initializeComponents();
        layoutComponents();
    }

    private void initializeComponents() {
        frame = new JFrame("Booking");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        flightDetailsPanel = new JPanel();
        flightDetailsPanel.setLayout(new BoxLayout(flightDetailsPanel, BoxLayout.Y_AXIS));
        flightDetailsPanel.setBorder(BorderFactory.createTitledBorder("Flight Details"));
        flightDetailsPanel.setPreferredSize(new Dimension(550, 150));

        // Initialize seat combo box with common seat numbers
        String[] seats = {"1A", "1B", "1C", "1D", "2A", "2B", "2C", "2D", "3A", "3B", "3C", "3D"};
        seatComboBox = new JComboBox<>(seats);

        passengerNameField = new JTextField(25);
        passengerEmailField = new JTextField(25);

        bookButton = new JButton("Book");
        cancelButton = new JButton("Cancel Booking");
        modifyButton = new JButton("Modify Booking");
        backButton = new JButton("Back");

        // Initially hide cancel and modify buttons (shown when editing existing booking)
        cancelButton.setVisible(false);
        modifyButton.setVisible(false);
    }

    private void layoutComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("Flight Booking");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Flight details panel
        mainPanel.add(flightDetailsPanel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Seat selection
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Select Seat:"), gbc);
        gbc.gridx = 1;
        formPanel.add(seatComboBox, gbc);

        // Passenger name
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Passenger Name:"), gbc);
        gbc.gridx = 1;
        formPanel.add(passengerNameField, gbc);

        // Passenger email
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Passenger Email:"), gbc);
        gbc.gridx = 1;
        formPanel.add(passengerEmailField, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(bookButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(backButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
    }

    public void display() {
        frame.setVisible(true);
    }

    public void dispose() {
        frame.dispose();
    }

    public void displayFlightDetails(Flight flight) {
        flightDetailsPanel.removeAll();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        
        JLabel airlineLabel = new JLabel("Airline: " + flight.getAirline());
        JLabel routeLabel = new JLabel("Route: " + flight.getOrigin() + " → " + flight.getDestination());
        JLabel departureLabel = new JLabel("Departure: " + dateFormat.format(flight.getDeparture()));
        JLabel arrivalLabel = new JLabel("Arrival: " + dateFormat.format(flight.getArrival()));
        JLabel priceLabel = new JLabel("Price: $" + String.format("%.2f", flight.getPrice()));
        JLabel seatsLabel = new JLabel("Available Seats: " + flight.getAvailableSeats());

        flightDetailsPanel.add(airlineLabel);
        flightDetailsPanel.add(routeLabel);
        flightDetailsPanel.add(departureLabel);
        flightDetailsPanel.add(arrivalLabel);
        flightDetailsPanel.add(priceLabel);
        flightDetailsPanel.add(seatsLabel);

        // Show book button, hide modify/cancel
        bookButton.setVisible(true);
        modifyButton.setVisible(false);
        cancelButton.setVisible(false);

        flightDetailsPanel.revalidate();
        flightDetailsPanel.repaint();
    }

    public void displayBookingDetails(Booking booking) {
        // Populate form with booking details
        seatComboBox.setSelectedItem(booking.getSeatNumber());
        
        // Show modify and cancel buttons, hide book button
        bookButton.setVisible(false);
        modifyButton.setVisible(true);
        cancelButton.setVisible(true);
    }

    public String getSeatSelection() {
        return (String) seatComboBox.getSelectedItem();
    }

    public String getPassengerName() {
        return passengerNameField.getText().trim();
    }

    public String getPassengerEmail() {
        return passengerEmailField.getText().trim();
    }

    public void addBookListener(ActionListener listener) {
        bookButton.addActionListener(listener);
    }

    public void addCancelListener(ActionListener listener) {
        cancelButton.addActionListener(listener);
    }

    public void addModifyListener(ActionListener listener) {
        modifyButton.addActionListener(listener);
    }

    public void addBackListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }

    public JFrame getFrame() {
        return frame;
    }
}

