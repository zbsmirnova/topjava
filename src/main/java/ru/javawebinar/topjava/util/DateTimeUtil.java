package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public  static <T extends Comparable> boolean isBetween(LocalDateTime lt, T startTime, T endTime) {
        if(startTime instanceof LocalDate && endTime instanceof LocalDate){
            LocalDate start = (LocalDate) startTime;
            LocalDate end = (LocalDate) endTime;
            LocalDate ltDate = lt.toLocalDate();
            return ltDate.compareTo(start) >= 0 && ltDate.compareTo(end) <= 0;
        }
        else if(startTime instanceof LocalTime && endTime instanceof LocalTime) {
            LocalTime start = (LocalTime) startTime;
            LocalTime end = (LocalTime) endTime;
            LocalTime ltTime = lt.toLocalTime();
            return ltTime.compareTo(start) >= 0 && ltTime.compareTo(end) <= 0;
        }
        else if(startTime instanceof LocalDateTime && endTime instanceof LocalDateTime){
            LocalDateTime start = (LocalDateTime) startTime;
            LocalDateTime end = (LocalDateTime) endTime;
            return lt.compareTo(start) >= 0 && lt.compareTo(end) <= 0;}
            else return false;
        }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}
