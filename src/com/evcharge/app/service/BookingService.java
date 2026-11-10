package com.evcharge.app.service;

import com.evcharge.app.dao.BookingDAO;
import com.evcharge.app.dao.ChargerDAO;
import com.evcharge.app.dao.ChargingSessionDAO;
import com.evcharge.app.model.Booking;
import com.evcharge.app.model.Charger;
import com.evcharge.app.model.ChargingSession;
import com.evcharge.app.model.Driver;
import com.evcharge.app.model.Vehicle;

public class BookingService {

    private final BookingDAO bookingDAO;
    private final ChargerDAO chargerDAO;
    private final ChargingSessionDAO sessionDAO;

    public BookingService() {
        this.bookingDAO = new BookingDAO();
        this.chargerDAO = new ChargerDAO();
        this.sessionDAO = new ChargingSessionDAO();
    }

    public BookingService(BookingDAO bookingDAO, ChargerDAO chargerDAO, ChargingSessionDAO sessionDAO) {
        this.bookingDAO = bookingDAO;
        this.chargerDAO = chargerDAO;
        this.sessionDAO = sessionDAO;
    }

    /**
     * Validates and persists a booking.
     * Checks:
     * 1. Nullability of driver, vehicle, charger
     * 2. Connector compatibility
     * 3. Charger availability
     */
    public boolean createBooking(Booking booking) {
        if (booking == null) {
            System.err.println("❌ Booking validation failed: Booking cannot be null.");
            return false;
        }

        Driver driver = booking.getDriver();
        Vehicle vehicle = booking.getVehicle();
        Charger charger = booking.getCharger();

        if (driver == null) {
            System.err.println("❌ Booking validation failed: Driver is required.");
            return false;
        }

        if (vehicle == null) {
            System.err.println("❌ Booking validation failed: Vehicle is required.");
            return false;
        }

        if (charger == null) {
            System.err.println("❌ Booking validation failed: Charger is required.");
            return false;
        }

        // 1. Compatibility Check
        if (!charger.isCompatibleWith(vehicle)) {
            System.err.println("❌ Incompatible charger connector! Vehicle requires: "
                    + vehicle.getConnectorType() + ", but charger provides: " + charger.getConnectorType());
            return false;
        }

        // 2. Availability Check
        if (!charger.isAvailable()) {
            System.err.println("❌ Charger " + charger.getChargerId() + " is currently occupied or unavailable.");
            return false;
        }

        // 3. Persist to MySQL database
        int driverId = driver.getDriverId() > 0 ? driver.getDriverId() : 1;
        int vehicleId = vehicle.getVehicleId() > 0 ? vehicle.getVehicleId() : 1;
        int chargerId = charger.getId() > 0 ? charger.getId() : 1;
        int batteryPct = vehicle.getCurrentBatteryPercentage();
        String mode = booking.getChargingMode() != null ? booking.getChargingMode() : "NORMAL";

        int generatedId = bookingDAO.createBooking(driverId, vehicleId, chargerId, mode, "PENDING", batteryPct);
        if (generatedId > 0) {
            booking.setId(generatedId);
            System.out.println("✅ Booking created successfully with DB ID: " + generatedId);
            return true;
        }

        return false;
    }

    /**
     * Confirms a booking and locks charger availability.
     */
    public boolean confirmBooking(Booking booking) {
        if (booking == null) return false;

        booking.confirmBooking();

        if (booking.getId() > 0) {
            bookingDAO.updateBookingStatus(booking.getId(), "CONFIRMED");
        }

        if (booking.getCharger() != null) {
            booking.getCharger().setAvailable(false);
            if (booking.getCharger().getId() > 0) {
                chargerDAO.updateAvailability(booking.getCharger().getId(), false);
            }
        }

        return true;
    }

    /**
     * Starts the charging session and persists session record to MySQL.
     */
    public ChargingSession startCharging(Booking booking) {
        if (booking == null) return null;

        booking.startCharging();

        if (booking.getId() > 0) {
            bookingDAO.updateBookingStatus(booking.getId(), "CHARGING");
            int sessionId = sessionDAO.startSession(booking.getId());
            return new ChargingSession(sessionId, "CS-" + sessionId, booking, 0.0, 0.0, false, "CHARGING");
        }

        return new ChargingSession("CS-TMP", booking);
    }

    /**
     * Completes the booking and releases the charger.
     */
    public boolean completeBooking(Booking booking) {
        if (booking == null) return false;

        booking.completeBooking();

        if (booking.getId() > 0) {
            bookingDAO.updateBookingStatus(booking.getId(), "COMPLETED");
        }

        if (booking.getCharger() != null) {
            booking.getCharger().setAvailable(true);
            if (booking.getCharger().getId() > 0) {
                chargerDAO.updateAvailability(booking.getCharger().getId(), true);
            }
        }

        return true;
    }

    /**
     * Cancels a booking and frees the charger.
     */
    public boolean cancelBooking(Booking booking) {
        if (booking == null) return false;

        booking.setStatus("CANCELLED");

        if (booking.getId() > 0) {
            bookingDAO.updateBookingStatus(booking.getId(), "CANCELLED");
        }

        if (booking.getCharger() != null) {
            booking.getCharger().setAvailable(true);
            if (booking.getCharger().getId() > 0) {
                chargerDAO.updateAvailability(booking.getCharger().getId(), true);
            }
        }

        return true;
    }

    /**
     * Dispatches the highest priority booking from the priority queue.
     */
    public Booking dispatchNext(BookingQueue queue) {
        if (queue == null || queue.getQueueSize() == 0) {
            return null;
        }

        Booking next = queue.getNextBooking();
        if (next != null) {
            confirmBooking(next);
        }
        return next;
    }
}
