package com.artsafin.tgalarm.parser.lexer.token;

import java.util.function.Predicate;

public class AmToken extends LiteralToken {
    public static Predicate<String> supports = (String chars) -> chars.equals("утра");

    public AmToken(String value) {
        super(value);
    }
}
