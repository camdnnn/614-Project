package com.flightreservation.data.repository;

<<<<<<< Updated upstream
import com.flightreservation.data.config.DBConnection;
=======
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.flightreservation.config.DBConnection;
>>>>>>> Stashed changes
import com.flightreservation.model.MonthlyNews;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MonthlyNewsRepository extends Repository<MonthlyNews> {

<<<<<<< Updated upstream
    public void create(MonthlyNews data) {
        String sql = "INSERT INTO monthly_news (id, title, content, publish_date) VALUES (?, ?, ?, ?)";

        try (var conn = DBConnection.getInstance();
             var stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, data.getId());
            stmt.setString(2, data.getTitle());
            stmt.setString(3, data.getContent());
            stmt.setDate(4, new Date(data.getPublishDate().getTime()));

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
=======
    private MonthlyNews mapRow(ResultSet rs) throws SQLException {
        return new MonthlyNews(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("content"),
                rs.getString("publish_date"));
    }

    @Override
    public void create(MonthlyNews data) {
        String sql = "INSERT INTO monthly_news (title, content, publish_date) VALUES (?, ?, ?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, data.getTitle());
            ps.setString(2, data.getContent());
            ps.setString(3, data.getPublishDate());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    // no setters; ignore id assignment for minimal change
                }
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Failed to create monthly news", ex);
>>>>>>> Stashed changes
        }
    }

    public MonthlyNews read(int id) {
<<<<<<< Updated upstream
        String sql = "SELECT * FROM monthly_news WHERE id = ?";
        MonthlyNews news = null;

        try (var conn = DBConnection.getInstance();
             var stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                news = mapRow(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return news;
=======
        String sql = "SELECT id, title, content, publish_date FROM monthly_news WHERE id = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapRow(rs) : null;
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Failed to read monthly news " + id, ex);
        }
>>>>>>> Stashed changes
    }

    public void update(int id, MonthlyNews data) {
        String sql = "UPDATE monthly_news SET title = ?, content = ?, publish_date = ? WHERE id = ?";
<<<<<<< Updated upstream

        try (var conn = DBConnection.getInstance();
             var stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, data.getTitle());
            stmt.setString(2, data.getContent());
            stmt.setDate(3, new Date(data.getPublishDate().getTime()));
            stmt.setInt(4, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
=======
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, data.getTitle());
            ps.setString(2, data.getContent());
            ps.setString(3, data.getPublishDate());
            ps.setInt(4, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalStateException("Failed to update monthly news " + id, ex);
>>>>>>> Stashed changes
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM monthly_news WHERE id = ?";
<<<<<<< Updated upstream

        try (var conn = DBConnection.getInstance();
             var stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
=======
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalStateException("Failed to delete monthly news " + id, ex);
>>>>>>> Stashed changes
        }
    }

    public List<MonthlyNews> getAll() {
<<<<<<< Updated upstream
        String sql = "SELECT * FROM monthly_news";
        List<MonthlyNews> list = new ArrayList<>();

        try (var conn = DBConnection.getInstance();
             var stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                MonthlyNews news = mapRow(rs);
                list.add(news);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    protected MonthlyNews mapRow(ResultSet rs) throws SQLException {
        MonthlyNews news = new MonthlyNews(
            rs.getInt("id"), 
            rs.getString("title"),
            rs.getString("content"), 
            rs.getDate("publish_date")
        );
        return news;
=======
        String sql = "SELECT id, title, content, publish_date FROM monthly_news ORDER BY publish_date DESC";
        List<MonthlyNews> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException ex) {
            throw new IllegalStateException("Failed to list monthly news", ex);
        }
        return list;
>>>>>>> Stashed changes
    }
}
