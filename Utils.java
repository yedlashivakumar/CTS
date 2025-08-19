package com.cts.PMS;

import java.util.UUID;

public class Utils {
    public static String generateQRCode() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}

