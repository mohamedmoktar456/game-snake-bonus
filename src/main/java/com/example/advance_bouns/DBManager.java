package com.example.advance_bouns;

import java.sql.*;

public class DBManager {

    public static void initializeDB() {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:player_data.db")) {
            String sql = "CREATE TABLE IF NOT EXISTS players (name TEXT, score INTEGER)";
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void saveScore(Player player) {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:player_data.db")) {
            String sql = "INSERT INTO players (name, score) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, player.getName());
            pstmt.setInt(2, player.getScore());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
