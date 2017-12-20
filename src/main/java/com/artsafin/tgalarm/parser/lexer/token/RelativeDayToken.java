package com.artsafin.tgalarm.parser.lexer.token;

import java.util.Optional;
import java.util.function.Predicate;

public class RelativeDayToken extends Token<Integer> {
    public static Predicate<String> supports = (String chars) -> getDayIncrement(chars).isPresent();

    private static Optional<Integer> getDayIncrement(String value) {
        value = value.toLowerCase();

        if (value.equals("сегодня") || value.equals("сег")) {
            return Optional.of(0);
        }
        if (value.equals("вчера")) {
            return Optional.of(-1);
        }
        if (value.equals("завтра")) {
            return Optional.of(1);
        }
        if (value.equals("позавчера")) {
            return Optional.of(-2);
        }
        if (value.equals("послезавтра")) {
            return Optional.of(2);
        }

        return Optional.empty();
    }

    public RelativeDayToken(String value) {
        super(getDayIncrement(value).orElseThrow(() -> new RuntimeException("Invalid relative day")));
    }
}
