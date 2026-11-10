package com.evcharge.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.evcharge.app.DBConnection;
import com.evcharge.app.model.Vehicle;

public class VehicleDAO {

    public Vehicle getVehicleById(int vehicleId) {
        String sql = "SELECT vehicle_id, driver_id, model, battery_capacity_kwh, connector_type, current_battery_percentage "
                   + "FROM ev_charging_app.vehicles WHERE vehicle_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, vehicleId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Vehicle(
                        rs.getInt("vehicle_id"),
                        rs.getInt("driver_id"),
                        rs.getString("model"),
                        rs.getDouble("battery_capacity_kwh"),
                        rs.getString("connector_type"),
                        rs.getInt("current_battery_percentage")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error fetching vehicle (" + vehicleId + "): " + e.getMessage());
        }
        return null;
    }

    public List<Vehicle> getVehiclesByDriverId(int driverId) {
        List<Vehicle> list = new ArrayList<>();
        String sql = "SELECT vehicle_id, driver_id, model, battery_capacity_kwh, connector_type, current_battery_percentage "
                   + "FROM ev_charging_app.vehicles WHERE driver_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, driverId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new Vehicle(
                        rs.getInt("vehicle_id"),
                        rs.getInt("driver_id"),
                        rs.getString("model"),
                        rs.getDouble("battery_capacity_kwh"),
                        rs.getString("connector_type"),
                        rs.getInt("current_battery_percentage")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error fetching vehicles for driver (" + driverId + "): " + e.getMessage());
        }
        return list;
    }

    public int saveVehicle(int driverId, String model, double capacity, String connector, int batteryPct) {
        String sql = "INSERT INTO ev_charging_app.vehicles "
                   + "(driver_id, model, battery_capacity_kwh, connector_type, current_battery_percentage) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, driverId);
            stmt.setString(2, model);
            stmt.setDouble(3, capacity);
            stmt.setString(4, connector);
            stmt.setInt(5, batteryPct);

            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error saving vehicle: " + e.getMessage());
        }
        return -1;
    }

    public boolean updateBatteryPercentage(int vehicleId, int pct) {
        String sql = "UPDATE ev_charging_app.vehicles SET current_battery_percentage = ? WHERE vehicle_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pct);
            stmt.setInt(2, vehicleId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("⚠️ Error updating battery % for vehicle (" + vehicleId + "): " + e.getMessage());
        }
        return false;
    }
}
