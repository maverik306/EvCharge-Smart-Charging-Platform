package com.evcharge.app.model;

import java.sql.Timestamp;

/**
 * Domain model representing a registered application user in EVCharge.
 */
public class User {

    private int userId;
    private String fullName;
    private String email;
    private String passwordHash;
    private Timestamp createdAt;

    public User() {
    }

    public User(int userId, String fullName, String email, String passwordHash, Timestamp createdAt) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.createdAt = createdAt;
    }

    public User(int userId, String fullName, String email, Timestamp createdAt) {
        this(userId, fullName, email, null, createdAt);
    }

    public User(String fullName, String email, String passwordHash) {
        this.userId = 0;
        this.fullName = fullName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
