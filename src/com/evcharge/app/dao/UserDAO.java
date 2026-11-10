package com.evcharge.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.evcharge.app.DBConnection;
import com.evcharge.app.model.User;

/**
 * Data Access Object for handling user authentication and registration in MySQL.
 */
public class UserDAO {

    public UserDAO() {
        ensureTableExists();
    }

    /**
     * Automatically ensures the app_users table exists in MySQL.
     */
    public void ensureTableExists() {
        String sql = "CREATE TABLE IF NOT EXISTS ev_charging_app.app_users ("
                   + "  user_id INT NOT NULL AUTO_INCREMENT, "
                   + "  full_name VARCHAR(100) NOT NULL, "
                   + "  email VARCHAR(150) NOT NULL, "
                   + "  password_hash VARCHAR(255) NOT NULL, "
                   + "  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, "
                   + "  PRIMARY KEY (user_id), "
                   + "  UNIQUE KEY uq_user_email (email)"
                   + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("⚠️ Warning: Could not verify/create app_users table: " + e.getMessage());
        }
    }

    /**
     * Checks if a user already exists with the given email.
     */
    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM ev_charging_app.app_users WHERE LOWER(email) = LOWER(?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email.trim());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error checking email existence: " + e.getMessage());
        }
        return false;
    }

    /**
     * Fetches a User by email address (includes password_hash for authentication).
     */
    public User getUserByEmail(String email) {
        String sql = "SELECT user_id, full_name, email, password_hash, created_at "
                   + "FROM ev_charging_app.app_users WHERE LOWER(email) = LOWER(?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email.trim());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getInt("user_id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("password_hash"),
                        rs.getTimestamp("created_at")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error fetching user by email: " + e.getMessage());
        }
        return null;
    }

    /**
     * Fetches a User by primary key ID.
     */
    public User getUserById(int userId) {
        String sql = "SELECT user_id, full_name, email, password_hash, created_at "
                   + "FROM ev_charging_app.app_users WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getInt("user_id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("password_hash"),
                        rs.getTimestamp("created_at")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error fetching user by ID: " + e.getMessage());
        }
        return null;
    }

    /**
     * Inserts a new user record into the database using PreparedStatement.
     * Returns the generated user_id, or -1 on failure.
     */
    public int createUser(String fullName, String email, String passwordHash) {
        String sql = "INSERT INTO ev_charging_app.app_users (full_name, email, password_hash) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, fullName.trim());
            stmt.setString(2, email.trim().toLowerCase());
            stmt.setString(3, passwordHash);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error creating user (" + email + "): " + e.getMessage());
        }
        return -1;
    }
}
