package com.cts.PMS;

import java.time.LocalDateTime;

public class Vehicle {
    private String qrCode;
    private String type;
    private String ownerName;
    private LocalDateTime entryTime;

    public Vehicle(String qrCode, String type, String ownerName) {
        this.qrCode = qrCode;
        this.type = type;
        this.ownerName = ownerName;
        this.entryTime = LocalDateTime.now();
    }

    public String getQrCode() { return qrCode; }
    public String getType() { return type; }
    public String getOwnerName() { return ownerName; }
    public LocalDateTime getEntryTime() { return entryTime; }
}
