package com.evcharge.app.model;

public class ChargingSession {

    private int id;
    private String sessionId;
    private Booking booking;
    private double energyUsedKwh;
    private double totalAmount;
    private boolean completed;
    private String sessionStatus;

    public ChargingSession(String sessionId, Booking booking) {
        this(0, sessionId, booking, 0.0, 0.0, false, "CHARGING");
    }

    public ChargingSession(int id, String sessionId, Booking booking, double energyUsedKwh,
                           double totalAmount, boolean completed, String sessionStatus) {
        this.id = id;
        this.sessionId = sessionId;
        this.booking = booking;
        this.energyUsedKwh = energyUsedKwh;
        this.totalAmount = totalAmount;
        this.completed = completed;
        this.sessionStatus = sessionStatus != null ? sessionStatus : (completed ? "COMPLETED" : "CHARGING");
    }

    public void completeSession(double energyUsedKwh) {
        if (energyUsedKwh > 0) {
            this.energyUsedKwh = energyUsedKwh;

            double rate = (booking != null && booking.getCharger() != null)
                    ? booking.getCharger().getPricePerKwh() : 18.50;
            this.totalAmount = energyUsedKwh * rate;
            this.completed = true;
            this.sessionStatus = "COMPLETED";

            if (booking != null) {
                booking.completeBooking();
            }
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public Booking getBooking() {
        return booking;
    }

    public double getEnergyUsedKwh() {
        return energyUsedKwh;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public boolean isCompleted() {
        return completed;
    }

    public String getSessionStatus() {
        return sessionStatus;
    }

    public void setSessionStatus(String sessionStatus) {
        this.sessionStatus = sessionStatus;
        if ("COMPLETED".equals(sessionStatus)) {
            this.completed = true;
        }
    }
}
