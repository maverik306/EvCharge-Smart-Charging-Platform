package com.evcharge.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.evcharge.app.DBConnection;
import com.evcharge.app.model.Booking;
import com.evcharge.app.model.ChargingSession;

public class ChargingSessionDAO {

    public int startSession(int bookingId) {
        String sql = "INSERT INTO ev_charging_app.charging_sessions "
                   + "(booking_id, started_at, session_status) "
                   + "VALUES (?, NOW(), 'CHARGING') "
                   + "ON DUPLICATE KEY UPDATE started_at = NOW(), session_status = 'CHARGING', session_id = LAST_INSERT_ID(session_id)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, bookingId);
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error starting charging session: " + e.getMessage());
        }
        return -1;
    }

    public boolean completeSession(int sessionId, double energyUsedKwh, double totalAmount) {
        String sql = "UPDATE ev_charging_app.charging_sessions "
                   + "SET ended_at = NOW(), energy_used_kwh = ?, total_amount = ?, session_status = 'COMPLETED' "
                   + "WHERE session_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, energyUsedKwh);
            stmt.setDouble(2, totalAmount);
            stmt.setInt(3, sessionId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("⚠️ Error completing charging session: " + e.getMessage());
        }
        return false;
    }

    public ChargingSession getSessionById(int sessionId) {
        String sql = "SELECT session_id, booking_id, energy_used_kwh, total_amount, session_status "
                   + "FROM ev_charging_app.charging_sessions WHERE session_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, sessionId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int bId = rs.getInt("booking_id");
                    Booking b = new BookingDAO().getBookingById(bId);
                    double energy = rs.getDouble("energy_used_kwh");
                    double amount = rs.getDouble("total_amount");
                    String status = rs.getString("session_status");

                    return new ChargingSession(
                        rs.getInt("session_id"),
                        "CS-" + rs.getInt("session_id"),
                        b,
                        energy,
                        amount,
                        "COMPLETED".equalsIgnoreCase(status),
                        status
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error fetching session (" + sessionId + "): " + e.getMessage());
        }
        return null;
    }
}
