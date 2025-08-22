package com.cts.PMS;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ParkingLot lot = new ParkingLot();

        System.out.println("🚗 Welcome to Smart Parking Lot Management System 🚗");
        System.out.println("---------------------------------------------------");

        boolean running = true;
        while (running) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Park Vehicle");
            System.out.println("2. Remove Vehicle");
            System.out.println("3. Search Vehicle");
            System.out.println("4. View Parking Stats");
            System.out.println("5. Exit");

            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Owner Name: ");
                    String owner = sc.nextLine();

                    String type = "";
                    while (true) {
                        System.out.println("Select Vehicle Type:");
                        System.out.println("1. Car");
                        System.out.println("2. Bike");
                        int typeChoice = sc.nextInt();
                        sc.nextLine(); // consume newline

                        if (typeChoice == 1) {
                            type = "Car";
                            break;
                        } else if (typeChoice == 2) {
                            type = "Bike";
                            break;
                        } else {
                            System.out.println("⚠️ Invalid selection. Please choose 1 for Car or 2 for Bike.");
                        }
                    }

                    String zone = "";
                    while (true) {
                        System.out.println("Select Zone:");
                        System.out.println("1. VIP");
                        System.out.println("2. Regular");
                        System.out.println("3. TwoWheeler");
                        int zoneChoice = sc.nextInt();
                        sc.nextLine(); // consume newline

                        if (zoneChoice == 1) {
                            zone = "VIP";
                            break;
                        } else if (zoneChoice == 2) {
                            zone = "Regular";
                            break;
                        } else if (zoneChoice == 3) {
                            zone = "TwoWheeler";
                            break;
                        } else {
                            System.out.println("⚠️ Invalid selection. Please choose 1 for VIP, 2 for Regular, or 3 for TwoWheeler.");
                        }
                    }

                    

                    String qr;
                    do {
                        qr = Utils.generateQRCode();
                    } while (!lot.isQRCodeUnique(qr));

                    Vehicle v = new Vehicle(qr, type, owner);
                    boolean parked = lot.parkVehicle(v, zone);
                    if (parked) {
                        ParkingSlot slot = lot.getSlotByQRCode(v.getQrCode()); // Add this helper method
                        System.out.println("✅ Vehicle parked successfully. QR Code: " + qr);
                        System.out.println("🅿️ Assigned Slot: " + slot.getSlotId());
                        lot.saveVehicleToDB(v, zone, slot.getSlotId());
                    } else {
                        System.out.println("❌ No available slots in selected zone.");
                    }
                    break;

                case 2:
                    System.out.print("Enter QR Code to remove: ");
                    String removeQr = sc.nextLine();
                    boolean removed = lot.removeVehicle(removeQr);
                    System.out.println(removed ? "✅ Vehicle removed." : "❌ Vehicle not found.");
                    break;

                case 3: // Search Vehicle
                    System.out.print("Enter QR Code to search: ");
                    String searchQr = sc.nextLine();
                    Vehicle v1 = lot.searchVehicle(searchQr);
                    if (v1 != null) {
                        System.out.println("🔍 Vehicle Found:");
                        System.out.println("Owner: " + v1.getOwnerName());
                        System.out.println("Type: " + v1.getType());
                        System.out.println("Entry Time: " + v1.getEntryTime());

                        // ✅ Add this here:
                        ParkingSlot slot = lot.getSlotByQRCode(searchQr);
                        if (slot != null) {
                            System.out.println("Slot ID: " + slot.getSlotId());
                        }
                    } else {
                        System.out.println("❌ Vehicle not found.");
                    }
                    break;


                case 4:
                    System.out.println("📊 Parking Zone Stats:");
                    lot.displayZoneStats();
                    break;

                case 5:
                    System.out.println("👋 Thank you for using Smart Parking Lot System!");
                    running = false;
                    break;

                default:
                    System.out.println("⚠️ Invalid choice. Try again.");
            }
        }

        sc.close();
    }
}
