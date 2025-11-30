package com.flightreservation.data.repository;

<<<<<<< Updated upstream
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
            stmt.setDate(4, new Date(data.getBookingDate().getTime()));
            stmt.setString(5, data.getStatus());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
=======
// import main.config.DBConnection;
// import main.java.models.Book;
import com.flightreservation.model.Booking;
import com.flightreservation.config.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC repository for the Bookings table.
 */
public class BookingRepository implements Repository<Booking> {

    @Override
    public void create(Booking b) {
        String sql = "INSERT INTO Bookings " +
                     "(customer_id, flight_id, seat_number, booking_date, status) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, b.getCustomerId());
            stmt.setInt(2, b.getFlightId());
            stmt.setString(3, b.getSeatNumber());

            // LocalDateTime -> Timestamp for DATETIME column
            Timestamp date = b.getBookingDate() != null ? Timestamp.valueOf(b.getBookingDate()) : null;
            stmt.setTimestamp(4, date);

            stmt.setString(5, b.getStatus());

            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalStateException("Failed to create booking", ex);
>>>>>>> Stashed changes
        }
    }

    public Booking read(int id) {
<<<<<<< Updated upstream
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
            stmt.setDate(4, new Date(data.getBookingDate().getTime()));
            stmt.setString(5, data.getStatus());
            stmt.setInt(6, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
=======
        String sql = "SELECT * FROM Bookings WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
                return null;
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Failed to read booking " + id, ex);
        }
    }

    @Override
    public void update(int id, Booking b) {
        String sql = "UPDATE Bookings SET " +
                     "customer_id = ?, " +
                     "flight_id = ?, " +
                     "seat_number = ?, " +
                     "booking_date = ?, " +
                     "status = ? " +
                     "WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, b.getCustomerId());
            stmt.setInt(2, b.getFlightId());
            stmt.setString(3, b.getSeatNumber());

            Timestamp date = b.getBookingDate() != null ? Timestamp.valueOf(b.getBookingDate()) : null;
            stmt.setTimestamp(4, date);

            stmt.setString(5, b.getStatus());
            stmt.setInt(6, id);

            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalStateException("Failed to update booking " + id, ex);
>>>>>>> Stashed changes
        }
    }

    public void delete(int id) {
<<<<<<< Updated upstream
        String sql = "DELETE FROM bookings WHERE id = ?";

        try (Connection conn = DBConnection.getInstance();
=======
        String sql = "DELETE FROM Bookings WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
>>>>>>> Stashed changes
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
<<<<<<< Updated upstream

        } catch (SQLException e) {
            e.printStackTrace();
=======
        } catch (SQLException ex) {
            throw new IllegalStateException("Failed to delete booking " + id, ex);
>>>>>>> Stashed changes
        }
    }

    public List<Booking> getAll() {
<<<<<<< Updated upstream
        String sql = "SELECT * FROM bookings";
        List<Booking> list = new ArrayList<>();

        try (Connection conn = DBConnection.getInstance();
=======
        String sql = "SELECT * FROM Bookings";
        List<Booking> list = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
>>>>>>> Stashed changes
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
<<<<<<< Updated upstream
                list.add(mapRow(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
=======
                list.add(map(rs));
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Failed to list bookings", ex);
>>>>>>> Stashed changes
        }

        return list;
    }

<<<<<<< Updated upstream
    protected Booking mapRow(ResultSet rs) throws SQLException {
        Booking b = new Booking(
            rs.getInt("id"),
            rs.getInt("customer_id"),
            rs.getInt("flight_id"),
            rs.getString("seat_number"),
            rs.getDate("booking_date"),
            rs.getString("status")
        );
        return b;
=======
    // Helper to map a ResultSet row to a Booking object
    private Booking map(ResultSet rs) throws SQLException {
        Timestamp ts = rs.getTimestamp("booking_date");
        return new Booking(
                rs.getInt("id"),
                rs.getInt("customer_id"),
                rs.getInt("flight_id"),
                rs.getString("seat_number"),
                ts != null ? ts.toLocalDateTime() : null,
                rs.getString("status")
        );
>>>>>>> Stashed changes
    }
}
