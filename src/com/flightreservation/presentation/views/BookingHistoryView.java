package com.flightreservation.presentation.views;

import com.flightreservation.model.*;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

public class BookingHistoryView {
    private JFrame frame;
    private JTable bookingsTable;
    private DefaultTableModel tableModel;
    private JButton viewDetailsButton;
    private JButton cancelBookingButton;
    private JButton modifyBookingButton;
    private JButton backButton;

    public BookingHistoryView() {
        initializeComponents();
        layoutComponents();
    }

    private void initializeComponents() {
        frame = new JFrame("Booking History");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(900, 500);
        frame.setLocationRelativeTo(null);

        // Table model
        String[] columnNames = {"ID", "Customer ID", "Flight ID", "Seat Number", "Date", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        bookingsTable = new JTable(tableModel);
        bookingsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        bookingsTable.setRowHeight(25);

        viewDetailsButton = new JButton("View Details");
        cancelBookingButton = new JButton("Cancel Booking");
        modifyBookingButton = new JButton("Modify Booking");
        backButton = new JButton("Back");
    }

    private void layoutComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("Booking History");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Table in scroll pane
        JScrollPane scrollPane = new JScrollPane(bookingsTable);
        scrollPane.setPreferredSize(new Dimension(850, 350));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(viewDetailsButton);
        buttonPanel.add(modifyBookingButton);
        buttonPanel.add(cancelBookingButton);
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

    public void displayBookings(List<Booking> bookings) {
        tableModel.setRowCount(0);
        
        if (bookings == null || bookings.isEmpty()) {
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        for (Booking booking : bookings) {
            Object[] row = {
                booking.getId(),
                booking.getCustomerId(),
                booking.getFlightId(),
                booking.getSeatNumber(),
                dateFormat.format(booking.getBookingDate()),
                booking.getStatus()
            };
            tableModel.addRow(row);
        }
    }

    public Booking getSelectedBooking() {
        int selectedRow = bookingsTable.getSelectedRow();
        if (selectedRow == -1) {
            return null;
        }
        
        int id = (Integer) tableModel.getValueAt(selectedRow, 0);
        int customerId = (Integer) tableModel.getValueAt(selectedRow, 1);
        int flightId = (Integer) tableModel.getValueAt(selectedRow, 2);
        String seatNumber = (String) tableModel.getValueAt(selectedRow, 3);
        String bookingDate = (String) tableModel.getValueAt(selectedRow, 4);
        String status = (String) tableModel.getValueAt(selectedRow, 5);
        
        Booking booking = new Booking(id, customerId, flightId, seatNumber, bookingDate, status);
        
        return booking;
    }

    public void refreshTable() {
        tableModel.fireTableDataChanged();
    }

    public void addViewDetailsListener(ActionListener listener) {
        viewDetailsButton.addActionListener(listener);
    }

    public void addCancelBookingListener(ActionListener listener) {
        cancelBookingButton.addActionListener(listener);
    }

    public void addModifyBookingListener(ActionListener listener) {
        modifyBookingButton.addActionListener(listener);
    }

    public void addBackListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }

    public JFrame getFrame() {
        return frame;
    }
}

