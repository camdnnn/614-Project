package com.flightreservation.presentation;

import com.flightreservation.model.Flight;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

public class FlightResultsView {
    private JFrame frame;
    private JTable flightsTable;
    private DefaultTableModel tableModel;
    private JButton selectButton;
    private JButton backButton;
    private JLabel noResultsLabel;

    public FlightResultsView() {
        initializeComponents();
        layoutComponents();
    }

    private void initializeComponents() {
        frame = new JFrame("Flight Search Results");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(900, 500);
        frame.setLocationRelativeTo(null);

        // Table model with columns
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

        selectButton = new JButton("Select Flight");
        backButton = new JButton("Back");
        noResultsLabel = new JLabel("No flights found matching your criteria.");
        noResultsLabel.setForeground(Color.RED);
        noResultsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        noResultsLabel.setVisible(false);
    }

    private void layoutComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("Flight Search Results");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Table in scroll pane
        JScrollPane scrollPane = new JScrollPane(flightsTable);
        scrollPane.setPreferredSize(new Dimension(850, 350));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // No results label
        mainPanel.add(noResultsLabel, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(selectButton);
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

    public void displayFlights(List<Flight> flights) {
        tableModel.setRowCount(0);
        noResultsLabel.setVisible(false);
        
        if (flights == null || flights.isEmpty()) {
            showNoResults();
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

    public Flight getSelectedFlight() {
        int selectedRow = flightsTable.getSelectedRow();
        if (selectedRow == -1) {
            return null;
        }
        
        // Create a Flight object from selected row data
        // Note: This is a simplified approach - in real implementation,
        // the controller should maintain the flight list
        int id = (Integer) tableModel.getValueAt(selectedRow, 0);
        String airline = (String) tableModel.getValueAt(selectedRow, 1);
        String origin = (String) tableModel.getValueAt(selectedRow, 2);
        String destination = (String) tableModel.getValueAt(selectedRow, 3);
        
        // For now, return a minimal flight object
        // The controller should provide the actual Flight object
        Flight flight = new Flight();
        flight.setId(id);
        flight.setAirline(airline);
        flight.setOrigin(origin);
        flight.setDestination(destination);
        
        return flight;
    }

    public void showNoResults() {
        tableModel.setRowCount(0);
        noResultsLabel.setVisible(true);
    }

    public void addSelectListener(ActionListener listener) {
        selectButton.addActionListener(listener);
    }

    public void addBackListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }

    public JFrame getFrame() {
        return frame;
    }
}

