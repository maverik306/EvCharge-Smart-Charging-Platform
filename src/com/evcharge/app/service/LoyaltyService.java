package com.evcharge.app.service;

import com.evcharge.app.dao.DriverDAO;
import com.evcharge.app.dao.LoyaltyDAO;
import com.evcharge.app.model.Driver;
import com.evcharge.app.model.Payment;

public class LoyaltyService {

    private final DriverDAO driverDAO;
    private final LoyaltyDAO loyaltyDAO;

    public LoyaltyService() {
        this.driverDAO = new DriverDAO();
        this.loyaltyDAO = new LoyaltyDAO();
    }

    public LoyaltyService(DriverDAO driverDAO, LoyaltyDAO loyaltyDAO) {
        this.driverDAO = driverDAO;
        this.loyaltyDAO = loyaltyDAO;
    }

    /**
     * Award loyalty points to a driver for a completed payment.
     * Rule: 1 loyalty point for every ₹10 spent.
     */
    public int awardPoints(Driver driver, Payment payment) {
        if (driver == null || payment == null) {
            return 0;
        }

        if (!"PAID".equalsIgnoreCase(payment.getPaymentStatus())) {
            return 0;
        }

        int points = (int) (payment.getAmount() / 10);
        if (points <= 0) {
            return 0;
        }

        driver.addLoyaltyPoints(points);

        // Persist to MySQL if driver exists in DB
        if (driver.getDriverId() > 0) {
            driverDAO.updateLoyaltyPoints(driver.getDriverId(), driver.getLoyaltyPoints());
            loyaltyDAO.recordTransaction(driver.getDriverId(), payment.getId(), points, "EARNED");
        }

        return points;
    }

    /**
     * Redeem loyalty points for discounts.
     */
    public boolean redeemPoints(Driver driver, int pointsToRedeem) {
        if (driver == null || pointsToRedeem <= 0 || driver.getLoyaltyPoints() < pointsToRedeem) {
            return false;
        }

        driver.addLoyaltyPoints(-pointsToRedeem);

        if (driver.getDriverId() > 0) {
            driverDAO.updateLoyaltyPoints(driver.getDriverId(), driver.getLoyaltyPoints());
            loyaltyDAO.recordTransaction(driver.getDriverId(), null, pointsToRedeem, "REDEEMED");
        }

        return true;
    }
}
