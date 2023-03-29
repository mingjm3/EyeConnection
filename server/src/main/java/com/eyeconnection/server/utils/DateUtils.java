package com.eyeconnection.server.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static LocalDateTime getDate(String dateString) {
        return LocalDateTime.parse(dateString, dateTimeFormatter);
    }
}
