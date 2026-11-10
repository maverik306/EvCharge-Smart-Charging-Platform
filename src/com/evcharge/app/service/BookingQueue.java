package com.evcharge.app.service;

import java.util.PriorityQueue;

import com.evcharge.app.model.Booking;

public class BookingQueue {

    private PriorityQueue<Booking> queue;

    public BookingQueue() {
        queue = new PriorityQueue<>((firstBooking, secondBooking) -> {

            boolean firstIsEmergency = firstBooking.getChargingMode()
                    .equalsIgnoreCase("EMERGENCY");

            boolean secondIsEmergency = secondBooking.getChargingMode()
                    .equalsIgnoreCase("EMERGENCY");

            if (firstIsEmergency && !secondIsEmergency) {
                return -1;
            }

            if (!firstIsEmergency && secondIsEmergency) {
                return 1;
            }

            return Integer.compare(
                    firstBooking.getVehicle().getCurrentBatteryPercentage(),
                    secondBooking.getVehicle().getCurrentBatteryPercentage()
            );
        });
    }

    public void addBooking(Booking booking) {
        queue.add(booking);
    }

    public Booking getNextBooking() {
        return queue.poll();
    }

    public int getQueueSize() {
        return queue.size();
    }
}
