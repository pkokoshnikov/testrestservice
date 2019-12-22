package ru.pkokoshnikov.testrestservice.api.model;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ZonedDateTimeXmlAdapter extends XmlAdapter<String, ZonedDateTime> {

    @Override
    public ZonedDateTime unmarshal(String v) {
        return ZonedDateTime.parse(v, DateTimeFormatter.ISO_ZONED_DATE_TIME);
    }

    @Override
    public String marshal(ZonedDateTime v) {
        return v.format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
    }
}
