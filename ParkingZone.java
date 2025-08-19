package com.cts.PMS;

import java.util.ArrayList;
import java.util.List;

public class ParkingZone {
    private String zoneName;
    private List<ParkingSlot> slots;

    public ParkingZone(String zoneName, int totalSlots) {
        this.zoneName = zoneName;
        this.slots = new ArrayList<>();
        for (int i = 1; i <= totalSlots; i++) {
            slots.add(new ParkingSlot(zoneName + "-S" + i));
        }
    }

    public List<ParkingSlot> getSlots() { return slots; }

    public ParkingSlot getFreeSlot() {
        for (ParkingSlot slot : slots) {
            if (!slot.isOccupied()) return slot;
        }
        return null;
    }

    public int getFreeCount() {
        return (int) slots.stream().filter(s -> !s.isOccupied()).count();
    }

    public int getOccupiedCount() {
        return (int) slots.stream().filter(ParkingSlot::isOccupied).count();
    }

    public String getZoneName() { return zoneName; }
}
