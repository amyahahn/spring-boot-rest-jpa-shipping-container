package com.amyhahn.ShippingContainerApplication.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ShippingContainerUtils {

    public static String getCurrentTimestampAsString() {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
}
