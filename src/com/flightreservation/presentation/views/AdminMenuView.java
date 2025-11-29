package com.flightreservation.presentation.views;

import com.flightreservation.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdminMenuView {
    private JFrame frame;
    private JButton manageFlightsButton;
    private JButton viewFlightsButton;
    private JButton logoutButton;

    public AdminMenuView() {
        initializeComponents();
        layoutComponents();
    }

    private void initializeComponents() {
        frame = new JFrame("System Administrator Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        manageFlightsButton = new JButton("Manage Flights");
        viewFlightsButton = new JButton("View Flights");
        logoutButton = new JButton("Logout");

        // Set button sizes
        Dimension buttonSize = new Dimension(250, 50);
        manageFlightsButton.setPreferredSize(buttonSize);
        viewFlightsButton.setPreferredSize(buttonSize);
        logoutButton.setPreferredSize(new Dimension(250, 40));
    }

    private void layoutComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("System Administrator Menu");
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
        buttonPanel.add(manageFlightsButton, gbc);

        gbc.gridy = 1;
        buttonPanel.add(viewFlightsButton, gbc);

        gbc.gridy = 2;
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

    public void addManageFlightsListener(ActionListener listener) {
        manageFlightsButton.addActionListener(listener);
    }

    public void addViewFlightsListener(ActionListener listener) {
        viewFlightsButton.addActionListener(listener);
    }

    public void addLogoutListener(ActionListener listener) {
        logoutButton.addActionListener(listener);
    }

    public JFrame getFrame() {
        return frame;
    }
}

