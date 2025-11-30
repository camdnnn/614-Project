// package main.java.repositories;

// import main.config.DBConnection;
// import main.java.models.Payment;
package com.flightreservation.data.repository;
<<<<<<< Updated upstream

import com.flightreservation.data.config.DBConnection;
import com.flightreservation.model.Payment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentRepository extends Repository<Payment> {

    public void create(Payment data) {
        String sql = "INSERT INTO payments (customer_id, booking_id, amount, payment_date, method) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, data.getCustomerId());
            stmt.setInt(2, data.getBookingId());
            stmt.setFloat(3, data.getAmount());
            stmt.setDate(4, new Date(data.getPaymentDate().getTime()));
            stmt.setString(5, data.getMethod());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
=======
import com.flightreservation.config.DBConnection;
import com.flightreservation.model.Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class PaymentRepository implements Repository<Payment> {

    @Override
    public void create(Payment p) {
        String sql = """
                INSERT INTO Payments
                (customer_id, booking_id, amount, payment_date, method)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, p.getCustomerId());
            stmt.setInt(2, p.getBookingId());
            stmt.setDouble(3, p.getAmount());
            stmt.setTimestamp(4, Timestamp.valueOf(p.getPaymentDate()));
            stmt.setString(5, p.getMethod());

            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalStateException("Failed to create payment", ex);
>>>>>>> Stashed changes
        }
    }

    public Payment read(int id) {
<<<<<<< Updated upstream
        String sql = "SELECT * FROM payments WHERE id = ?";
        Payment payment = null;

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                payment = mapRow(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return payment;
    }

    public void update(int id, Payment data) {
        String sql = "UPDATE payments SET customer_id = ?, booking_id = ?, amount = ?, payment_date = ?, method = ? WHERE id = ?";

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, data.getCustomerId());
            stmt.setInt(2, data.getBookingId());
            stmt.setFloat(3, data.getAmount());
            stmt.setDate(4, new Date(data.getPaymentDate().getTime()));
            stmt.setString(5, data.getMethod());
            stmt.setInt(6, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
=======
        String sql = "SELECT * FROM Payments WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            return rs.next() ? map(rs) : null;
        } catch (SQLException ex) {
            throw new IllegalStateException("Failed to read payment " + id, ex);
        }
    }

    @Override
    public void update(int id, Payment p) {
        String sql = """
                UPDATE Payments SET
                customer_id=?, booking_id=?, amount=?, payment_date=?, method=?
                WHERE id=?
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, p.getCustomerId());
            stmt.setInt(2, p.getBookingId());
            stmt.setDouble(3, p.getAmount());
            stmt.setTimestamp(4, Timestamp.valueOf(p.getPaymentDate()));
            stmt.setString(5, p.getMethod());
            stmt.setInt(6, id);

            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalStateException("Failed to update payment " + id, ex);
>>>>>>> Stashed changes
        }
    }

    public void delete(int id) {
<<<<<<< Updated upstream
        String sql = "DELETE FROM payments WHERE id = ?";

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
=======
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM Payments WHERE id=?")) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalStateException("Failed to delete payment " + id, ex);
>>>>>>> Stashed changes
        }
    }

    public List<Payment> getAll() {
<<<<<<< Updated upstream
        String sql = "SELECT * FROM payments";
        List<Payment> list = new ArrayList<>();

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Payment payment = mapRow(rs);
                list.add(payment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
=======
        List<Payment> list = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Payments")) {

            while (rs.next()) list.add(map(rs));
        } catch (SQLException ex) {
            throw new IllegalStateException("Failed to list payments", ex);
>>>>>>> Stashed changes
        }

        return list;
    }

<<<<<<< Updated upstream
    protected Payment mapRow(ResultSet rs) throws SQLException {
        Payment p = new Payment(
            rs.getInt("id"),
            rs.getInt("customer_id"),
            rs.getInt("booking_id"),
            rs.getFloat("amount"),
            rs.getDate("payment_date"),
            rs.getString("method")
        );
        return p;
=======
    private Payment map(ResultSet rs) throws SQLException {
        return new Payment(
            rs.getInt("id"),
            rs.getInt("customer_id"),
            rs.getInt("booking_id"),
            rs.getDouble("amount"),
            rs.getTimestamp("payment_date").toLocalDateTime(),
            rs.getString("method")
        );
>>>>>>> Stashed changes
    }
}
