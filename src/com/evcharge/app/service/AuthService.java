package com.evcharge.app.service;

import java.util.regex.Pattern;

import org.mindrot.jbcrypt.BCrypt;

import com.evcharge.app.dao.UserDAO;
import com.evcharge.app.model.User;

/**
 * Service handling user authentication, credential validation,
 * password hashing (BCrypt), and account registration logic.
 */
public class AuthService {

    private final UserDAO userDAO;

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}$"
    );

    public AuthService() {
        this.userDAO = new UserDAO();
    }

    public AuthService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Represents the outcome of an authentication or registration attempt.
     */
    public static class AuthResult {
        private final boolean success;
        private final String message;
        private final User user;

        public AuthResult(boolean success, String message, User user) {
            this.success = success;
            this.message = message;
            this.user = user;
        }

        public static AuthResult success(String message, User user) {
            return new AuthResult(true, message, user);
        }

        public static AuthResult failure(String message) {
            return new AuthResult(false, message, null);
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }

        public User getUser() {
            return user;
        }
    }

    /**
     * Validates whether an email has a standard valid format.
     */
    public boolean isValidEmail(String email) {
        if (email == null) return false;
        String trimmed = email.trim();
        return !trimmed.isEmpty() && EMAIL_PATTERN.matcher(trimmed).matches();
    }

    /**
     * Validates password strength:
     * - Minimum 6 characters
     * - Must contain at least one letter and at least one digit
     * Returns null if valid, or an error message if invalid.
     */
    public String validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            return "Password is required.";
        }
        if (password.length() < 6) {
            return "Password must be at least 6 characters long.";
        }
        boolean hasLetter = false;
        boolean hasDigit = false;
        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) hasLetter = true;
            if (Character.isDigit(c)) hasDigit = true;
        }
        if (!hasLetter || !hasDigit) {
            return "Password must contain at least one letter and one number.";
        }
        return null;
    }

    /**
     * Validates registration form inputs.
     * Returns null if all valid, or a descriptive error message.
     */
    public String validateRegistrationInputs(String fullName, String email, String password, String confirmPassword) {
        if (fullName == null || fullName.trim().isEmpty()) {
            return "Please enter your full name.";
        }
        if (fullName.trim().length() < 2) {
            return "Full name must be at least 2 characters.";
        }
        if (email == null || email.trim().isEmpty()) {
            return "Please enter your email address.";
        }
        if (!isValidEmail(email)) {
            return "Please enter a valid email address (e.g. user@example.com).";
        }
        String pwdErr = validatePassword(password);
        if (pwdErr != null) {
            return pwdErr;
        }
        if (confirmPassword == null || confirmPassword.isEmpty()) {
            return "Please confirm your password.";
        }
        if (!password.equals(confirmPassword)) {
            return "Passwords do not match. Please re-enter.";
        }
        return null;
    }

    /**
     * Registers a new user account with BCrypt password hashing.
     */
    public AuthResult register(String fullName, String email, String password, String confirmPassword) {
        // 1. Input validation
        String validationError = validateRegistrationInputs(fullName, email, password, confirmPassword);
        if (validationError != null) {
            return AuthResult.failure(validationError);
        }

        String normalizedEmail = email.trim().toLowerCase();

        // 2. Check for duplicate email in database
        try {
            if (userDAO.existsByEmail(normalizedEmail)) {
                return AuthResult.failure("An account with this email address already exists. Please log in.");
            }
        } catch (Exception e) {
            return AuthResult.failure("Unable to verify account availability. Please check MySQL database connection.");
        }

        // 3. Securely hash password using BCrypt with salt cost 12
        String passwordHash;
        try {
            passwordHash = BCrypt.hashpw(password, BCrypt.gensalt(12));
        } catch (Exception e) {
            return AuthResult.failure("Security error: unable to hash password.");
        }

        // 4. Insert into database
        int userId = userDAO.createUser(fullName.trim(), normalizedEmail, passwordHash);
        if (userId > 0) {
            User newUser = userDAO.getUserById(userId);
            if (newUser == null) {
                newUser = new User(userId, fullName.trim(), normalizedEmail, null);
            }
            return AuthResult.success("Account created successfully! You can now log in.", newUser);
        } else {
            return AuthResult.failure("Registration failed. Please try again or check database connection.");
        }
    }

    /**
     * Authenticates a user using email and password.
     */
    public AuthResult authenticate(String email, String password) {
        // 1. Input field checks
        if (email == null || email.trim().isEmpty()) {
            return AuthResult.failure("Please enter your email address.");
        }
        if (!isValidEmail(email)) {
            return AuthResult.failure("Please enter a valid email address.");
        }
        if (password == null || password.isEmpty()) {
            return AuthResult.failure("Please enter your password.");
        }

        String normalizedEmail = email.trim().toLowerCase();

        // 2. Look up user by email in MySQL
        User user;
        try {
            user = userDAO.getUserByEmail(normalizedEmail);
        } catch (Exception e) {
            return AuthResult.failure("Database error: Could not reach MySQL. Please ensure MySQL is running.");
        }

        // 3. User does not exist
        if (user == null) {
            return AuthResult.failure("No account found with this email. Please create an account.");
        }

        // 4. Verify password against stored BCrypt hash
        try {
            String storedHash = user.getPasswordHash();
            if (storedHash == null || !BCrypt.checkpw(password, storedHash)) {
                return AuthResult.failure("Incorrect password. Please try again.");
            }
        } catch (Exception e) {
            return AuthResult.failure("Authentication failed: invalid password format or verification error.");
        }

        // 5. Success
        return AuthResult.success("Welcome back, " + user.getFullName() + "!", user);
    }
}
