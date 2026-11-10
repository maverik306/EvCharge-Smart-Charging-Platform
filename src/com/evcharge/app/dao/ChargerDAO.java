package com.evcharge.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.evcharge.app.DBConnection;
import com.evcharge.app.model.Charger;
import com.evcharge.app.model.ChargingLocation;

public class ChargerDAO {

    public Charger getChargerById(int chargerId) {
        String sql = "SELECT charger_id, location_id, charger_code, charging_mode, connector_type, price_per_kwh, available "
                   + "FROM ev_charging_app.chargers WHERE charger_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, chargerId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Charger(
                        rs.getInt("charger_id"),
                        rs.getInt("location_id"),
                        rs.getString("charger_code"),
                        rs.getString("charging_mode"),
                        rs.getString("connector_type"),
                        rs.getDouble("price_per_kwh"),
                        rs.getBoolean("available")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error fetching charger (" + chargerId + "): " + e.getMessage());
        }
        return null;
    }

    public List<Charger> getAllChargers() {
        List<Charger> list = new ArrayList<>();
        String sql = "SELECT charger_id, location_id, charger_code, charging_mode, connector_type, price_per_kwh, available "
                   + "FROM ev_charging_app.chargers ORDER BY charger_id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(new Charger(
                    rs.getInt("charger_id"),
                    rs.getInt("location_id"),
                    rs.getString("charger_code"),
                    rs.getString("charging_mode"),
                    rs.getString("connector_type"),
                    rs.getDouble("price_per_kwh"),
                    rs.getBoolean("available")
                ));
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error fetching chargers: " + e.getMessage());
        }
        return list;
    }

    public List<ChargingLocation> getAllLocations() {
        List<ChargingLocation> list = new ArrayList<>();
        String sql = "SELECT location_id, name, address, location_type, verified, rating "
                   + "FROM ev_charging_app.charging_locations ORDER BY location_id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(new ChargingLocation(
                    rs.getInt("location_id"),
                    rs.getString("name"),
                    rs.getString("address"),
                    rs.getString("location_type"),
                    rs.getBoolean("verified"),
                    rs.getDouble("rating")
                ));
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error fetching locations: " + e.getMessage());
        }
        return list;
    }

    public int saveLocation(String name, String address, String type, boolean verified, double rating) {
        String sql = "INSERT INTO ev_charging_app.charging_locations (name, address, location_type, verified, rating) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, name);
            stmt.setString(2, address);
            stmt.setString(3, type);
            stmt.setBoolean(4, verified);
            stmt.setDouble(5, rating);

            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error saving location: " + e.getMessage());
        }
        return -1;
    }

    public int saveCharger(int locationId, String code, String mode, String connector, double price, boolean available) {
        String sql = "INSERT INTO ev_charging_app.chargers "
                   + "(location_id, charger_code, charging_mode, connector_type, price_per_kwh, available) "
                   + "VALUES (?, ?, ?, ?, ?, ?) "
                   + "ON DUPLICATE KEY UPDATE charging_mode=VALUES(charging_mode), price_per_kwh=VALUES(price_per_kwh), available=VALUES(available)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, locationId);
            stmt.setString(2, code);
            stmt.setString(3, mode);
            stmt.setString(4, connector);
            stmt.setDouble(5, price);
            stmt.setBoolean(6, available);

            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error saving charger: " + e.getMessage());
        }
        return -1;
    }

    public boolean updateAvailability(int chargerId, boolean available) {
        String sql = "UPDATE ev_charging_app.chargers SET available = ? WHERE charger_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, available);
            stmt.setInt(2, chargerId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("⚠️ Error updating charger availability: " + e.getMessage());
        }
        return false;
    }
}
