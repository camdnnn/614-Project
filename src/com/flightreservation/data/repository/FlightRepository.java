package com.flightreservation.data.repository;

import com.flightreservation.data.config.DBConnection;
import com.flightreservation.model.Flight;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FlightRepository extends Repository<Flight> {

    public void create(Flight data) {
        String sql = "INSERT INTO flights (airline, origin, destination, departure, arrival, price, seats_available) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, data.getAirline());
            stmt.setString(2, data.getOrigin());
            stmt.setString(3, data.getDestination());
            stmt.setTimestamp(4, new Timestamp(data.getDeparture().getTime()));
            stmt.setTimestamp(5, new Timestamp(data.getArrival().getTime()));
            stmt.setDouble(6, data.getPrice());
            stmt.setInt(7, data.getAvailableSeats());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Flight read(int id) {
        String sql = "SELECT * FROM flights WHERE id = ?";
        Flight flight = null;

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                flight = mapRow(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flight;
    }

    public void update(int id, Flight data) {
        String sql = "UPDATE flights SET airline = ?, origin = ?, destination = ?, departure = ?, arrival = ?, price = ?, seats_available = ? WHERE id = ?";

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, data.getAirline());
            stmt.setString(2, data.getOrigin());
            stmt.setString(3, data.getDestination());
            stmt.setTimestamp(4, new Timestamp(data.getDeparture().getTime()));
            stmt.setTimestamp(5, new Timestamp(data.getArrival().getTime()));
            stmt.setDouble(6, data.getPrice());
            stmt.setInt(7, data.getAvailableSeats());
            stmt.setInt(8, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM flights WHERE id = ?";

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Flight> getAll() {
        String sql = "SELECT * FROM flights";
        List<Flight> list = new ArrayList<>();

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Flight flight = mapRow(rs);
                list.add(flight);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    
    protected Flight mapRow(ResultSet rs) throws SQLException {
        Flight f = new Flight(
            rs.getInt("id"),
            rs.getString("airline"),
            rs.getString("origin"),
            rs.getString("destination"),
            rs.getTimestamp("departure"),
            rs.getTimestamp("arrival"),
            rs.getFloat("price"),
            rs.getInt("seats_available")
        );
        return f;
    }
}
