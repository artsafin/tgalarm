package com.artsafin.tgalarm.parser.lexer.token;

import java.util.function.Predicate;

public class InOnAtLiteralToken extends LiteralToken {
    public static Predicate<String> supports = (String chars) -> chars.toLowerCase().equals("в");

    public InOnAtLiteralToken(String value) {
        super(value);
    }
}
