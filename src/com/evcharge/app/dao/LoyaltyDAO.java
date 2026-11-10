package com.evcharge.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.evcharge.app.DBConnection;

public class LoyaltyDAO {

    public int recordTransaction(int driverId, Integer paymentId, int points, String type) {
        String sql = "INSERT INTO ev_charging_app.loyalty_transactions "
                   + "(driver_id, payment_id, points, transaction_type, created_at) "
                   + "VALUES (?, ?, ?, ?, NOW())";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, driverId);
            if (paymentId != null && paymentId > 0) {
                stmt.setInt(2, paymentId);
            } else {
                stmt.setNull(2, java.sql.Types.INTEGER);
            }
            stmt.setInt(3, points);
            stmt.setString(4, type != null ? type : "EARNED");

            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error recording loyalty transaction: " + e.getMessage());
        }
        return -1;
    }
}
