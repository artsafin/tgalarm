package com.artsafin.tgalarm.parser.lexer.token;

import java.util.function.Predicate;

public class DayLiteralToken extends LiteralToken {
    public static Predicate<String> supports = (String chars) -> chars.toLowerCase().equals("числа") || chars.toLowerCase().equals("число");

    public DayLiteralToken(String value) {
        super(value);
    }
}
