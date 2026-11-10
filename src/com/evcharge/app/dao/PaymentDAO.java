package com.evcharge.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.evcharge.app.DBConnection;
import com.evcharge.app.model.ChargingSession;
import com.evcharge.app.model.Payment;

public class PaymentDAO {

    public int recordPayment(int sessionId, String paymentMethod, double amount, String paymentStatus) {
        String sql = "INSERT INTO ev_charging_app.payments "
                   + "(session_id, payment_method, amount, payment_status, paid_at) "
                   + "VALUES (?, ?, ?, ?, NOW()) "
                   + "ON DUPLICATE KEY UPDATE payment_method=VALUES(payment_method), amount=VALUES(amount), payment_status=VALUES(payment_status), paid_at=NOW(), payment_id=LAST_INSERT_ID(payment_id)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, sessionId);
            stmt.setString(2, paymentMethod);
            stmt.setDouble(3, amount);
            stmt.setString(4, paymentStatus != null ? paymentStatus : "PAID");

            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error recording payment: " + e.getMessage());
        }
        return -1;
    }

    public Payment getPaymentBySessionId(int sessionId) {
        String sql = "SELECT payment_id, session_id, payment_method, amount, payment_status "
                   + "FROM ev_charging_app.payments WHERE session_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, sessionId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ChargingSession session = new ChargingSessionDAO().getSessionById(sessionId);
                    return new Payment(
                        rs.getInt("payment_id"),
                        "PAY-" + rs.getInt("payment_id"),
                        session,
                        rs.getString("payment_method"),
                        rs.getString("payment_status"),
                        rs.getDouble("amount")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error fetching payment for session (" + sessionId + "): " + e.getMessage());
        }
        return null;
    }
}
