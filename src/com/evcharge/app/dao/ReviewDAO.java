package com.evcharge.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.evcharge.app.DBConnection;
import com.evcharge.app.model.Booking;
import com.evcharge.app.model.ChargingLocation;
import com.evcharge.app.model.Driver;
import com.evcharge.app.model.Review;

public class ReviewDAO {

    public boolean submitReview(int driverId, int locationId, int bookingId, int rating, String comment) {
        String sql = "INSERT INTO ev_charging_app.reviews (driver_id, location_id, booking_id, rating, comment, created_at) "
                   + "VALUES (?, ?, ?, ?, ?, NOW()) "
                   + "ON DUPLICATE KEY UPDATE rating = VALUES(rating), comment = VALUES(comment)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, driverId);
            stmt.setInt(2, locationId);
            stmt.setInt(3, bookingId);
            stmt.setInt(4, rating);
            stmt.setString(5, comment);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Review saved/updated in MySQL database!");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Could not save review: " + e.getMessage());
        }
        return false;
    }

    public List<Review> getReviewsByLocation(int locationId) {
        List<Review> list = new ArrayList<>();
        String sql = "SELECT review_id, booking_id, driver_id, location_id, rating, comment "
                   + "FROM ev_charging_app.reviews WHERE location_id = ? ORDER BY review_id DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, locationId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int dId = rs.getInt("driver_id");
                    int bId = rs.getInt("booking_id");
                    Driver driver = new DriverDAO().getDriverById(dId);
                    Booking booking = new BookingDAO().getBookingById(bId);
                    ChargingLocation loc = new ChargerDAO().getAllLocations().stream()
                            .filter(l -> l.getLocationId() == locationId)
                            .findFirst()
                            .orElse(null);

                    list.add(new Review(
                        "REV-" + rs.getInt("review_id"),
                        driver,
                        loc,
                        booking,
                        rs.getInt("rating"),
                        rs.getString("comment")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error fetching reviews for location (" + locationId + "): " + e.getMessage());
        }
        return list;
    }

    public double getAverageRatingForLocation(int locationId) {
        String sql = "SELECT AVG(rating) FROM ev_charging_app.reviews WHERE location_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, locationId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error calculating avg rating: " + e.getMessage());
        }
        return 0.0;
    }
}
