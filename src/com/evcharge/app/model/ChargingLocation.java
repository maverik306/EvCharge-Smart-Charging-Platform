package com.evcharge.app.model;

public class ChargingLocation {

    private int locationId;
    private String name;
    private String address;
    private String locationType;
    private boolean verified;
    private double rating;

    public ChargingLocation(String name, String address,
                            boolean verified, double rating) {
        this(0, name, address, "Station", verified, rating);
    }

    public ChargingLocation(int locationId, String name, String address,
                            String locationType, boolean verified, double rating) {
        this.locationId = locationId;
        this.name = name;
        this.address = address;
        this.locationType = locationType;
        this.verified = verified;
        this.rating = rating;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getLocationType() {
        return locationType;
    }

    public boolean isVerified() {
        return verified;
    }

    public double getRating() {
        return rating;
    }
}
