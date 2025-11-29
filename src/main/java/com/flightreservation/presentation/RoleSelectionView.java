package com.flightreservation.presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class RoleSelectionView {
    private JFrame frame;
    private JLabel titleLabel;
    private JButton customerButton;
    private JButton agentButton;
    private JButton adminButton;
    private JButton backButton;

    public RoleSelectionView() {
        initializeComponents();
        layoutComponents();
    }

    private void initializeComponents() {
        frame = new JFrame("Flight Reservation System - Select Role");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 350);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        titleLabel = new JLabel("Select Your Role");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        customerButton = new JButton("Customer");
        customerButton.setPreferredSize(new Dimension(200, 50));
        customerButton.setFont(new Font("Arial", Font.PLAIN, 14));

        agentButton = new JButton("Flight Agent");
        agentButton.setPreferredSize(new Dimension(200, 50));
        agentButton.setFont(new Font("Arial", Font.PLAIN, 14));

        adminButton = new JButton("System Administrator");
        adminButton.setPreferredSize(new Dimension(200, 50));
        adminButton.setFont(new Font("Arial", Font.PLAIN, 14));

        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(200, 40));
    }

    private void layoutComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title at top
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(customerButton, gbc);

        gbc.gridy = 1;
        buttonPanel.add(agentButton, gbc);

        gbc.gridy = 2;
        buttonPanel.add(adminButton, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(30, 10, 10, 10);
        buttonPanel.add(backButton, gbc);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
    }

    public void display() {
        frame.setVisible(true);
    }

    public void dispose() {
        frame.dispose();
    }

    public void addCustomerListener(ActionListener listener) {
        customerButton.addActionListener(listener);
    }

    public void addAgentListener(ActionListener listener) {
        agentButton.addActionListener(listener);
    }

    public void addAdminListener(ActionListener listener) {
        adminButton.addActionListener(listener);
    }

    public void addBackListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }

    public JFrame getFrame() {
        return frame;
    }
}

