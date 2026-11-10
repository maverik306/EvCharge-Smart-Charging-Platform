package com.evcharge.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.evcharge.app.DBConnection;
import com.evcharge.app.model.Driver;

public class DriverDAO {

    /**
     * Fetch a Driver domain object from MySQL by driverId.
     */
    public Driver getDriverById(int driverId) {
        String sql = "SELECT driver_id, name, phone_number, email, loyalty_points "
                   + "FROM ev_charging_app.drivers WHERE driver_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, driverId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Driver(
                        rs.getInt("driver_id"),
                        rs.getString("name"),
                        rs.getString("phone_number"),
                        rs.getString("email"),
                        rs.getInt("loyalty_points")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error fetching driver (" + driverId + "): " + e.getMessage());
        }
        return null;
    }

    /**
     * Fetch a Driver domain object from MySQL by email.
     */
    public Driver getDriverByEmail(String email) {
        String sql = "SELECT driver_id, name, phone_number, email, loyalty_points "
                   + "FROM ev_charging_app.drivers WHERE LOWER(email) = LOWER(?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email.trim());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Driver(
                        rs.getInt("driver_id"),
                        rs.getString("name"),
                        rs.getString("phone_number"),
                        rs.getString("email"),
                        rs.getInt("loyalty_points")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error fetching driver by email (" + email + "): " + e.getMessage());
        }
        return null;
    }

    /**
     * Insert a new driver or update if phone/email already exists.
     * Returns the driver_id.
     */
    public int saveDriver(String name, String phone, String email) {
        String sql = "INSERT INTO ev_charging_app.drivers (name, phone_number, email, loyalty_points) "
                   + "VALUES (?, ?, ?, 0) "
                   + "ON DUPLICATE KEY UPDATE name = VALUES(name), driver_id = LAST_INSERT_ID(driver_id)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, name);
            stmt.setString(2, phone);
            stmt.setString(3, email);

            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error saving driver: " + e.getMessage());
        }
        return -1;
    }

    /**
     * Update driver's total loyalty points.
     */
    public boolean updateLoyaltyPoints(int driverId, int points) {
        String sql = "UPDATE ev_charging_app.drivers SET loyalty_points = ? WHERE driver_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, points);
            stmt.setInt(2, driverId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("⚠️ Error updating loyalty points for driver (" + driverId + "): " + e.getMessage());
        }
        return false;
    }

    /**
     * Backward-compatible console output method.
     */
    public void printDriverDetails(int driverId) {
        Driver d = getDriverById(driverId);
        if (d != null) {
            System.out.println("--- Driver Info from MySQL ---");
            System.out.println("ID: " + d.getDriverId());
            System.out.println("Name: " + d.getName());
            System.out.println("Email: " + d.getEmail());
            System.out.println("Loyalty Points: " + d.getLoyaltyPoints());
        } else {
            System.out.println("Driver with ID " + driverId + " not found.");
        }
    }
}
