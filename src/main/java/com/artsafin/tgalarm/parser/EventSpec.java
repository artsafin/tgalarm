package com.artsafin.tgalarm.parser;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class EventSpec {
    private ZonedDateTime dateTime;
    private String annotation;

    EventSpec(ZonedDateTime dateTime, String annotation) {
        this.dateTime = dateTime;
        this.annotation = annotation;
    }

    public Optional<ZonedDateTime> getDateTime() {
        return Optional.ofNullable(dateTime);
    }

    public String getAnnotation() {
        return annotation;
    }

    @Override
    public String toString() {
        return "At "
                + dateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                + " annotation '" + annotation + '\'';
    }

    public Optional<ZonedDateTime> nextPeriod() {
        return Optional.empty();
    }
}
