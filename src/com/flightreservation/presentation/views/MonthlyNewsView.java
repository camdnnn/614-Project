package com.flightreservation.presentation.views;

import com.flightreservation.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

public class MonthlyNewsView {
    private JFrame frame;
    private JPanel newsPanel;
    private JTextArea newsContentArea;
    private JLabel titleLabel;
    private JLabel dateLabel;
    private JButton backButton;
    private JButton refreshButton;

    public MonthlyNewsView() {
        initializeComponents();
        layoutComponents();
    }

    private void initializeComponents() {
        frame = new JFrame("Monthly News");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        newsPanel = new JPanel(new BorderLayout(10, 10));
        newsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        titleLabel = new JLabel("Monthly News");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        dateLabel = new JLabel("Date: ");
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        dateLabel.setHorizontalAlignment(SwingConstants.CENTER);

        newsContentArea = new JTextArea();
        newsContentArea.setEditable(false);
        newsContentArea.setLineWrap(true);
        newsContentArea.setWrapStyleWord(true);
        newsContentArea.setFont(new Font("Arial", Font.PLAIN, 14));
        newsContentArea.setBackground(Color.WHITE);

        backButton = new JButton("Back");
        refreshButton = new JButton("Refresh");
    }

    private void layoutComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // News panel
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.add(dateLabel, BorderLayout.NORTH);
        
        JScrollPane scrollPane = new JScrollPane(newsContentArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(650, 350));
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        
        newsPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(newsPanel, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(refreshButton);
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

    public void displayNews(MonthlyNews news) {
        if (news == null) {
            clearNews();
            return;
        }

        titleLabel.setText(news.getTitle());
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateLabel.setText("Date: " + dateFormat.format(news.getPublishDate()));
        
        newsContentArea.setText(news.getContent());
        newsContentArea.setCaretPosition(0); // Scroll to top
    }

    public void clearNews() {
        titleLabel.setText("Monthly News");
        dateLabel.setText("Date: ");
        newsContentArea.setText("");
    }

    public void addBackListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }

    public void addRefreshListener(ActionListener listener) {
        refreshButton.addActionListener(listener);
    }

    public JFrame getFrame() {
        return frame;
    }
}

