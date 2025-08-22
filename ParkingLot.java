package com.cts.PMS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ParkingLot {
    private final Map<String, ParkingZone> zones = new HashMap<>();
    private final Map<String, ParkingSlot> qrToSlotMap = new HashMap<>();

    public ParkingLot() {
        zones.put("VIP", new ParkingZone("VIP", 5));
        zones.put("Regular", new ParkingZone("Regular", 10));
        zones.put("TwoWheeler", new ParkingZone("TwoWheeler", 7));
    }

    public boolean parkVehicle(Vehicle v, String zoneName) {
        ParkingZone zone = zones.get(zoneName);
        if (zone != null) {
            ParkingSlot slot = zone.getFreeSlot();
            if (slot != null) {
                v.setEntryTime(LocalDateTime.now());
                slot.parkVehicle(v);
                qrToSlotMap.put(v.getQrCode(), slot);
                saveVehicleToDB(v, zoneName, slot.getSlotId());
                return true;
            }
        }
        return false;
    }

    public boolean removeVehicle(String qr) {
        ParkingSlot slot = qrToSlotMap.remove(qr);
        if (slot != null && slot.isOccupied()) {
            slot.removeVehicle();
            updateVehicleExitInfo(qr);
            return true;
        }
        return false;
    }


    public Vehicle searchVehicle(String qr) {
        ParkingSlot slot = qrToSlotMap.get(qr);
        return (slot != null) ? slot.getVehicle() : null;
    }

    public int getTotalFreeSlots() {
        return zones.values().stream().mapToInt(ParkingZone::getFreeCount).sum();
    }

    public int getTotalOccupiedSlots() {
        return zones.values().stream().mapToInt(ParkingZone::getOccupiedCount).sum();
    }

    public void displayZoneStats() {
        for (ParkingZone zone : zones.values()) {
            System.out.println(zone.getZoneName() + " Zone → Free: " +
                zone.getFreeCount() + ", Occupied: " + zone.getOccupiedCount());
        }
    }

    public void saveVehicleToDB(Vehicle v, String zone, String slotId) {
        try (Connection conn = DBUtil.getConnection()) {
        	PreparedStatement ps = conn.prepareStatement(
        		    "INSERT INTO vehicles (qr_code, owner_name, type, entry_time, zone, slot_id) " +
        		    "VALUES (?, ?, ?, ?, ?, ?) " +
        		    "ON DUPLICATE KEY UPDATE " +
        		    "owner_name = VALUES(owner_name), " +
        		    "type = VALUES(type), " +
        		    "entry_time = VALUES(entry_time), " +
        		    "zone = VALUES(zone), " +
        		    "slot_id = VALUES(slot_id)"
        		);

            ps.setString(1, v.getQrCode());
            ps.setString(2, v.getOwnerName());
            ps.setString(3, v.getType());
            ps.setString(4, v.getEntryTime().toString());
            ps.setString(5, zone);
            ps.setString(6, slotId);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("❌ DB Error: " + e.getMessage());
        }
    }
    public ParkingSlot getSlotByQRCode(String qrCode) {
        return qrToSlotMap.get(qrCode);
    }
    public boolean isQRCodeUnique(String qrCode) {
        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT qr_code FROM vehicles WHERE qr_code = ?");
            ps.setString(1, qrCode);
            ResultSet rs = ps.executeQuery();
            return !rs.next(); // true if not found
        } catch (Exception e) {
            System.out.println("❌ DB Check Error: " + e.getMessage());
            return false;
        }
    }

    public void updateVehicleExitInfo(String qrCode) {
        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                "UPDATE vehicles SET exit_time = ?, status = ? WHERE qr_code = ?");
            ps.setString(1, LocalDateTime.now().toString());
            ps.setString(2, "Exited");
            ps.setString(3, qrCode);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("❌ DB Update Error: " + e.getMessage());
        }
    }


}
