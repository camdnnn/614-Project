package com.flightreservation.presentation.views;

import com.flightreservation.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AgentMenuView {
    private JFrame frame;
    private JButton manageBookingsButton;
    private JButton manageCustomersButton;
    private JButton viewFlightsButton;
    private JButton viewPaymentsButton;
    private JButton logoutButton;

    public AgentMenuView() {
        initializeComponents();
        layoutComponents();
    }

    private void initializeComponents() {
        frame = new JFrame("Flight Agent Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        manageBookingsButton = new JButton("Manage Bookings");
        manageCustomersButton = new JButton("Manage Customers");
        viewFlightsButton = new JButton("View Flights");
        viewPaymentsButton = new JButton("View Payments");
        logoutButton = new JButton("Logout");

        // Set button sizes
        Dimension buttonSize = new Dimension(250, 50);
        manageBookingsButton.setPreferredSize(buttonSize);
        manageCustomersButton.setPreferredSize(buttonSize);
        viewFlightsButton.setPreferredSize(buttonSize);
        viewPaymentsButton.setPreferredSize(buttonSize);
        logoutButton.setPreferredSize(new Dimension(250, 40));
    }

    private void layoutComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("Flight Agent Menu");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(manageBookingsButton, gbc);

        gbc.gridy = 1;
        buttonPanel.add(manageCustomersButton, gbc);

        gbc.gridy = 2;
        buttonPanel.add(viewFlightsButton, gbc);

        gbc.gridy = 3;
        buttonPanel.add(viewPaymentsButton, gbc);

        gbc.gridy = 4;
        gbc.insets = new Insets(30, 10, 10, 10);
        buttonPanel.add(logoutButton, gbc);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
    }

    public void display() {
        frame.setVisible(true);
    }

    public void dispose() {
        frame.dispose();
    }

    public void addManageBookingsListener(ActionListener listener) {
        manageBookingsButton.addActionListener(listener);
    }

    public void addManageCustomersListener(ActionListener listener) {
        manageCustomersButton.addActionListener(listener);
    }

    public void addViewFlightsListener(ActionListener listener) {
        viewFlightsButton.addActionListener(listener);
    }

    public void addViewPaymentsListener(ActionListener listener) {
        viewPaymentsButton.addActionListener(listener);
    }

    public void addLogoutListener(ActionListener listener) {
        logoutButton.addActionListener(listener);
    }

    public JFrame getFrame() {
        return frame;
    }
}

