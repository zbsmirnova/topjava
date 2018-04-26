package ru.javawebinar.topjava.util.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeConverterFactory implements ConverterFactory<String,LocalTime> {

    @Override
    public <T extends LocalTime> Converter<String, T> getConverter(Class<T> targetType) {
        return new TimeConverter();
    }
    public class TimeConverter<T extends LocalTime> implements Converter<String, LocalTime> {
        private java.time.format.DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        @Override
        public LocalTime convert(String source) {
            if (source.equals("null") || !source.contains("T") && !source.contains(":")) return null;
            if (source.contains("T")) source = source.substring(12);
            return LocalTime.parse(source, formatter);
        }
    }

}
