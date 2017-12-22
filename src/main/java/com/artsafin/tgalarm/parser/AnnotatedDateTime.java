package com.artsafin.tgalarm.parser;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class AnnotatedDateTime {
    private ZonedDateTime dateTime;
    private String annotation;

    public AnnotatedDateTime(ZonedDateTime dateTime, String annotation) {
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
}
