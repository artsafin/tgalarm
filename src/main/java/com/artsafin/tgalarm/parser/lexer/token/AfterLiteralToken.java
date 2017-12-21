package com.artsafin.tgalarm.parser.lexer.token;

import java.util.Optional;
import java.util.function.Predicate;

public class AfterLiteralToken extends LiteralToken {
    public static Predicate<String> supports = (String chars) -> chars.toLowerCase().equals("через");

    public AfterLiteralToken(String value) {
        super(value);
    }

    public static Optional<AfterLiteralToken> of (String value) {
        return Optional.of(new AfterLiteralToken(value));
    }
}
