package com.evcharge.app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.evcharge.app.model.*;
import com.evcharge.app.service.*;

public class AppTestSuite {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("⚡ EV CHARGING APP - AUTOMATED TEST SUITE ⚡");
        System.out.println("=================================================\n");

        testPriorityQueue();
        testConnectorCompatibility();
        testLoyaltyPointCalculation();
        testBookingLifecycleTransitions();
        testReviewBusinessRules();
        testDatabaseConnectivity();
        testAuthValidationRules();
        testBCryptPasswordSecurity();
        testUserRegistrationAndLoginLifecycle();

        System.out.println("\n-------------------------------------------------");
        System.out.println("Test Results: " + passed + " PASSED, " + failed + " FAILED");
        System.out.println("=================================================");

        if (failed > 0) {
            System.exit(1);
        }
    }

    private static void assertTrue(String testName, boolean condition) {
        if (condition) {
            System.out.println("  [PASS] " + testName);
            passed++;
        } else {
            System.err.println("  [FAIL] " + testName);
            failed++;
        }
    }

    private static void assertEquals(String testName, Object expected, Object actual) {
        boolean match = (expected == null && actual == null) || (expected != null && expected.equals(actual));
        if (match) {
            System.out.println("  [PASS] " + testName);
            passed++;
        } else {
            System.err.println("  [FAIL] " + testName + " (Expected: " + expected + ", Actual: " + actual + ")");
            failed++;
        }
    }

    // ── 1. Priority Queue Tests ───────────────────────────────────────────
    private static void testPriorityQueue() {
        System.out.println("1. Priority Queue Logic (EMERGENCY jumps first, then lowest battery)");
        BookingQueue queue = new BookingQueue();

        Driver d = new Driver("Test Driver", "0000000000", "test@test.com");
        Charger c = new Charger("GC-01", "Fast", "CCS2", 18.5, true);

        Vehicle normalHighBat = new Vehicle("Car A", 40.0, "CCS2", 80);
        Vehicle normalLowBat  = new Vehicle("Car B", 40.0, "CCS2", 15);
        Vehicle emergencyCar  = new Vehicle("Car C", 40.0, "CCS2", 45);

        Booking b1 = new Booking("BK-NORM-80", d, normalHighBat, c, "NORMAL");
        Booking b2 = new Booking("BK-NORM-15", d, normalLowBat,  c, "NORMAL");
        Booking b3 = new Booking("BK-EMERG",   d, emergencyCar,  c, "EMERGENCY");

        queue.addBooking(b1);
        queue.addBooking(b2);
        queue.addBooking(b3);

        assertEquals("Queue size should be 3", 3, queue.getQueueSize());

        Booking first = queue.getNextBooking();
        assertEquals("1st dispatched must be EMERGENCY", "BK-EMERG", first.getBookingId());

        Booking second = queue.getNextBooking();
        assertEquals("2nd dispatched must be lowest battery (15%)", "BK-NORM-15", second.getBookingId());

        Booking third = queue.getNextBooking();
        assertEquals("3rd dispatched must be remaining vehicle (80%)", "BK-NORM-80", third.getBookingId());
    }

    // ── 2. Connector Compatibility Tests ──────────────────────────────────
    private static void testConnectorCompatibility() {
        System.out.println("\n2. Charger & Vehicle Connector Compatibility");

        Charger ccs2Charger = new Charger("C-1", "Fast", "CCS2", 18.5, true);
        Charger type2Charger = new Charger("C-2", "Slow", "Type 2", 12.0, true);

        Vehicle ccs2Vehicle = new Vehicle("Nexon", 40.5, "CCS2", 20);
        Vehicle type2Vehicle = new Vehicle("Kona", 39.2, "type 2", 30); // lowercase test

        assertTrue("CCS2 Vehicle matches CCS2 Charger", ccs2Charger.isCompatibleWith(ccs2Vehicle));
        assertTrue("CCS2 Vehicle does NOT match Type 2 Charger", !type2Charger.isCompatibleWith(ccs2Vehicle));
        assertTrue("Type 2 (case-insensitive) matches Type 2 Charger", type2Charger.isCompatibleWith(type2Vehicle));
    }

    // ── 3. Loyalty Program Tests ──────────────────────────────────────────
    private static void testLoyaltyPointCalculation() {
        System.out.println("\n3. Loyalty Points Calculation (1 pt per ₹10 spent)");
        LoyaltyService service = new LoyaltyService();

        Driver driver = new Driver("Loyalty User", "9999999999", "loyalty@test.com");
        Booking booking = new Booking("BK-LOYAL", driver, null, null, "NORMAL");
        ChargingSession session = new ChargingSession("CS-LOYAL", booking);
        session.completeSession(25.0); // total will be based on dummy rate

        Payment paidPayment = new Payment("PAY-1", session);
        paidPayment.makePayment("UPI");

        int initialPoints = driver.getLoyaltyPoints();
        int awarded = service.awardPoints(driver, paidPayment);

        assertTrue("Awarded points should match amount / 10", awarded == (int)(paidPayment.getAmount() / 10));
        assertEquals("Driver total points incremented", initialPoints + awarded, driver.getLoyaltyPoints());

        // Unpaid payment check
        Payment unpaid = new Payment("PAY-2", session);
        int zeroPoints = service.awardPoints(driver, unpaid);
        assertEquals("Unpaid payment awards 0 points", 0, zeroPoints);
    }

    // ── 4. Booking Lifecycle Tests ────────────────────────────────────────
    private static void testBookingLifecycleTransitions() {
        System.out.println("\n4. Booking Status State Machine");
        Booking b = new Booking("BK-STATE", null, null, null, "NORMAL");

        assertEquals("Initial status must be PENDING", "PENDING", b.getStatus());

        b.confirmBooking();
        assertEquals("After confirm must be CONFIRMED", "CONFIRMED", b.getStatus());

        b.startCharging();
        assertEquals("After start charging must be CHARGING", "CHARGING", b.getStatus());

        b.completeBooking();
        assertEquals("After completion must be COMPLETED", "COMPLETED", b.getStatus());
    }

    // ── 5. Review Business Rules ──────────────────────────────────────────
    private static void testReviewBusinessRules() {
        System.out.println("\n5. Customer Review Validation Rules");
        Driver d = new Driver("Reviewer", "8888888888", "rev@test.com");
        ChargingLocation loc = new ChargingLocation("Central Hub", "Main St", true, 4.5);

        Booking pendingBooking = new Booking("BK-REV-1", d, null, null, "NORMAL");
        Review reviewPending = new Review("REV-1", d, loc, pendingBooking, 5, "Great!");
        assertTrue("Review on PENDING booking must be invalid", !reviewPending.isValidReview());

        pendingBooking.completeBooking();
        Review reviewCompletedValid = new Review("REV-2", d, loc, pendingBooking, 5, "Excellent service!");
        assertTrue("Review on COMPLETED booking with 5 stars must be valid", reviewCompletedValid.isValidReview());

        Review reviewInvalidRatingHigh = new Review("REV-3", d, loc, pendingBooking, 6, "Out of bounds!");
        assertTrue("Rating 6 must be invalid", !reviewInvalidRatingHigh.isValidReview());

        Review reviewInvalidRatingLow = new Review("REV-4", d, loc, pendingBooking, 0, "Out of bounds!");
        assertTrue("Rating 0 must be invalid", !reviewInvalidRatingLow.isValidReview());
    }

    // ── 6. Database Connectivity Tests ────────────────────────────────────
    private static void testDatabaseConnectivity() {
        System.out.println("\n6. MySQL Live Database Verification");
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            assertTrue("MySQL Connection is open and active", conn != null && !conn.isClosed());

            try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM ev_charging_app.drivers")) {
                assertTrue("Able to query drivers table", rs.next());
                int count = rs.getInt(1);
                System.out.println("     ℹ Current drivers in database: " + count);
            }

            try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM ev_charging_app.chargers")) {
                assertTrue("Able to query chargers table", rs.next());
                int count = rs.getInt(1);
                System.out.println("     ℹ Current chargers in database: " + count);
            }

        } catch (Exception e) {
            assertTrue("Database check threw exception: " + e.getMessage(), false);
        }
    }

    // ── 7. Authentication Validation Rules Tests ──────────────────────────
    private static void testAuthValidationRules() {
        System.out.println("\n7. Authentication Input Validation Rules");
        AuthService authService = new AuthService();

        // Email validation
        assertTrue("Empty email is invalid", !authService.isValidEmail(""));
        assertTrue("Null email is invalid", !authService.isValidEmail(null));
        assertTrue("Email missing @ is invalid", !authService.isValidEmail("testexample.com"));
        assertTrue("Email missing domain extension is invalid", !authService.isValidEmail("test@example"));
        assertTrue("Valid email is accepted", authService.isValidEmail("user@evcharge.com"));

        // Password security rules
        assertTrue("Empty password is invalid", authService.validatePassword("") != null);
        assertTrue("Short password (<6 chars) is invalid", authService.validatePassword("abc1") != null);
        assertTrue("Password without digits is invalid", authService.validatePassword("abcdef") != null);
        assertTrue("Password without letters is invalid", authService.validatePassword("123456") != null);
        assertTrue("Valid password with letters and digits is accepted", authService.validatePassword("Secret123") == null);

        // Registration form field checks
        assertTrue("Missing full name is rejected",
            authService.validateRegistrationInputs("", "user@test.com", "Secret123", "Secret123") != null);
        assertTrue("Password mismatch is rejected",
            authService.validateRegistrationInputs("John Doe", "user@test.com", "Secret123", "Wrong456") != null);
        assertTrue("All valid registration fields accepted",
            authService.validateRegistrationInputs("John Doe", "user@test.com", "Secret123", "Secret123") == null);
    }

    // ── 8. Password Hashing & Security Tests ──────────────────────────────
    private static void testBCryptPasswordSecurity() {
        System.out.println("\n8. BCrypt Password Hashing & Secure Verification");
        String plain = "ChargeSafe2026!";
        String hash1 = org.mindrot.jbcrypt.BCrypt.hashpw(plain, org.mindrot.jbcrypt.BCrypt.gensalt(12));
        String hash2 = org.mindrot.jbcrypt.BCrypt.hashpw(plain, org.mindrot.jbcrypt.BCrypt.gensalt(12));

        assertTrue("BCrypt hash is non-empty", hash1 != null && !hash1.isEmpty());
        assertTrue("BCrypt never equals plain text", !plain.equals(hash1));
        assertTrue("BCrypt generates unique salts for identical inputs", !hash1.equals(hash2));
        assertTrue("BCrypt verifies correct plain text against hash",
            org.mindrot.jbcrypt.BCrypt.checkpw(plain, hash1));
        assertTrue("BCrypt rejects incorrect password against hash",
            !org.mindrot.jbcrypt.BCrypt.checkpw("WrongPassword", hash1));
    }

    // ── 9. User Registration & Login End-to-End Tests ─────────────────────
    private static void testUserRegistrationAndLoginLifecycle() {
        System.out.println("\n9. MySQL User Registration & Login Lifecycle");
        AuthService authService = new AuthService();
        com.evcharge.app.dao.UserDAO userDAO = new com.evcharge.app.dao.UserDAO();

        String uniqueEmail = "tester_" + System.currentTimeMillis() + "@evcharge.org";
        String plainPassword = "TestPassword123";

        // 1. Register new user
        AuthService.AuthResult regResult = authService.register(
            "EV Test Driver",
            uniqueEmail,
            plainPassword,
            plainPassword
        );
        assertTrue("User registration in MySQL succeeds", regResult.isSuccess());
        assertTrue("User ID is assigned (>0)", regResult.getUser() != null && regResult.getUser().getUserId() > 0);

        // 2. Prevent duplicate registration
        AuthService.AuthResult dupResult = authService.register(
            "Duplicate Driver",
            uniqueEmail,
            plainPassword,
            plainPassword
        );
        assertTrue("Duplicate registration with existing email is rejected", !dupResult.isSuccess());

        // 3. Login with wrong password
        AuthService.AuthResult wrongPwdResult = authService.authenticate(uniqueEmail, "IncorrectPwd999");
        assertTrue("Login with wrong password fails", !wrongPwdResult.isSuccess());

        // 4. Login with non-existent email
        AuthService.AuthResult nonExistentResult = authService.authenticate("nonexistent_9999@evcharge.org", plainPassword);
        assertTrue("Login with non-existent email fails with account message", !nonExistentResult.isSuccess());

        // 5. Successful login
        AuthService.AuthResult loginResult = authService.authenticate(uniqueEmail, plainPassword);
        assertTrue("Login with correct credentials succeeds", loginResult.isSuccess());
        assertEquals("Authenticated user email matches", uniqueEmail.toLowerCase(), loginResult.getUser().getEmail().toLowerCase());
        assertEquals("Authenticated user name matches", "EV Test Driver", loginResult.getUser().getFullName());
    }
}
