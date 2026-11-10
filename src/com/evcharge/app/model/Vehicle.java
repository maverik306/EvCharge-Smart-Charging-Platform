package com.evcharge.app.model;

public class Vehicle {
    private int vehicleId;
    private int driverId;
    private String model;
    private double batteryCapacityKwh;
    private String connectorType;
    private int currentBatteryPercentage;

    public Vehicle(String model, double batteryCapacityKwh,
                   String connectorType, int currentBatteryPercentage) {
        this(0, 0, model, batteryCapacityKwh, connectorType, currentBatteryPercentage);
    }

    public Vehicle(int vehicleId, int driverId, String model, double batteryCapacityKwh,
                   String connectorType, int currentBatteryPercentage) {
        this.vehicleId = vehicleId;
        this.driverId = driverId;
        this.model = model;
        this.batteryCapacityKwh = batteryCapacityKwh;
        this.connectorType = connectorType;
        this.currentBatteryPercentage = currentBatteryPercentage;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getModel() {
        return model;
    }

    public double getBatteryCapacityKwh() {
        return batteryCapacityKwh;
    }

    public String getConnectorType() {
        return connectorType;
    }

    public int getCurrentBatteryPercentage() {
        return currentBatteryPercentage;
    }

    public void setCurrentBatteryPercentage(int currentBatteryPercentage) {
        this.currentBatteryPercentage = currentBatteryPercentage;
    }
}
