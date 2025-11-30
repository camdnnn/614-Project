package com.flightreservation.data.repository;

import com.flightreservation.data.config.DBConnection;
import com.flightreservation.model.Booking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingRepository extends Repository<Booking> {

    public void create(Booking data) {
        String sql = "INSERT INTO bookings (customer_id, flight_id, seat_number, booking_date, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, data.getCustomerId());
            stmt.setInt(2, data.getFlightId());
            stmt.setString(3, data.getSeatNumber());
            stmt.setString(4, data.getBookingDate());
            stmt.setString(5, data.getStatus());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Booking read(int id) {
        String sql = "SELECT * FROM bookings WHERE id = ?";
        Booking booking = null;

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                booking = mapRow(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return booking;
    }

    public void update(int id, Booking data) {
        String sql = "UPDATE bookings SET customer_id = ?, flight_id = ?, seat_number = ?, booking_date = ?, status = ? WHERE id = ?";

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, data.getCustomerId());
            stmt.setInt(2, data.getFlightId());
            stmt.setString(3, data.getSeatNumber());
            stmt.setString(4, data.getBookingDate());
            stmt.setString(5, data.getStatus());
            stmt.setInt(6, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM bookings WHERE id = ?";

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Booking> getAll() {
        String sql = "SELECT * FROM bookings";
        List<Booking> list = new ArrayList<>();

        try (Connection conn = DBConnection.getInstance();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    protected Booking mapRow(ResultSet rs) throws SQLException {
        Booking b = new Booking(
            rs.getInt("id"),
            rs.getInt("customer_id"),
            rs.getInt("flight_id"),
            rs.getString("seat_number"),
            rs.getString("booking_date"),
            rs.getString("status")
        );
        return b;
    }
}
