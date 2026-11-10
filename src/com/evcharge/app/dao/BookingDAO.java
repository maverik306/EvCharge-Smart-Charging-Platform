package com.evcharge.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.evcharge.app.DBConnection;
import com.evcharge.app.model.Booking;
import com.evcharge.app.model.Charger;
import com.evcharge.app.model.Driver;
import com.evcharge.app.model.Vehicle;

public class BookingDAO {

    /**
     * Backward-compatible save/update by explicit bookingId.
     */
    public boolean saveBooking(int bookingId, int driverId, int chargerId, String mode, String status) {
        String sql = "INSERT INTO ev_charging_app.bookings "
                + "(booking_id, driver_id, charger_id, mode, booking_status, requested_at) "
                + "VALUES (?, ?, ?, ?, ?, NOW()) "
                + "ON DUPLICATE KEY UPDATE booking_status = VALUES(booking_status), mode = VALUES(mode)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bookingId);
            stmt.setInt(2, driverId);
            stmt.setInt(3, chargerId);
            stmt.setString(4, mode);
            stmt.setString(5, status);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Booking " + bookingId + " saved/updated in MySQL database!");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Could not save booking (" + bookingId + "): " + e.getMessage());
        }
        return false;
    }

    /**
     * Insert a new booking record with full schema columns and return auto-generated booking_id.
     */
    public int createBooking(int driverId, int vehicleId, int chargerId, String mode, String status, int batteryPct) {
        String sql = "INSERT INTO ev_charging_app.bookings "
                   + "(driver_id, vehicle_id, charger_id, request_type, battery_percentage_at_request, booking_status, requested_at, mode) "
                   + "VALUES (?, ?, ?, ?, ?, ?, NOW(), ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, driverId);
            stmt.setInt(2, vehicleId > 0 ? vehicleId : 1);
            stmt.setInt(3, chargerId);
            stmt.setString(4, mode.equalsIgnoreCase("EMERGENCY") ? "EMERGENCY" : "NORMAL");
            stmt.setInt(5, batteryPct);
            stmt.setString(6, status != null ? status : "PENDING");
            stmt.setString(7, mode);

            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error creating booking: " + e.getMessage());
        }
        return -1;
    }

    /**
     * Fetch a Booking domain model by booking_id.
     */
    public Booking getBookingById(int bookingId) {
        String sql = "SELECT booking_id, driver_id, vehicle_id, charger_id, mode, booking_status "
                   + "FROM ev_charging_app.bookings WHERE booking_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bookingId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int dId = rs.getInt("driver_id");
                    int vId = rs.getInt("vehicle_id");
                    int cId = rs.getInt("charger_id");

                    Driver driver = new DriverDAO().getDriverById(dId);
                    Vehicle vehicle = new VehicleDAO().getVehicleById(vId);
                    Charger charger = new ChargerDAO().getChargerById(cId);

                    return new Booking(
                        rs.getInt("booking_id"),
                        "BK-" + rs.getInt("booking_id"),
                        driver,
                        vehicle,
                        charger,
                        rs.getString("mode"),
                        rs.getString("booking_status")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error fetching booking (" + bookingId + "): " + e.getMessage());
        }
        return null;
    }

    /**
     * Update booking status in MySQL.
     */
    public boolean updateBookingStatus(int bookingId, String status) {
        String sql = "UPDATE ev_charging_app.bookings SET booking_status = ? WHERE booking_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setInt(2, bookingId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("⚠️ Error updating booking status: " + e.getMessage());
        }
        return false;
    }
}
