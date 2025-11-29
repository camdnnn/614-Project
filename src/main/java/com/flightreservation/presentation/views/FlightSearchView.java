package com.flightreservation.presentation.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class FlightSearchView {
    private JFrame frame;
    private JTextField originField;
    private JTextField destinationField;
    private JTextField departureDateField;
    private JComboBox<String> airlineComboBox;
    private JButton searchButton;
    private JButton clearButton;
    private JButton backButton;

    public FlightSearchView() {
        initializeComponents();
        layoutComponents();
    }

    private void initializeComponents() {
        frame = new JFrame("Search Flights");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 350);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        originField = new JTextField(20);
        destinationField = new JTextField(20);
        departureDateField = new JTextField(20);
        
        // Initialize airline combo box with common airlines
        String[] airlines = {"All Airlines", "Air Canada", "WestJet", "United Airlines", "American Airlines", "Delta"};
        airlineComboBox = new JComboBox<>(airlines);
        
        searchButton = new JButton("Search");
        clearButton = new JButton("Clear");
        backButton = new JButton("Back");
    }

    private void layoutComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("Flight Search");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Origin
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Origin:"), gbc);
        gbc.gridx = 1;
        formPanel.add(originField, gbc);

        // Destination
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Destination:"), gbc);
        gbc.gridx = 1;
        formPanel.add(destinationField, gbc);

        // Departure Date
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Departure Date (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        formPanel.add(departureDateField, gbc);

        // Airline
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Airline:"), gbc);
        gbc.gridx = 1;
        formPanel.add(airlineComboBox, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(searchButton);
        buttonPanel.add(clearButton);
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

    public String getOrigin() {
        return originField.getText().trim();
    }

    public String getDestination() {
        return destinationField.getText().trim();
    }

    public String getDepartureDate() {
        return departureDateField.getText().trim();
    }

    public String getSelectedAirline() {
        return (String) airlineComboBox.getSelectedItem();
    }

    public void clearFields() {
        originField.setText("");
        destinationField.setText("");
        departureDateField.setText("");
        airlineComboBox.setSelectedIndex(0);
    }

    public void addSearchListener(ActionListener listener) {
        searchButton.addActionListener(listener);
    }

    public void addClearListener(ActionListener listener) {
        clearButton.addActionListener(listener);
    }

    public void addBackListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }

    public JFrame getFrame() {
        return frame;
    }
}

