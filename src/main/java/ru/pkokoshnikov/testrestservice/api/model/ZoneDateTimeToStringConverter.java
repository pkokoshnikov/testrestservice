package ru.pkokoshnikov.testrestservice.api.model;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ZoneDateTimeToStringConverter extends StdConverter<ZonedDateTime, String> {
    @Override
    public String convert(ZonedDateTime v) {
        return v.format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
    }
}
