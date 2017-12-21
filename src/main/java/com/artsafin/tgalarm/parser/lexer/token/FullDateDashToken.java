package com.artsafin.tgalarm.parser.lexer.token;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.function.Predicate;

public class FullDateDashToken extends Token<LocalDate> {
    public static Predicate<String> supports = (String chars) -> of(chars).isPresent();

    public static Optional<FullDateDashToken> of(String value) {
        try {
            LocalDate parsed = LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return Optional.of(new FullDateDashToken(parsed));
        } catch (DateTimeParseException exc) {
            return Optional.empty();
        }
    }

    public FullDateDashToken(LocalDate value) {
        super(value);
    }
}
