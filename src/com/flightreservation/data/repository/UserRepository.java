package com.flightreservation.data.repository;

import com.flightreservation.data.config.DBConnection;
import com.flightreservation.model.Role;
import com.flightreservation.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        }
    }

    public User read(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        User user = null;

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

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
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = DBConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAll() {
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
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("email"),
            rs.getString("password"),
            Role.valueOf(rs.getString("role"))
        );
        return u;
    }
    
}