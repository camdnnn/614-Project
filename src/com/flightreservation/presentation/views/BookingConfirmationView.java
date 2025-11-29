package com.flightreservation.presentation;

import com.flightreservation.model.Booking;
import com.flightreservation.model.Flight;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

public class BookingConfirmationView {
    private JFrame frame;
    private JPanel confirmationPanel;
    private JLabel confirmationNumberLabel;
    private JLabel flightInfoLabel;
    private JLabel passengerInfoLabel;
    private JLabel seatInfoLabel;
    private JLabel statusLabel;
    private JButton printButton;
    private JButton doneButton;

    public BookingConfirmationView() {
        initializeComponents();
        layoutComponents();
    }

    private void initializeComponents() {
        frame = new JFrame("Booking Confirmation");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        confirmationPanel = new JPanel();
        confirmationPanel.setLayout(new BoxLayout(confirmationPanel, BoxLayout.Y_AXIS));
        confirmationPanel.setBorder(BorderFactory.createTitledBorder("Booking Confirmation"));
        confirmationPanel.setPreferredSize(new Dimension(550, 350));

        confirmationNumberLabel = new JLabel("Confirmation Number: ");
        confirmationNumberLabel.setFont(new Font("Arial", Font.BOLD, 16));

        flightInfoLabel = new JLabel("Flight Information: ");
        passengerInfoLabel = new JLabel("Passenger Information: ");
        seatInfoLabel = new JLabel("Seat: ");
        statusLabel = new JLabel("Status: ");

        printButton = new JButton("Print Confirmation");
        doneButton = new JButton("Done");
    }

    private void layoutComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("Booking Confirmation");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Confirmation panel
        confirmationPanel.add(Box.createVerticalStrut(20));
        confirmationPanel.add(confirmationNumberLabel);
        confirmationPanel.add(Box.createVerticalStrut(15));
        confirmationPanel.add(flightInfoLabel);
        confirmationPanel.add(Box.createVerticalStrut(10));
        confirmationPanel.add(passengerInfoLabel);
        confirmationPanel.add(Box.createVerticalStrut(10));
        confirmationPanel.add(seatInfoLabel);
        confirmationPanel.add(Box.createVerticalStrut(10));
        confirmationPanel.add(statusLabel);
        confirmationPanel.add(Box.createVerticalGlue());

        mainPanel.add(confirmationPanel, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(printButton);
        buttonPanel.add(doneButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
    }

    public void display() {
        frame.setVisible(true);
    }

    public void dispose() {
        frame.dispose();
    }

    public void displayConfirmation(Booking booking, Flight flight) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        
        // Set confirmation number (assuming booking ID is used)
        confirmationNumberLabel.setText("Confirmation Number: " + booking.getId());
        
        // Flight information
        String flightInfo = String.format(
            "Flight: %s | Route: %s → %s | Departure: %s | Arrival: %s | Price: $%.2f",
            flight.getAirline(),
            flight.getOrigin(),
            flight.getDestination(),
            dateFormat.format(flight.getDeparture()),
            dateFormat.format(flight.getArrival()),
            flight.getPrice()
        );
        flightInfoLabel.setText("Flight Information: " + flightInfo);
        
        // Passenger information (would need to get from User service)
        passengerInfoLabel.setText("Passenger Information: [To be populated by controller]");
        
        // Seat information
        seatInfoLabel.setText("Seat: " + booking.getSeatNumber());
        
        // Status
        statusLabel.setText("Status: " + booking.getStatus());
    }

    public void setConfirmationNumber(String number) {
        confirmationNumberLabel.setText("Confirmation Number: " + number);
    }

    public void addPrintListener(ActionListener listener) {
        printButton.addActionListener(listener);
    }

    public void addDoneListener(ActionListener listener) {
        doneButton.addActionListener(listener);
    }

    public JFrame getFrame() {
        return frame;
    }
}

