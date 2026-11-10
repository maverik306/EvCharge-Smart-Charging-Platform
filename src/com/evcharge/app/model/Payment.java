package com.evcharge.app.model;

public class Payment {

    private int id;
    private String paymentId;
    private ChargingSession session;
    private String paymentMethod;
    private String paymentStatus;
    private double amount;

    public Payment(String paymentId, ChargingSession session) {
        this(0, paymentId, session, null, "PENDING", session != null ? session.getTotalAmount() : 0.0);
    }

    public Payment(int id, String paymentId, ChargingSession session,
                   String paymentMethod, String paymentStatus, double amount) {
        this.id = id;
        this.paymentId = paymentId;
        this.session = session;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus != null ? paymentStatus : "PENDING";
        this.amount = amount;
    }

    public void makePayment(String paymentMethod) {
        this.paymentMethod = paymentMethod;
        this.paymentStatus = "PAID";
        if (session != null) {
            this.amount = session.getTotalAmount();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public ChargingSession getSession() {
        return session;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public double getAmount() {
        if (amount > 0) return amount;
        return session != null ? session.getTotalAmount() : 0.0;
    }
}
