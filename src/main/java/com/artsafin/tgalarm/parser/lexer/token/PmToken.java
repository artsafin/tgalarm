package com.artsafin.tgalarm.parser.lexer.token;

import java.util.Optional;
import java.util.function.Predicate;

public class PmToken extends LiteralToken {
    public static Predicate<String> supports = (String chars) -> chars.equals("вечера") || chars.equals("дня");

    public PmToken(String value) {
        super(value);
    }

    public static Optional<PmToken> of (String value) {
        return Optional.of(new PmToken(value));
    }
}
