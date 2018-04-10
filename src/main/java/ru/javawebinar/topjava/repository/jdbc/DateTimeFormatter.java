package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.function.Function;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan({ "ru.javawebinar.topjava.repository.jdbc*" })
public class DateTimeFormatter {

    @Bean
    @Profile("hsqldb")
    public Function<LocalDateTime, Timestamp> convertToTimestamp() {
        return Timestamp::valueOf;
    }

    @Bean
    @Profile("postgres")
    public Function<LocalDateTime, LocalDateTime> convertToLocalDateTime() {
        return dt -> dt;
    }
}
