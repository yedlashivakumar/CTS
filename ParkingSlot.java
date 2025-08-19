package com.cts.PMS;

public class ParkingSlot {
    private String slotId;
    private boolean isOccupied;
    private Vehicle vehicle;

    public ParkingSlot(String slotId) {
        this.slotId = slotId;
        this.isOccupied = false;
        this.vehicle = null;
    }

    public boolean isOccupied() { return isOccupied; }
    public String getSlotId() { return slotId; }
    public Vehicle getVehicle() { return vehicle; }

    public void parkVehicle(Vehicle v) {
        this.vehicle = v;
        this.isOccupied = true;
    }

    public void removeVehicle() {
        this.vehicle = null;
        this.isOccupied = false;
    }
}

