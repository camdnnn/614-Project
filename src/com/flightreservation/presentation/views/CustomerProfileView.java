package com.flightreservation.presentation.views;

import com.flightreservation.model.*;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class CustomerProfileView {
    private JFrame frame;
    private JTextField searchField;
    private JButton searchButton;
    private JTable customersTable;
    private DefaultTableModel tableModel;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField addressField;
    private JButton addButton;
    private JButton updateButton;
    private JButton clearButton;
    private JButton backButton;

    public CustomerProfileView() {
        initializeComponents();
        layoutComponents();
    }

    private void initializeComponents() {
        frame = new JFrame("Customer Profile Management");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null);

        // Search components
        searchField = new JTextField(20);
        searchButton = new JButton("Search");

        // Table model
        String[] columnNames = {"ID", "Name", "Email", "Phone", "Address", "Role"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        customersTable = new JTable(tableModel);
        customersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        customersTable.setRowHeight(25);
        
        // Add selection listener to populate form fields
        customersTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                populateFormFromSelectedRow();
            }
        });

        // Form fields
        nameField = new JTextField(25);
        emailField = new JTextField(25);
        phoneField = new JTextField(25);
        addressField = new JTextField(25);

        // Buttons
        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        clearButton = new JButton("Clear");
        backButton = new JButton("Back");
    }

    private void layoutComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("Customer Profile Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        mainPanel.add(searchPanel, BorderLayout.NORTH);

        // Split pane for table and form
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerLocation(300);
        splitPane.setResizeWeight(0.5);

        // Table panel
        JScrollPane tableScrollPane = new JScrollPane(customersTable);
        splitPane.setTopComponent(tableScrollPane);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        formPanel.add(emailField, gbc);

        // Phone
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        formPanel.add(phoneField, gbc);

        // Address
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Address:"), gbc);
        gbc.gridx = 1;
        formPanel.add(addressField, gbc);

        splitPane.setBottomComponent(formPanel);

        mainPanel.add(splitPane, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(backButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
    }

    private void populateFormFromSelectedRow() {
        int selectedRow = customersTable.getSelectedRow();
        if (selectedRow != -1) {
            nameField.setText(tableModel.getValueAt(selectedRow, 1).toString());
            emailField.setText(tableModel.getValueAt(selectedRow, 2).toString());
            phoneField.setText(tableModel.getValueAt(selectedRow, 3).toString());
            addressField.setText(tableModel.getValueAt(selectedRow, 4).toString());
        }
    }

    public void display() {
        frame.setVisible(true);
    }

    public void dispose() {
        frame.dispose();
    }

    public void displayCustomers(List<User> customers) {
        tableModel.setRowCount(0);
        
        if (customers == null || customers.isEmpty()) {
            return;
        }
        
        for (User customer : customers) {
            Object[] row = {
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                "", // Phone - not in User model, would need to extend
                "", // Address - not in User model, would need to extend
                customer.getRole().toString()
            };
            tableModel.addRow(row);
        }
    }

    public void displayCustomerDetails(User customer) {
        nameField.setText(customer.getName());
        emailField.setText(customer.getEmail());
        phoneField.setText(""); // Would need to extend User model
        addressField.setText(""); // Would need to extend User model
    }

    public String getSearchQuery() {
        return searchField.getText().trim();
    }

    public String getCustomerName() {
        return nameField.getText().trim();
    }

    public String getCustomerEmail() {
        return emailField.getText().trim();
    }

    public String getCustomerPhone() {
        return phoneField.getText().trim();
    }

    public String getCustomerAddress() {
        return addressField.getText().trim();
    }

    public User getSelectedCustomer() {
        int selectedRow = customersTable.getSelectedRow();
        if (selectedRow == -1) {
            return null;
        }
        
        int id = (Integer) tableModel.getValueAt(selectedRow, 0);
        String name = (String) tableModel.getValueAt(selectedRow, 1);
        String email = (String) tableModel.getValueAt(selectedRow, 2);
        
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        
        return user;
    }

    public void clearFields() {
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        addressField.setText("");
        searchField.setText("");
        customersTable.clearSelection();
    }

    public void addSearchListener(ActionListener listener) {
        searchButton.addActionListener(listener);
    }

    public void addAddListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }

    public void addUpdateListener(ActionListener listener) {
        updateButton.addActionListener(listener);
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

