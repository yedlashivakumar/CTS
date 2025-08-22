package com.cts.PMS;

import java.time.LocalDateTime;

public class Vehicle {
    private final String qrCode;
    private final String type;
    private final String ownerName;
    private LocalDateTime entryTime;

    public Vehicle(String qrCode, String type, String ownerName) {
        this.qrCode = qrCode;
        this.type = type;
        this.ownerName = ownerName;
    }

    public String getQrCode() {
        return qrCode;
    }

    public String getType() {
        return type;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(LocalDateTime entryTime) {
        this.entryTime = entryTime;
    }
}
