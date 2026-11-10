package com.evcharge.app.model;

public class Driver {
    private int driverId;
    private String name;
    private String phoneNumber;
    private String email;
    private int loyaltyPoints;

    public Driver(String name, String phoneNumber, String email) {
        this.driverId = 0;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.loyaltyPoints = 0;
    }

    public Driver(int driverId, String name, String phoneNumber, String email, int loyaltyPoints) {
        this.driverId = driverId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.loyaltyPoints = loyaltyPoints;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void addLoyaltyPoints(int points) {
        if (points > 0) {
            loyaltyPoints += points;
        }
    }
}
