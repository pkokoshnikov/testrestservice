package ru.pkokoshnikov.testrestservice.api.model;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class StringToZoneDateTimeConverter extends StdConverter<String, ZonedDateTime> {
    @Override
    public ZonedDateTime convert(String v) {
        return ZonedDateTime.parse(v, DateTimeFormatter.ISO_ZONED_DATE_TIME);
    }
}
