package com.flightreservation.presentation.views;

import com.flightreservation.model.*;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FlightManagementView {
    private JFrame frame;
    private JTable flightsTable;
    private DefaultTableModel tableModel;
    private JTextField flightIdField;
    private JTextField originField;
    private JTextField destinationField;
    private JTextField departureDateField;
    private JTextField arrivalDateField;
    private JTextField priceField;
    private JTextField seatsField;
    private JButton addButton;
    private JButton updateButton;
    private JButton removeButton;
    private JButton clearButton;
    private JButton backButton;

    public FlightManagementView() {
        initializeComponents();
        layoutComponents();
    }

    private void initializeComponents() {
        frame = new JFrame("Flight Management");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null);

        // Table model
        String[] columnNames = {"ID", "Airline", "Origin", "Destination", "Departure", "Arrival", "Price", "Available Seats"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        flightsTable = new JTable(tableModel);
        flightsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        flightsTable.setRowHeight(25);
        
        // Add selection listener to populate form fields
        flightsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                populateFormFromSelectedRow();
            }
        });

        // Form fields
        flightIdField = new JTextField(15);
        flightIdField.setEditable(false);
        originField = new JTextField(15);
        destinationField = new JTextField(15);
        departureDateField = new JTextField(15);
        arrivalDateField = new JTextField(15);
        priceField = new JTextField(15);
        seatsField = new JTextField(15);

        // Buttons
        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        removeButton = new JButton("Remove");
        clearButton = new JButton("Clear");
        backButton = new JButton("Back");
    }

    private void layoutComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("Flight Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Split pane for table and form
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerLocation(300);
        splitPane.setResizeWeight(0.5);

        // Table panel
        JScrollPane tableScrollPane = new JScrollPane(flightsTable);
        splitPane.setTopComponent(tableScrollPane);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Flight ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Flight ID:"), gbc);
        gbc.gridx = 1;
        formPanel.add(flightIdField, gbc);

        // Origin
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Origin:"), gbc);
        gbc.gridx = 1;
        formPanel.add(originField, gbc);

        // Destination
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Destination:"), gbc);
        gbc.gridx = 1;
        formPanel.add(destinationField, gbc);

        // Departure Date
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Departure Date:"), gbc);
        gbc.gridx = 1;
        formPanel.add(departureDateField, gbc);

        // Arrival Date
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Arrival Date:"), gbc);
        gbc.gridx = 1;
        formPanel.add(arrivalDateField, gbc);

        // Price
        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(new JLabel("Price:"), gbc);
        gbc.gridx = 1;
        formPanel.add(priceField, gbc);

        // Available Seats
        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(new JLabel("Available Seats:"), gbc);
        gbc.gridx = 1;
        formPanel.add(seatsField, gbc);

        splitPane.setBottomComponent(formPanel);

        mainPanel.add(splitPane, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(backButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
    }

    private void populateFormFromSelectedRow() {
        int selectedRow = flightsTable.getSelectedRow();
        if (selectedRow != -1) {
            flightIdField.setText(tableModel.getValueAt(selectedRow, 0).toString());
            originField.setText(tableModel.getValueAt(selectedRow, 2).toString());
            destinationField.setText(tableModel.getValueAt(selectedRow, 3).toString());
            departureDateField.setText(tableModel.getValueAt(selectedRow, 4).toString());
            arrivalDateField.setText(tableModel.getValueAt(selectedRow, 5).toString());
            priceField.setText(tableModel.getValueAt(selectedRow, 6).toString().replace("$", ""));
            seatsField.setText(tableModel.getValueAt(selectedRow, 7).toString());
        }
    }

    public void display() {
        frame.setVisible(true);
    }

    public void dispose() {
        frame.dispose();
    }

    public void displayFlights(List<Flight> flights) {
        tableModel.setRowCount(0);
        
        if (flights == null || flights.isEmpty()) {
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        
        for (Flight flight : flights) {
            Object[] row = {
                flight.getId(),
                flight.getAirline(),
                flight.getOrigin(),
                flight.getDestination(),
                dateFormat.format(flight.getDeparture()),
                dateFormat.format(flight.getArrival()),
                String.format("$%.2f", flight.getPrice()),
                flight.getAvailableSeats()
            };
            tableModel.addRow(row);
        }
    }

    public String getFlightId() {
        return flightIdField.getText().trim();
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

    public String getArrivalDate() {
        return arrivalDateField.getText().trim();
    }

    public String getPrice() {
        return priceField.getText().trim();
    }

    public String getSeats() {
        return seatsField.getText().trim();
    }

    public Flight getSelectedFlight() {
        int selectedRow = flightsTable.getSelectedRow();
        if (selectedRow == -1) {
            return null;
        }
        
        int id = (Integer) tableModel.getValueAt(selectedRow, 0);
        String airline = (String) tableModel.getValueAt(selectedRow, 1);
        String origin = (String) tableModel.getValueAt(selectedRow, 2);
        String destination = (String) tableModel.getValueAt(selectedRow, 3);
        Date departure = (Date) tableModel.getValueAt(selectedRow, 4);
        Date arrival = (Date) tableModel.getValueAt(selectedRow, 5);
        Float price = Float.parseFloat(tableModel.getValueAt(selectedRow, 6).toString().replace("$", ""));
        int availableSeats = (Integer) tableModel.getValueAt(selectedRow, 7);

        Flight flight = new Flight(id, airline, origin, destination, departure, arrival, price, availableSeats);

        return flight;
    }

    public void clearFields() {
        flightIdField.setText("");
        originField.setText("");
        destinationField.setText("");
        departureDateField.setText("");
        arrivalDateField.setText("");
        priceField.setText("");
        seatsField.setText("");
        flightsTable.clearSelection();
    }

    public void addAddListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }

    public void addUpdateListener(ActionListener listener) {
        updateButton.addActionListener(listener);
    }

    public void addRemoveListener(ActionListener listener) {
        removeButton.addActionListener(listener);
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

