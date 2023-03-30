package com.eyeconnection.server.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static LocalDateTime getDate(String dateString) {
        return LocalDateTime.parse(dateString, dateTimeFormatter);
    }

    public static LocalDateTime[] getDate(String[] dates) {
        LocalDateTime[] ret = new LocalDateTime[dates.length];
        for(int i = 0; i < dates.length; i++) {
            ret[i] = getDate(dates[i]);
        }
        return ret;
    }

    public static LocalDateTime[] getDate(LocalDateTime[] localDateTimes) {
        return null;
    }
}
