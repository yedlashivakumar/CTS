package com.cts.PMS;
import java.util.*;

public class ParkingLot {
    private Map<String, ParkingZone> zones;
    private Map<String, Vehicle> activeVehicles;

    public ParkingLot() {
        zones = new HashMap<>();
        activeVehicles = new HashMap<>();

        zones.put("VIP", new ParkingZone("VIP", 5));
        zones.put("Regular", new ParkingZone("Regular", 10));
        zones.put("TwoWheeler", new ParkingZone("TwoWheeler", 7));
    }

    public boolean parkVehicle(Vehicle v, String zoneName) {
        ParkingZone zone = zones.get(zoneName);
        if (zone == null) return false;

        ParkingSlot slot = zone.getFreeSlot();
        if (slot == null) return false;

        slot.parkVehicle(v);
        activeVehicles.put(v.getQrCode(), v);
        return true;
    }

    public boolean removeVehicle(String qrCode) {
        for (ParkingZone zone : zones.values()) {
            for (ParkingSlot slot : zone.getSlots()) {
                if (slot.isOccupied() && slot.getVehicle().getQrCode().equals(qrCode)) {
                    slot.removeVehicle();
                    activeVehicles.remove(qrCode);
                    return true;
                }
            }
        }
        return false;
    }

    public Vehicle searchVehicle(String qrCode) {
        return activeVehicles.get(qrCode);
    }

    public int getTotalFreeSlots() {
        return zones.values().stream().mapToInt(ParkingZone::getFreeCount).sum();
    }

    public int getTotalOccupiedSlots() {
        return zones.values().stream().mapToInt(ParkingZone::getOccupiedCount).sum();
    }

    public void displayZoneStats() {
        for (ParkingZone zone : zones.values()) {
            System.out.println(zone.getZoneName() + " Zone → Free: " + zone.getFreeCount() + ", Occupied: " + zone.getOccupiedCount());
        }
    }
}
