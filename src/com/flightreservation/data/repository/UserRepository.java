package com.flightreservation.data.repository;

<<<<<<< Updated upstream
import com.flightreservation.data.config.DBConnection;
=======
import com.flightreservation.config.DBConnection;
>>>>>>> Stashed changes
import com.flightreservation.model.Role;
import com.flightreservation.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

<<<<<<< Updated upstream
public class UserRepository extends Repository<User> {

    public void create(User data) {
        String sql = "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, data.getName());
            stmt.setString(2, data.getEmail());
            stmt.setString(3, data.getPassword());
            stmt.setString(4, data.getRole().name());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
=======
public class UserRepository implements Repository<User> {

    @Override
    public void create(User user) {
        String sql = "INSERT INTO Users (name, email, password, role) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getRole().name().toLowerCase());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalStateException("Failed to create user", ex);
>>>>>>> Stashed changes
        }
    }

    public User read(int id) {
<<<<<<< Updated upstream
        String sql = "SELECT * FROM users WHERE id = ?";
        User user = null;

        try (Connection conn = DBConnection.getInstance();
=======
        String sql = "SELECT * FROM Users WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
>>>>>>> Stashed changes
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
<<<<<<< Updated upstream

            if (rs.next()) {
                user = mapRow(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public void update(int id, User data) {
        String sql = "UPDATE users SET name = ?, email = ?, password = ?, role = ? WHERE id = ?";

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, data.getName());
            stmt.setString(2, data.getEmail());
            stmt.setString(3, data.getPassword());
            stmt.setString(4, data.getRole().name());
            stmt.setInt(5, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
=======
            return rs.next() ? map(rs) : null;
        } catch (SQLException ex) {
            throw new IllegalStateException("Failed to read user " + id, ex);
        }
    }

    @Override
    public void update(int id, User user) {
        String sql = "UPDATE Users SET name=?, email=?, password=?, role=? WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getRole().name().toLowerCase());
            stmt.setInt(5, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalStateException("Failed to update user " + id, ex);
>>>>>>> Stashed changes
        }
    }

    public void delete(int id) {
<<<<<<< Updated upstream
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
=======
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM Users WHERE id=?")) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalStateException("Failed to delete user " + id, ex);
>>>>>>> Stashed changes
        }
    }

    public List<User> getAll() {
<<<<<<< Updated upstream
        String sql = "SELECT * FROM users";
        List<User> users = new ArrayList<>();

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(mapRow(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    protected User mapRow(ResultSet rs) throws SQLException {
        User u = new User(
=======
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM Users";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) users.add(map(rs));
        } catch (SQLException ex) {
            throw new IllegalStateException("Failed to list users", ex);
        }
        return users;
    }

    private User map(ResultSet rs) throws SQLException {
        return new User(
>>>>>>> Stashed changes
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("email"),
            rs.getString("password"),
<<<<<<< Updated upstream
            Role.valueOf(rs.getString("role"))
        );
        return u;
=======
            Role.valueOf(rs.getString("role").toUpperCase())
        );
>>>>>>> Stashed changes
    }
}
