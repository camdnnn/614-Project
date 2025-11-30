package com.flightreservation.data.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static Connection connection;
    private static Properties props;

    private DBConnection() { }

    public static Connection getInstance() throws SQLException {
        if (connection == null || connection.isClosed()) {
            loadProperties();
            createConnection();
        }
        return connection;
    }

    private static synchronized void loadProperties() throws SQLException {
        if (props != null) return;

        props = new Properties();
        try (FileInputStream fis = new FileInputStream("db.properties")) {
            props.load(fis);
        } catch (IOException e) {
            throw new SQLException("Unable to load db.properties file", e);
        }
    }

    private static synchronized void createConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            return;
        }

        String url = props.getProperty("DB_URL");
        String user = props.getProperty("DB_USER");
        String password = props.getProperty("DB_PASSWORD");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        }
    }
}
