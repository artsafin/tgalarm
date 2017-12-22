package com.artsafin.tgalarm.parser.lexer.token;

import java.util.function.Predicate;

public class EscapeToken extends Token<String> {
    public static Predicate<String> supports = (String chars) -> chars.toLowerCase().equals("\"");

    public EscapeToken(String value) {
        super(value);
    }
}
