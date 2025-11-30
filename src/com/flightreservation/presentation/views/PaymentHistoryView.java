package com.flightreservation.presentation.views;

import com.flightreservation.model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PaymentHistoryView {
    private JFrame frame;
    private JTable paymentsTable;
    private DefaultTableModel tableModel;
    private JButton viewDetailsButton;
    private JButton backButton;

    public PaymentHistoryView() {
        initializeComponents();
        layoutComponents();
    }

    private void initializeComponents() {
        frame = new JFrame("Payment History");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(900, 500);
        frame.setLocationRelativeTo(null);

        // Table model
        String[] columnNames = {"ID", "Customer ID", "Booking ID", "Amount", "Date", "Method"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        paymentsTable = new JTable(tableModel);
        paymentsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        paymentsTable.setRowHeight(25);

        viewDetailsButton = new JButton("View Details");
        backButton = new JButton("Back");
    }

    private void layoutComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("Payment History");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Table in scroll pane
        JScrollPane scrollPane = new JScrollPane(paymentsTable);
        scrollPane.setPreferredSize(new Dimension(850, 350));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(viewDetailsButton);
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

    public void displayPayments(List<Payment> payments) {
        tableModel.setRowCount(0);
        
        if (payments == null || payments.isEmpty()) {
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        for (Payment payment : payments) {
            Object[] row = {
                payment.getId(),
                payment.getCustomerId(),
                payment.getBookingId(),
                String.format("$%.2f", payment.getAmount()),
                dateFormat.format(payment.getPaymentDate()),
                payment.getMethod()
            };
            tableModel.addRow(row);
        }
    }

    public Payment getSelectedPayment() {
        int selectedRow = paymentsTable.getSelectedRow();
        if (selectedRow == -1) {
            return null;
        }
        
        int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
        int customerId = Integer.parseInt(tableModel.getValueAt(selectedRow, 1).toString());
        int bookingId = Integer.parseInt(tableModel.getValueAt(selectedRow, 2).toString());
        String amountStr = tableModel.getValueAt(selectedRow, 3).toString();
        float amount = Float.parseFloat(amountStr.replace("$", ""));
        String dateStr = tableModel.getValueAt(selectedRow, 4).toString();
        Date paymentDate;
        try {
            paymentDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (Exception e) {
            paymentDate = null;
        }
        String method = tableModel.getValueAt(selectedRow, 5).toString();

        Payment payment = new Payment(id, customerId, bookingId, amount, paymentDate, method);
        
        return payment;
    }

    public void addViewDetailsListener(ActionListener listener) {
        viewDetailsButton.addActionListener(listener);
    }

    public void addBackListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }

    public JFrame getFrame() {
        return frame;
    }
}

