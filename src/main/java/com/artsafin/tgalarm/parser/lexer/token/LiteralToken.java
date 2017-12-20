package com.artsafin.tgalarm.parser.lexer.token;

public class LiteralToken extends Token<String> {
    public LiteralToken(String value) {
        super(value);
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass().equals(getClass())
                && (
                ((LiteralToken) obj).getValue() == null && getValue() == null
                        || ((LiteralToken) obj).getValue() != null && getValue() != null
                        && getValue().equals(((LiteralToken) obj).getValue())
        );
    }
}
