package com.artsafin.tgalarm.parser.lexer.token;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.function.Predicate;

public class FullTimeToken extends Token<LocalTime> {
    public static Predicate<String> supports = (String chars) -> of(chars).isPresent();

    private static Optional<LocalTime> of(String chars) {
        try {
            return Optional.of(LocalTime.parse(chars, DateTimeFormatter.ofPattern("HH:mm")));
        } catch (DateTimeParseException exc) {
            return Optional.empty();
        }
    }

    public FullTimeToken(String value) {
        super(of(value).get());
    }
}
