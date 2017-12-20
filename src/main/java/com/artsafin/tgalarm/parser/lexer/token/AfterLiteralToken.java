package com.artsafin.tgalarm.parser.lexer.token;

import java.util.function.Predicate;

public class AfterLiteralToken extends LiteralToken {
    public static Predicate<String> supports = (String chars) -> chars.toLowerCase().equals("через");

    public AfterLiteralToken(String value) {
        super(null);
    }
    public AfterLiteralToken() {
        super(null);
    }
}
