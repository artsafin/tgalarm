package com.artsafin.tgalarm.parser.lexer.token;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.function.Predicate;

public class FullDateDashToken extends Token<LocalDate> {
    public static Predicate<String> supports = (String chars) -> create(chars).isPresent();

    private static Optional<LocalDate> create(String value) {
        try {
            LocalDate parsed = LocalDate.parse(value, DateTimeFormatter.ofPattern("YYYY-mm-dd"));
            return Optional.of(parsed);
        } catch (DateTimeParseException exc) {
            return Optional.empty();
        }
    }

    public FullDateDashToken(String value) {
        super(create(value).orElseThrow(() -> new RuntimeException("Invalid date")));
    }
}
