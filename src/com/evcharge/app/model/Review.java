package com.evcharge.app.model;

public class Review {

    private String reviewId;
    private Driver driver;
    private ChargingLocation location;
    private Booking booking;
    private int rating;
    private String comment;

    public Review(String reviewId, Driver driver,
                  ChargingLocation location, Booking booking,
                  int rating, String comment) {

        this.reviewId = reviewId;
        this.driver = driver;
        this.location = location;
        this.booking = booking;
        this.rating = rating;
        this.comment = comment;
    }

    public boolean isValidReview() {
        return booking != null
                && "COMPLETED".equals(booking.getStatus())
                && rating >= 1
                && rating <= 5;
    }

    public String getReviewId() {
        return reviewId;
    }

    public Driver getDriver() {
        return driver;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public ChargingLocation getLocation() {
        return location;
    }

    public Booking getBooking() {
        return booking;
    }
}
