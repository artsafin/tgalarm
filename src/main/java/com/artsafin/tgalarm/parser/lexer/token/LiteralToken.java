package com.artsafin.tgalarm.parser.lexer.token;

import java.util.Optional;

public class LiteralToken extends Token<String> {
    public LiteralToken(String value) {
        super(value);
    }

    public static Optional<? extends LiteralToken> of (String value) {
        return Optional.of(new LiteralToken(value));
    }
}
