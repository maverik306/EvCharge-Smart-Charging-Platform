package com.evcharge.app.model;

public class Charger {

    private int id;
    private int locationId;
    private String chargerId;
    private String chargingMode;
    private String connectorType;
    private double pricePerKwh;
    private boolean available;

    public Charger(String chargerId, String chargingMode,
                   String connectorType, double pricePerKwh,
                   boolean available) {
        this(0, 0, chargerId, chargingMode, connectorType, pricePerKwh, available);
    }

    public Charger(int id, int locationId, String chargerId, String chargingMode,
                   String connectorType, double pricePerKwh,
                   boolean available) {
        this.id = id;
        this.locationId = locationId;
        this.chargerId = chargerId;
        this.chargingMode = chargingMode;
        this.connectorType = connectorType;
        this.pricePerKwh = pricePerKwh;
        this.available = available;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getChargerId() {
        return chargerId;
    }

    public String getChargingMode() {
        return chargingMode;
    }

    public String getConnectorType() {
        return connectorType;
    }

    public double getPricePerKwh() {
        return pricePerKwh;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isCompatibleWith(Vehicle vehicle) {
        if (vehicle == null || vehicle.getConnectorType() == null || this.connectorType == null) {
            return false;
        }
        return connectorType.equalsIgnoreCase(vehicle.getConnectorType());
    }
}
