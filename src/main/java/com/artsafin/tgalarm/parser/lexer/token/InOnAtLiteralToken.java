package com.artsafin.tgalarm.parser.lexer.token;

import java.util.Optional;
import java.util.function.Predicate;

public class InOnAtLiteralToken extends LiteralToken {
    public static Predicate<String> supports = (String chars) -> chars.toLowerCase().equals("в");

    public InOnAtLiteralToken(String value) {
        super(value);
    }

    public static Optional<InOnAtLiteralToken> of (String value) {
        return Optional.of(new InOnAtLiteralToken(value));
    }
}
