package com.flightreservation.presentation.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PaymentView {
    private JFrame frame;
    private JLabel amountLabel;
    private JLabel statusLabel;
    private JTextField cardNumberField;
    private JTextField cardHolderField;
    private JTextField expiryDateField;
    private JPasswordField cvvField;
    private JButton payButton;
    private JButton cancelButton;

    public PaymentView() {
        initializeComponents();
        layoutComponents();
    }

    private void initializeComponents() {
        frame = new JFrame("Payment");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        amountLabel = new JLabel("Amount: $0.00");
        amountLabel.setFont(new Font("Arial", Font.BOLD, 16));
        amountLabel.setHorizontalAlignment(SwingConstants.CENTER);

        statusLabel = new JLabel(" ");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setForeground(Color.GREEN);

        cardNumberField = new JTextField(20);
        cardHolderField = new JTextField(20);
        expiryDateField = new JTextField(10);
        cvvField = new JPasswordField(5);

        payButton = new JButton("Pay");
        cancelButton = new JButton("Cancel");
    }

    private void layoutComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("Payment");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Amount label
        JPanel amountPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        amountPanel.add(amountLabel);
        mainPanel.add(amountPanel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Card Number
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Card Number:"), gbc);
        gbc.gridx = 1;
        formPanel.add(cardNumberField, gbc);

        // Card Holder
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Card Holder Name:"), gbc);
        gbc.gridx = 1;
        formPanel.add(cardHolderField, gbc);

        // Expiry Date
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Expiry Date (MM/YY):"), gbc);
        gbc.gridx = 1;
        formPanel.add(expiryDateField, gbc);

        // CVV
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("CVV:"), gbc);
        gbc.gridx = 1;
        formPanel.add(cvvField, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Status label
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        statusPanel.add(statusLabel);
        mainPanel.add(statusPanel, BorderLayout.EAST);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(payButton);
        buttonPanel.add(cancelButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
    }

    public void display() {
        frame.setVisible(true);
    }

    public void dispose() {
        frame.dispose();
    }

    public void displayAmount(double amount) {
        amountLabel.setText("Amount: $" + String.format("%.2f", amount));
    }

    public String getCardNumber() {
        return cardNumberField.getText().trim();
    }

    public String getCardHolder() {
        return cardHolderField.getText().trim();
    }

    public String getExpiryDate() {
        return expiryDateField.getText().trim();
    }

    public String getCvv() {
        return new String(cvvField.getPassword());
    }

    public void showSuccess() {
        statusLabel.setText("Payment successful!");
        statusLabel.setForeground(Color.GREEN);
    }

    public void showError(String message) {
        statusLabel.setText(message);
        statusLabel.setForeground(Color.RED);
    }

    public void clearFields() {
        cardNumberField.setText("");
        cardHolderField.setText("");
        expiryDateField.setText("");
        cvvField.setText("");
        statusLabel.setText(" ");
    }

    public void addPayListener(ActionListener listener) {
        payButton.addActionListener(listener);
    }

    public void addCancelListener(ActionListener listener) {
        cancelButton.addActionListener(listener);
    }

    public JFrame getFrame() {
        return frame;
    }
}

