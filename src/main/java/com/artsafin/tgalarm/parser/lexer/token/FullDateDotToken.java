package com.artsafin.tgalarm.parser.lexer.token;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.function.Predicate;

public class FullDateDotToken extends Token<LocalDate> {
    public static Predicate<String> supports = (String chars) -> of(chars).isPresent();

    public static Optional<FullDateDotToken> of(String value) {
        try {
            LocalDate parsed = LocalDate.parse(value, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            return Optional.of(new FullDateDotToken(parsed));
        } catch (DateTimeParseException exc) {
            return Optional.empty();
        }
    }

    public FullDateDotToken(LocalDate value) {
        super(value);
    }
}
