package ru.javawebinar.topjava.util.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import javax.swing.text.Style;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateConverterFactory implements ConverterFactory<String,LocalDate> {

    @Override
    public <T extends LocalDate> Converter<String, T> getConverter(Class<T> targetType) {
        return new DateConverterFactory.DateConverter();

    }

    public class DateConverter<T extends LocalDate> implements Converter<String, LocalDate> {
        private java.time.format.DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        @Override
        public LocalDate convert(String source) {
            if (source.equals("null") || !source.contains("-")) return null;

            source = source.substring(0, 10);
            return LocalDate.parse(source, formatter);
        }
    }

}

