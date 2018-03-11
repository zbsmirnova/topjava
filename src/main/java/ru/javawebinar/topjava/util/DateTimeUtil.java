package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public  static boolean isBetween(Temporal lt, Temporal startTime, Temporal endTime, Class <? extends Comparable> type) {
        if(type == LocalTime.class) {
            LocalTime ltLocal = LocalTime.from(lt);
            LocalTime startTimeLocal = LocalTime.from(startTime);
            LocalTime endTimeLocal = LocalTime.from(endTime);
            return ltLocal.compareTo(startTimeLocal) >= 0 && ltLocal.compareTo(endTimeLocal) <= 0;
        }
        else if(type == LocalDate.class){
            LocalDate ltLocal = LocalDate.from(lt);
            LocalDate startTimeLocal = LocalDate.from(startTime);
            LocalDate endTimeLocal = LocalDate.from(endTime);
            return ltLocal.compareTo(startTimeLocal) >= 0 && ltLocal.compareTo(endTimeLocal) <= 0;

        }
        else if(type == LocalDateTime.class){
            LocalDateTime ltLocal = LocalDateTime.from(lt);
            LocalDateTime startTimeLocal = LocalDateTime.from(startTime);
            LocalDateTime endTimeLocal = LocalDateTime.from(endTime);
            return ltLocal.compareTo(startTimeLocal) >= 0 && ltLocal.compareTo(endTimeLocal) <= 0;

        }
       return false;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}
