package com.flightreservation.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public final class DBConnection {
    private DBConnection() {
    }

    public static Connection getConnection() {
        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");
        if (url == null || url.isBlank()) {
            throw new UnsupportedOperationException("DB_URL not configured");
        }
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            throw new IllegalStateException("Connection failed", ex);
        }
    }
}
