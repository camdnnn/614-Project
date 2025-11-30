package com.flightreservation.data.repository;

<<<<<<< Updated upstream
import com.flightreservation.data.config.DBConnection;
=======
import com.flightreservation.config.DBConnection;
>>>>>>> Stashed changes
import com.flightreservation.model.Flight;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

<<<<<<< Updated upstream
public class FlightRepository extends Repository<Flight> {

    public void create(Flight data) {
        String sql = "INSERT INTO flights (id, airline, origin, destination, departure, arrival, price, seats_available) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, data.getId());
            stmt.setString(2, data.getAirline());
            stmt.setString(3, data.getOrigin());
            stmt.setString(4, data.getDestination());
            stmt.setDate(5, new Date(data.getDeparture().getTime()));
            stmt.setDate(6, new Date(data.getArrival().getTime()));
            stmt.setDouble(7, data.getPrice());
            stmt.setInt(8, data.getAvailableSeats());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
=======
public class FlightRepository implements Repository<Flight> {

    @Override
    public void create(Flight f) {
        String sql = "INSERT INTO Flights "
                + "(airline, origin, destination, departure, arrival, price, seats_available) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, f.getAirline());
            stmt.setString(2, f.getOrigin());
            stmt.setString(3, f.getDestination());
            stmt.setString(4, f.getDeparture());
            stmt.setString(5, f.getArrival());
            stmt.setDouble(6, f.getPrice());
            stmt.setInt(7, f.getAvailableSeats());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalStateException("Failed to create flight", ex);
>>>>>>> Stashed changes
        }
    }

    public Flight read(int id) {
<<<<<<< Updated upstream
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
            stmt.setDate(4, new Date(data.getDeparture().getTime()));
            stmt.setDate(5, new Date(data.getArrival().getTime()));
            stmt.setDouble(6, data.getPrice());
            stmt.setInt(7, data.getAvailableSeats());
            stmt.setInt(8, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
=======
        String sql = "SELECT * FROM Flights WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            return rs.next() ? map(rs) : null;
        } catch (SQLException ex) {
            throw new IllegalStateException("Failed to read flight " + id, ex);
        }
    }

    @Override
    public void update(int id, Flight f) {
        String sql = "UPDATE Flights SET "
                + "airline=?, origin=?, destination=?, "
                + "departure=?, arrival=?, price=?, seats_available=? "
                + "WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, f.getAirline());
            stmt.setString(2, f.getOrigin());
            stmt.setString(3, f.getDestination());
            stmt.setString(4, f.getDeparture());
            stmt.setString(5, f.getArrival());
            stmt.setDouble(6, f.getPrice());
            stmt.setInt(7, f.getAvailableSeats());
            stmt.setInt(8, id);

            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalStateException("Failed to update flight " + id, ex);
>>>>>>> Stashed changes
        }
    }

    public void delete(int id) {
<<<<<<< Updated upstream
        String sql = "DELETE FROM flights WHERE id = ?";

        try (Connection conn = DBConnection.getInstance();
=======
        String sql = "DELETE FROM Flights WHERE id=?";

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
            throw new IllegalStateException("Failed to delete flight " + id, ex);
>>>>>>> Stashed changes
        }
    }

    public List<Flight> getAll() {
<<<<<<< Updated upstream
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
            rs.getDate("departure"),
            rs.getDate("arrival"),
            rs.getFloat("price"),
            rs.getInt("seats_available")
        );
        return f;
    }
=======
        List<Flight> flights = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Flights")) {

            while (rs.next()) flights.add(map(rs));
        } catch (SQLException ex) {
            throw new IllegalStateException("Failed to list flights", ex);
        }

        return flights;
    }

    private Flight map(ResultSet rs) throws SQLException {
        return new Flight(
            rs.getInt("id"),
            rs.getString("airline"),
            rs.getString("origin"),
            rs.getString("destination"),
            rs.getString("departure"),
            rs.getString("arrival"),
            rs.getFloat("price"),
            rs.getInt("seats_available")
        );
    }
>>>>>>> Stashed changes
}
