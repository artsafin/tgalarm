package com.artsafin.tgalarm.parser;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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

    public Optional<String> timeSpecAsString() {
        return Optional.ofNullable(dateTime).map(it -> it.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG)));
    }

    public String annotation() {
        return annotation;
    }

    public Optional<ZonedDateTime> nextPeriod() {
        return Optional.empty();
    }
}
