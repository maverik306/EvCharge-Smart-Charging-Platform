package com.evcharge.app.model;

public class Booking {

    private int id;
    private String bookingId;
    private Driver driver;
    private Vehicle vehicle;
    private Charger charger;
    private String chargingMode;
    private String status;

    public Booking(String bookingId, Driver driver, Vehicle vehicle,
                   Charger charger, String chargingMode) {
        this(0, bookingId, driver, vehicle, charger, chargingMode, "PENDING");
    }

    public Booking(int id, String bookingId, Driver driver, Vehicle vehicle,
                   Charger charger, String chargingMode, String status) {
        this.id = id;
        this.bookingId = bookingId;
        this.driver = driver;
        this.vehicle = vehicle;
        this.charger = charger;
        this.chargingMode = chargingMode;
        this.status = status != null ? status : "PENDING";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookingId() {
        return bookingId;
    }

    public Driver getDriver() {
        return driver;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Charger getCharger() {
        return charger;
    }

    public String getChargingMode() {
        return chargingMode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void confirmBooking() {
        status = "CONFIRMED";
    }

    public void startCharging() {
        status = "CHARGING";
    }

    public void completeBooking() {
        status = "COMPLETED";
    }
}
