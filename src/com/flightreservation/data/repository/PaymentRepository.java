// package main.java.repositories;

// import main.config.DBConnection;
// import main.java.models.Payment;
package com.flightreservation.data.repository;

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
            stmt.setFloat(3, (float) data.getAmount());
            stmt.setDate(4, new Date(data.getPaymentDate().getTime()));
            stmt.setString(5, data.getMethod());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Payment read(int id) {
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
            stmt.setFloat(3, (float) data.getAmount());
            stmt.setDate(4, new Date(data.getPaymentDate().getTime()));
            stmt.setString(5, data.getMethod());
            stmt.setInt(6, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM payments WHERE id = ?";

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Payment> getAll() {
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
        }

        return list;
    }

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
    }
}
