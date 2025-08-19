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
                    System.out.print("Enter Vehicle Type (Car/Bike): ");
                    String type = sc.nextLine();
                    System.out.print("Choose Zone (VIP/Regular/TwoWheeler): ");
                    String zone = sc.nextLine();

                    String qr = Utils.generateQRCode();
                    Vehicle v = new Vehicle(qr, type, owner);
                    boolean parked = lot.parkVehicle(v, zone);
                    if (parked) {
                        System.out.println("✅ Vehicle parked successfully. QR Code: " + qr);
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

                case 3:
                    System.out.print("Enter QR Code to search: ");
                    String searchQr = sc.nextLine();
                    Vehicle found = lot.searchVehicle(searchQr);
                    if (found != null) {
                        System.out.println("🔍 Vehicle Found:");
                        System.out.println("Owner: " + found.getOwnerName());
                        System.out.println("Type: " + found.getType());
                        System.out.println("Entry Time: " + found.getEntryTime());
                        System.out.println("Free Slots: " + lot.getTotalFreeSlots());
                        System.out.println("Occupied Slots: " + lot.getTotalOccupiedSlots());
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

