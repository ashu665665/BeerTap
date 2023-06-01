package com.backend.dispenser.beertap.util;

import java.time.Instant;

public class DateUtil {

    public static long datetimeDiffInSeconds(String datetimeString1, String datetimeString2) {
        Instant instant1 = Instant.parse(datetimeString1);
        Instant instant2 = Instant.parse(datetimeString2);
        long diffInSeconds = Math.abs(instant2.getEpochSecond() - instant1.getEpochSecond());
        return diffInSeconds;
    }

}
