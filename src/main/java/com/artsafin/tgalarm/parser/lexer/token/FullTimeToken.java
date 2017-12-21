package com.artsafin.tgalarm.parser.lexer.token;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.function.Predicate;

public class FullTimeToken extends Token<LocalTime> {
    public static Predicate<String> supports = (String chars) -> of(chars).isPresent();

    public static Optional<FullTimeToken> of(String chars) {
        try {
            LocalTime parsed = LocalTime.parse(chars, DateTimeFormatter.ofPattern("HH:mm"));
            return Optional.of(new FullTimeToken(parsed));
        } catch (DateTimeParseException exc) {
            return Optional.empty();
        }
    }

    public FullTimeToken(LocalTime value) {
        super(value);
    }
}
