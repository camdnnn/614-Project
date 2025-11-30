package com.flightreservation.data.repository;

import com.flightreservation.data.config.DBConnection;
import com.flightreservation.model.MonthlyNews;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MonthlyNewsRepository extends Repository<MonthlyNews> {

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
        }
    }

    public MonthlyNews read(int id) {
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
    }

    public void update(int id, MonthlyNews data) {
        String sql = "UPDATE monthly_news SET title = ?, content = ?, publish_date = ? WHERE id = ?";

        try (var conn = DBConnection.getInstance();
             var stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, data.getTitle());
            stmt.setString(2, data.getContent());
            stmt.setDate(3, new Date(data.getPublishDate().getTime()));
            stmt.setInt(4, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM monthly_news WHERE id = ?";

        try (var conn = DBConnection.getInstance();
             var stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<MonthlyNews> getAll() {
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
    }
    
}