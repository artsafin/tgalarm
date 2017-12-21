package com.artsafin.tgalarm.parser.lexer.token;

import java.util.Optional;
import java.util.function.Predicate;

public class AmToken extends LiteralToken {
    public static Predicate<String> supports = (String chars) -> chars.equals("утра");

    public AmToken(String value) {
        super(value);
    }

    public static Optional<AmToken> of (String value) {
        return Optional.of(new AmToken(value));
    }
}
