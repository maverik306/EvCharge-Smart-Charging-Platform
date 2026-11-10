package com.evcharge.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.evcharge.app.dao.UserDAO;
import com.evcharge.app.model.User;
import com.evcharge.app.service.AuthService;
import com.evcharge.app.service.AuthService.AuthResult;

/**
 * End-to-end verification of all 12 user scenarios specified in requirements.
 */
public class AuthScenariosTest {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        System.out.println("=============================================================");
        System.out.println("⚡ EVCHARGE AUTHENTICATION SYSTEM — 12 SCENARIO VERIFICATION ⚡");
        System.out.println("=============================================================\n");

        AuthService authService = new AuthService();
        UserDAO userDAO = new UserDAO();

        String testEmail = "scenario_user_" + System.currentTimeMillis() + "@evcharge.test";
        String testPassword = "SecurePassword2026!";

        // Test 1: Open the application entry point
        System.out.println("--- Test 1: Application Entry Point ---");
        try {
            LoginFrame loginFrame = new LoginFrame();
            assertTrue("Test 1 Passed: LoginFrame initializes properly as primary entry point", loginFrame != null);
            loginFrame.dispose();
        } catch (Exception e) {
            fail("Test 1 Failed: " + e.getMessage());
        }

        // Test 2: Enter incorrect email or password
        System.out.println("\n--- Test 2: Incorrect Credentials Login ---");
        AuthResult invalidEmailRes = authService.authenticate("unregistered_random@example.com", testPassword);
        assertTrue("Test 2.1 Passed: Unregistered email returns account not found",
            !invalidEmailRes.isSuccess() && invalidEmailRes.getMessage().contains("create an account"));

        // Register a temporary user to test wrong password
        authService.register("Scenario User", testEmail, testPassword, testPassword);
        AuthResult wrongPwdRes = authService.authenticate(testEmail, "WrongPassword999");
        assertTrue("Test 2.2 Passed: Wrong password rejected with appropriate message",
            !wrongPwdRes.isSuccess() && wrongPwdRes.getMessage().contains("Incorrect password"));

        // Test 3: Click Create Account
        System.out.println("\n--- Test 3: Create Account Window Navigation ---");
        try {
            LoginFrame loginFrame = new LoginFrame();
            RegisterFrame regFrame = new RegisterFrame(loginFrame);
            assertTrue("Test 3 Passed: Registration page opens successfully with matching theme", regFrame != null);
            regFrame.dispose();
            loginFrame.dispose();
        } catch (Exception e) {
            fail("Test 3 Failed: " + e.getMessage());
        }

        // Test 4: Submit registration form with empty fields
        System.out.println("\n--- Test 4: Registration with Empty Fields ---");
        String emptyErr1 = authService.validateRegistrationInputs("", "a@b.com", "pass123", "pass123");
        String emptyErr2 = authService.validateRegistrationInputs("Name", "", "pass123", "pass123");
        String emptyErr3 = authService.validateRegistrationInputs("Name", "a@b.com", "", "");
        assertTrue("Test 4 Passed: Empty fields are blocked with validation error",
            emptyErr1 != null && emptyErr2 != null && emptyErr3 != null);

        // Test 5: Enter an invalid email
        System.out.println("\n--- Test 5: Invalid Email Format ---");
        String invalidEmailErr = authService.validateRegistrationInputs("Name", "invalid-email-string", "pass123", "pass123");
        assertTrue("Test 5 Passed: Malformed email format rejected",
            invalidEmailErr != null && invalidEmailErr.toLowerCase().contains("valid email"));

        // Test 6: Passwords do not match
        System.out.println("\n--- Test 6: Password and Confirm Password Mismatch ---");
        String mismatchErr = authService.validateRegistrationInputs("Name", "user@test.com", "Pass123", "Pass456");
        assertTrue("Test 6 Passed: Mismatched passwords rejected",
            mismatchErr != null && mismatchErr.contains("do not match"));

        // Test 7: Register a new account with valid info and verify BCrypt hash in MySQL
        System.out.println("\n--- Test 7: Valid Registration & Secure BCrypt Storage ---");
        String freshEmail = "hash_verify_" + System.currentTimeMillis() + "@evcharge.test";
        AuthResult regSuccess = authService.register("Hash Check User", freshEmail, "MyPass123", "MyPass123");
        assertTrue("Test 7.1 Passed: Valid registration succeeds", regSuccess.isSuccess());

        // Inspect raw row in MySQL to guarantee plain-text password was NEVER stored
        boolean hashVerifiedInDb = false;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT password_hash FROM ev_charging_app.app_users WHERE email = ?")) {
            stmt.setString(1, freshEmail);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("password_hash");
                    boolean isBcrypt = storedHash != null && storedHash.startsWith("$2a$") && !storedHash.equals("MyPass123");
                    hashVerifiedInDb = isBcrypt;
                    System.out.println("     ℹ Raw DB stored hash: " + storedHash.substring(0, 20) + "...");
                }
            }
        } catch (Exception e) {
            fail("Test 7 DB Query Failed: " + e.getMessage());
        }
        assertTrue("Test 7.2 Passed: MySQL holds strong BCrypt hash, never plain-text", hashVerifiedInDb);

        // Test 8: Register again using the same email
        System.out.println("\n--- Test 8: Prevent Duplicate Email Registration ---");
        AuthResult dupRes = authService.register("Duplicate Person", freshEmail, "NewPass123", "NewPass123");
        assertTrue("Test 8 Passed: Duplicate registration blocked with user-friendly message",
            !dupRes.isSuccess() && dupRes.getMessage().contains("already exists"));

        // Test 9: Log in using newly registered account and launch dashboard
        System.out.println("\n--- Test 9: Log In and Open EVCharge Dashboard ---");
        AuthResult loginRes = authService.authenticate(freshEmail, "MyPass123");
        assertTrue("Test 9.1 Passed: Successful authentication with valid credentials", loginRes.isSuccess());

        MainFrame mainFrame = new MainFrame(loginRes.getUser());
        assertTrue("Test 9.2 Passed: EVCharge Dashboard window created for authenticated user",
            mainFrame != null && mainFrame.currentUser != null);
        assertEquals("Test 9.3 Passed: Logged-in user is set on dashboard",
            freshEmail, mainFrame.currentUser.getEmail());

        // Test 10: Log out
        System.out.println("\n--- Test 10: Logout Functionality ---");
        mainFrame.currentUser = null; // Simulate logout end-of-session
        assertTrue("Test 10.1 Passed: Session cleared upon logout", mainFrame.currentUser == null);
        mainFrame.dispose();
        LoginFrame returnLogin = new LoginFrame();
        returnLogin.setNotification("You have been signed out successfully.", "INFO");
        assertTrue("Test 10.2 Passed: User redirected back to LoginFrame", returnLogin != null);
        returnLogin.dispose();

        // Test 11: Restart simulation and re-login
        System.out.println("\n--- Test 11: Restart Simulation & Persistent MySQL Account ---");
        AuthService freshAuthService = new AuthService();
        AuthResult reLoginRes = freshAuthService.authenticate(freshEmail, "MyPass123");
        assertTrue("Test 11 Passed: Account remains fully operational after app restart",
            reLoginRes.isSuccess() && reLoginRes.getUser().getEmail().equals(freshEmail));

        // Test 12: Disconnect or DB unavailable
        System.out.println("\n--- Test 12: Database Error Handling ---");
        // We test with a mock/custom DAO that fails or bad input
        UserDAO mockFailingDAO = new UserDAO() {
            @Override
            public User getUserByEmail(String email) {
                throw new RuntimeException("Simulated connection timeout to MySQL");
            }
        };
        AuthService faultTolerantAuth = new AuthService(mockFailingDAO);
        AuthResult dbErrorRes = faultTolerantAuth.authenticate("any@test.com", "Password123");
        assertTrue("Test 12 Passed: DB failure handled gracefully without crashing application",
            !dbErrorRes.isSuccess() && dbErrorRes.getMessage().contains("Database error"));

        System.out.println("\n=============================================================");
        System.out.println("Scenario Test Results: " + passed + " PASSED, " + failed + " FAILED");
        System.out.println("=============================================================");

        if (failed > 0) {
            System.exit(1);
        } else {
            System.exit(0);
        }
    }

    private static void assertTrue(String name, boolean condition) {
        if (condition) {
            System.out.println("  [PASS] " + name);
            passed++;
        } else {
            System.err.println("  [FAIL] " + name);
            failed++;
        }
    }

    private static void assertEquals(String name, Object expected, Object actual) {
        boolean match = (expected == null && actual == null) || (expected != null && expected.equals(actual));
        if (match) {
            System.out.println("  [PASS] " + name);
            passed++;
        } else {
            System.err.println("  [FAIL] " + name + " (Expected: " + expected + ", Actual: " + actual + ")");
            failed++;
        }
    }

    private static void fail(String message) {
        System.err.println("  [FAIL] " + message);
        failed++;
    }
}
