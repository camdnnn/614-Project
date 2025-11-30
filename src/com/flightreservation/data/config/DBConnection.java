package com.flightreservation.data.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        Path primary = Paths.get("db.properties");
        Path fallback = Paths.get("db.properties.example");
        Path toLoad = Files.exists(primary) ? primary : fallback;

        if (!Files.exists(toLoad)) {
            throw new SQLException("Unable to load database config: db.properties (or db.properties.example) file not found");
        }

        try (FileInputStream fis = new FileInputStream(toLoad.toFile())) {
            props.load(fis);
        } catch (IOException e) {
            throw new SQLException("Unable to load database config from " + toLoad, e);
        }
    }

    private static synchronized void createConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            return;
        }

        String url = props.getProperty("DB_URL");
        String user = getFirstNonBlank("DB_USERNAME", "DB_USER");
        String password = getFirstNonBlank("DB_PASSWORD", "DB_PASS");

        if (url == null || url.isBlank()) {
            throw new SQLException("DB_URL is not configured in db.properties");
        }
        if (user == null || user.isBlank()) {
            throw new SQLException("DB_USERNAME/DB_USER is not configured in db.properties");
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        }
    }

    private static String getFirstNonBlank(String primaryKey, String fallbackKey) {
        String value = props.getProperty(primaryKey);
        if (value == null || value.isBlank()) {
            value = props.getProperty(fallbackKey);
        }
        return value;
    }
}
