package com.evcharge.app;

import java.sql.Connection;

import com.evcharge.app.dao.BookingDAO;
import com.evcharge.app.dao.DriverDAO;
import com.evcharge.app.dao.ReviewDAO;
import com.evcharge.app.model.Booking;
import com.evcharge.app.model.Charger;
import com.evcharge.app.model.ChargingLocation;
import com.evcharge.app.model.ChargingSession;
import com.evcharge.app.model.Driver;
import com.evcharge.app.model.Payment;
import com.evcharge.app.model.Review;
import com.evcharge.app.model.Vehicle;
import com.evcharge.app.service.BookingQueue;
import com.evcharge.app.service.LoyaltyService;

public class Main {

    public static void main(String[] args) {

        Driver driver = new Driver(
                "Ananya Sharma",
                "9876543210",
                "ananya@email.com"
        );

        driver.addLoyaltyPoints(50);

        System.out.println("Driver: " + driver.getName());
        System.out.println("Email: " + driver.getEmail());
        System.out.println("Loyalty points: " + driver.getLoyaltyPoints());

        Vehicle vehicle = new Vehicle(
                "Tata Nexon EV",
                40.5,
                "CCS2",
                18
        );

        System.out.println("Vehicle: " + vehicle.getModel());
        System.out.println("Battery: "
                + vehicle.getCurrentBatteryPercentage() + "%");
        System.out.println("Connector: " + vehicle.getConnectorType());

        ChargingLocation location = new ChargingLocation(
                "GreenCharge Cafe",
                "MG Road, Bengaluru",
                true,
                4.6
        );

        System.out.println("Location: " + location.getName());
        System.out.println("Verified: " + location.isVerified());
        System.out.println("Rating: " + location.getRating());
        Charger charger = new Charger(
                "GC-01",
                "Fast",
                "CCS2",
                18.50,
                true
        );

        if (charger.isCompatibleWith(vehicle) && charger.isAvailable()) {
            System.out.println("Compatible charger is available.");
        } else {
            System.out.println("This charger cannot be booked.");
        }
        
        Booking booking = new Booking(
                "BK-001",
                driver,
                vehicle,
                charger,
                "EMERGENCY"
        );

        System.out.println("Booking ID: " + booking.getBookingId());
        System.out.println("Driver: " + booking.getDriver().getName());
        System.out.println("Vehicle: " + booking.getVehicle().getModel());
        System.out.println("Charger: " + booking.getCharger().getChargerId());
        System.out.println("Mode: " + booking.getChargingMode());
        System.out.println("Status: " + booking.getStatus());
        Driver secondDriver = new Driver(
                "Rahul Kumar",
                "9123456789",
                "rahul@email.com"
        );

        Vehicle secondVehicle = new Vehicle(
                "MG ZS EV",
                50.3,
                "CCS2",
                8
        );

        Booking normalBooking = new Booking(
                "BK-002",
                secondDriver,
                secondVehicle,
                charger,
                "NORMAL"
        );

        BookingQueue bookingQueue = new BookingQueue();

        bookingQueue.addBooking(normalBooking);
        bookingQueue.addBooking(booking);

        Booking nextBooking = bookingQueue.getNextBooking();

        System.out.println("Next booking: "
                + nextBooking.getBookingId()
                + " - "
                + nextBooking.getChargingMode());
        booking.confirmBooking();
        System.out.println("After confirmation: " + booking.getStatus());

        booking.startCharging();
        System.out.println("When charging starts: " + booking.getStatus());

        ChargingSession session = new ChargingSession("CS-001", booking);

        session.completeSession(18.4);

        System.out.println("Energy used: "
                + session.getEnergyUsedKwh() + " kWh");
        System.out.println("Total bill: ₹"
                + session.getTotalAmount());
        System.out.println("After charging ends: "
                + booking.getStatus());
        Payment payment = new Payment("PAY-001", session);

        payment.makePayment("UPI");

        System.out.println("Payment method: "
                + payment.getPaymentMethod());
        System.out.println("Amount paid: ₹"
                + payment.getAmount());
        System.out.println("Payment status: "
                + payment.getPaymentStatus());
        LoyaltyService loyaltyService = new LoyaltyService();

        int earnedPoints = loyaltyService.awardPoints(driver, payment);

        System.out.println("Points earned: " + earnedPoints);
        System.out.println("Driver total points: "
                + driver.getLoyaltyPoints());
        Review review = new Review(
                "REV-001",
                driver,
                location,
                booking,
                5,
                "Fast charging and clean rest area."
        );

        if (review.isValidReview()) {
            System.out.println("Review accepted: "
                    + review.getRating() + " stars");
            System.out.println("Comment: "
                    + review.getComment());
        } else {
            System.out.println("Review cannot be submitted.");
        }
     // Call database operations method
        runDatabaseOperations();
    } // <-- Close main() method here

    // Database operations method placed outside of main()
    private static void runDatabaseOperations() {
        System.out.println("\n=== MYSQL DATABASE OPERATIONS ===");

        try (Connection conn = DBConnection.getConnection()) {
            if (conn != null) {
                System.out.println("[SUCCESS] MySQL Database connected successfully!");
            }
        } catch (Exception e) {
            System.err.println("[ERROR] Database connection failed:");
            e.printStackTrace();
        }

        // 1. Fetch Driver details from MySQL
        DriverDAO driverDAO = new DriverDAO();
        driverDAO.printDriverDetails(1);

        // 2. Insert/Update a valid Booking record
     // 2. Insert/Update a valid Booking record using ID = 1
        BookingDAO bookingDAO = new BookingDAO();
        bookingDAO.saveBooking(1, 1, 1, "EMERGENCY", "COMPLETED");

        // 3. Insert/Update a Review linked directly to Booking ID = 1
        ReviewDAO reviewDAO = new ReviewDAO();
        reviewDAO.submitReview(1, 1, 1, 5, "Fast charging and clean rest area.");
    }
}
